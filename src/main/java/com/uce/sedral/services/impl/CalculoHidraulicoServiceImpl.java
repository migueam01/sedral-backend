package com.uce.sedral.services.impl;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.dto.CalculoHidraulicoDTO;
import com.uce.sedral.models.dto.CotaAlturaTuberia;
import com.uce.sedral.models.entities.CalculoHidraulico;
import com.uce.sedral.models.entities.Proyecto;
import com.uce.sedral.models.entities.Tuberia;
import com.uce.sedral.repositories.ICalculoHidraulicoRepo;
import com.uce.sedral.repositories.IProyectoRepo;
import com.uce.sedral.repositories.ITuberiaRepo;
import com.uce.sedral.services.ICalculoHidraulicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.uce.sedral.utils.Operaciones.*;

@Service
@RequiredArgsConstructor
public class CalculoHidraulicoServiceImpl implements ICalculoHidraulicoService {

    private final ICalculoHidraulicoRepo calculoRepo;
    private final ITuberiaRepo tuberiaRepo;
    private final IProyectoRepo proyectoRepo;

    private Proyecto proyecto = new Proyecto();

    @Override
    @Transactional
    public CalculoHidraulico calcularPorTuberia(Integer idTuberia, Integer idProyecto) {
        /*Proyecto proyecto = proyectoRepo.findById(idProyecto).orElseThrow(() ->
                new ModeloNotFoundException("Proyecto no encontrado"));*/
        buscarProyecto(idProyecto);
        Tuberia tuberia = tuberiaRepo.findById(idTuberia).orElseThrow(() ->
                new ModeloNotFoundException("Tubería no encontrada"));
        double pendiente = formatearNumero(calcularPendiente(tuberia), 4);
        double caudal = formatearNumero(calcularCaudal(tuberia), 4);
        double velocidad = formatearNumero(calcularVelocidad(caudal, tuberia), 4);
        CalculoHidraulico calculo = calculoRepo.findByTuberiaIdTuberia(idTuberia)
                .orElse(new CalculoHidraulico());
        calculo.setCaudal(caudal);
        calculo.setPendiente(pendiente);
        calculo.setVelocidad(velocidad);
        calculo.setTuberia(tuberia);
        return calculoRepo.save(calculo);
    }

    @Override
    @Transactional
    public List<CalculoHidraulico> calcularTodas(Integer idProyecto) {
        buscarProyecto(idProyecto);
        List<CalculoHidraulico> listaCalculos = new ArrayList<>();
        List<Tuberia> tuberias = tuberiaRepo.findAll();
        for (Tuberia t : tuberias) {
            double pendiente = formatearNumero(calcularPendiente(t), 4);
            double caudal = formatearNumero(calcularCaudal(t), 4);
            double velocidad = formatearNumero(calcularVelocidad(caudal, t), 4);
            CalculoHidraulico calculo = calculoRepo.findByTuberiaIdTuberia(t.getIdTuberia())
                    .orElse(new CalculoHidraulico());
            calculo.setCaudal(caudal);
            calculo.setPendiente(pendiente);
            calculo.setVelocidad(velocidad);
            calculo.setTuberia(t);
            listaCalculos.add(calculoRepo.save(calculo));
        }
        return listaCalculos;
    }

    @Override
    public List<CalculoHidraulicoDTO> obtenerTodos() {
        List<CalculoHidraulicoDTO> calculos = new ArrayList<>();
        calculoRepo.obtenerCalculosConPozos().forEach(c -> {
            calculos.add(new CalculoHidraulicoDTO(
                    Integer.parseInt(String.valueOf(c[0])),
                    Integer.parseInt(String.valueOf(c[1])),
                    String.valueOf(c[2]),
                    String.valueOf(c[3]),
                    Double.parseDouble(String.valueOf(c[4])),
                    Double.parseDouble(String.valueOf(c[5])),
                    Double.parseDouble(String.valueOf(c[6]))
            ));
        });
        return calculos;
    }

    private double calcularPendiente(Tuberia tuberia) {
        CotaAlturaTuberia cotasAlturas = tuberiaRepo.getCotasAlturasTuberias(tuberia.getIdTuberia());
        return ((cotasAlturas.getCotaInicio() - cotasAlturas.getCotaFin()) / tuberia.getLongitud()) * 100;
    }

    private double calcularCaudal(Tuberia tuberia) {
        double poblacion = (tuberia.getAreaAporte() / 100) * proyecto.getDensidadPoblacional();
        double caudal = (poblacion * proyecto.getDotacion()) / 86400; //En l/s
        System.out.println("Caudal l/s " + caudal);
        return caudal;
    }

    private double calcularVelocidad(double caudal, Tuberia tuberia) {
        double areaTuberia = (Math.PI * Math.pow((double) tuberia.getDiametro() / 1000, 2)) / 4;
        double velocidad = (caudal / 1000) / areaTuberia; //caudal / 1000 para obtener en m3/s
        System.out.println("Velocidad " + velocidad);
        return velocidad;
    }

    private void buscarProyecto(Integer idProyecto) {
        proyecto = proyectoRepo.findById(idProyecto).orElseThrow(() ->
                new ModeloNotFoundException("Proyecto no encontrado"));
    }
}
