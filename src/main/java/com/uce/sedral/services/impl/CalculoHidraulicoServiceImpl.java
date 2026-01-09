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
import com.uce.sedral.utils.ManejoCalculosHidraulicos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.uce.sedral.utils.Operaciones.*;
import static com.uce.sedral.utils.ConversorUnidades.convertirMilimetrosAMetros;

@Service
@RequiredArgsConstructor
public class CalculoHidraulicoServiceImpl implements ICalculoHidraulicoService {

    private final ICalculoHidraulicoRepo calculoRepo;
    private final ITuberiaRepo tuberiaRepo;
    private final IProyectoRepo proyectoRepo;

    private double pendiente;
    private double caudalParcial;
    private double velocidadManningParcial;
    private double velocidadManningLleno;
    private double caudalLleno;
    private double areaParcial;
    private double areaLlena;
    private double manning;

    @Override
    @Transactional
    public CalculoHidraulico calcularPorTuberia(Integer idTuberia, Integer idProyecto) {
        buscarProyecto(idProyecto);
        double relacionCaudal;
        double relacionVelocidad;
        double relacionArea;
        Tuberia tuberia = tuberiaRepo.findById(idTuberia).orElseThrow(() ->
                new ModeloNotFoundException("Tubería no encontrada"));
        CalculoHidraulico calculo = calculoRepo.findByTuberiaIdTuberia(idTuberia)
                .orElse(new CalculoHidraulico());
        calcularDatosHidraulicos(tuberia);
        calculo.setCaudal(caudalParcial);
        calculo.setPendiente(pendiente);
        calculo.setVelocidad(velocidadManningParcial);
        calculo.setManning(manning);
        relacionCaudal = formatearNumero(caudalParcial / caudalLleno, 4);
        relacionVelocidad = formatearNumero(velocidadManningParcial / velocidadManningLleno, 4);
        relacionArea = formatearNumero(areaParcial / areaLlena, 4);
        calculo.setRelacionCaudal(relacionCaudal);
        calculo.setRelacionVelocidad(relacionVelocidad);
        calculo.setRelacionArea(relacionArea);
        calculo.setTuberia(tuberia);
        return calculoRepo.save(calculo);
    }

    @Override
    @Transactional
    public List<CalculoHidraulico> calcularTodas(Integer idProyecto) {
        buscarProyecto(idProyecto);
        double relacionCaudal;
        double relacionVelocidad;
        double relacionArea;
        List<CalculoHidraulico> listaCalculos = new ArrayList<>();
        List<Tuberia> tuberias = tuberiaRepo.findAll();
        for (Tuberia t : tuberias) {
            calcularDatosHidraulicos(t);
            CalculoHidraulico calculo = calculoRepo.findByTuberiaIdTuberia(t.getIdTuberia())
                    .orElse(new CalculoHidraulico());
            calculo.setCaudal(caudalParcial);
            calculo.setPendiente(pendiente);
            calculo.setVelocidad(velocidadManningParcial);
            calculo.setManning(manning);
            relacionCaudal = formatearNumero(caudalParcial / caudalLleno, 4);
            relacionVelocidad = formatearNumero(velocidadManningParcial / velocidadManningLleno, 4);
            relacionArea = formatearNumero(areaParcial / areaLlena, 4);
            calculo.setRelacionCaudal(relacionCaudal);
            calculo.setRelacionVelocidad(relacionVelocidad);
            calculo.setRelacionArea(relacionArea);
            calculo.setTuberia(t);
            listaCalculos.add(calculoRepo.save(calculo));
        }
        return listaCalculos;
    }

    public void calcularDatosHidraulicos(Tuberia tuberia) {
        ManejoCalculosHidraulicos calculosHidraulicos = new ManejoCalculosHidraulicos(convertirMilimetrosAMetros(tuberia.getDiametro()),
                tuberia.getCalado());
        CotaAlturaTuberia cotasAlturas = tuberiaRepo.getCotasAlturasTuberias(tuberia.getIdTuberia());
        pendiente = formatearNumero(calculosHidraulicos.calcularPendiente(cotasAlturas, tuberia.getLongitud()), 2);
        areaParcial = formatearNumero(calculosHidraulicos.calcularAreaParcial(), 2);
        areaLlena = formatearNumero(calculosHidraulicos.calcularAreaLlena(), 2);
        velocidadManningParcial = formatearNumero(calculosHidraulicos.calcularVelocidadManningParcial(pendiente, tuberia.getMaterial()),
                2);
        caudalParcial = formatearNumero(calculosHidraulicos.calcularCaudalParcial(velocidadManningParcial, areaParcial),
                2);
        velocidadManningLleno = formatearNumero(calculosHidraulicos.calcularVelocidadManningLleno(pendiente, tuberia.getMaterial()),
                2);
        caudalLleno = formatearNumero(calculosHidraulicos.calcularCaudalLleno(velocidadManningLleno, areaLlena),
                2);
        manning = calculosHidraulicos.getManning();
    }

    @Override
    public List<CalculoHidraulicoDTO> obtenerTodos() {
        List<CalculoHidraulicoDTO> calculos = new ArrayList<>();
        calculoRepo.obtenerCalculosConPozos().forEach(c -> {
            calculos.add(new CalculoHidraulicoDTO(
                    Integer.parseInt(String.valueOf(c[0])), //id cálculo
                    Integer.parseInt(String.valueOf(c[1])), //id tubería
                    String.valueOf(c[2]), //Nombre pozo inicio
                    String.valueOf(c[3]), //Nombre pozo fin
                    Integer.parseInt(String.valueOf(c[4])), //Diámetro
                    String.valueOf(c[5]), //Material
                    Double.parseDouble(String.valueOf(c[6])), //Calado
                    Double.parseDouble(String.valueOf(c[7])), //Manning
                    Double.parseDouble(String.valueOf(c[8])), //Pendiente
                    Double.parseDouble(String.valueOf(c[9])), //Velocidad
                    Double.parseDouble(String.valueOf(c[10])), //Caudal
                    Double.parseDouble(String.valueOf(c[11])), //Relación de caudal
                    Double.parseDouble(String.valueOf(c[12])), //Relación de velocidad
                    Double.parseDouble(String.valueOf(c[13])) //Relación de área
            ));
        });
        return calculos;
    }

    private void buscarProyecto(Integer idProyecto) {
        Proyecto proyecto = proyectoRepo.findById(idProyecto).orElseThrow(() ->
                new ModeloNotFoundException("Proyecto no encontrado"));
    }
}