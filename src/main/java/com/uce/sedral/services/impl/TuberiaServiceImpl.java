package com.uce.sedral.services.impl;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.dto.*;
import com.uce.sedral.models.entities.Pozo;
import com.uce.sedral.models.entities.Tuberia;
import com.uce.sedral.repositories.IPozoRepo;
import com.uce.sedral.repositories.ITuberiaRepo;
import com.uce.sedral.services.GeomUtils;
import com.uce.sedral.services.ITuberiaService;
import com.uce.sedral.utils.Operaciones;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Service
@RequiredArgsConstructor
public class TuberiaServiceImpl implements ITuberiaService {

    private final ITuberiaRepo repo;
    private final IPozoRepo pozoRepo;

    @Override
    public Tuberia save(Tuberia tuberia) {
        Pozo pozoInicio = pozoRepo.findById(tuberia.getPozoInicio().getIdPozo()).orElseThrow(() ->
                new ModeloNotFoundException("Pozo inicio no encontrado"));
        Pozo pozoFin = pozoRepo.findById(tuberia.getPozoFin().getIdPozo()).orElseThrow(() ->
                new ModeloNotFoundException("Pozo fin no encontrado"));
        //Calcular longitud de la tubería
        if (tuberia.getLongitud() == 0) {
            double longitudTuberia = sqrt(pow(pozoFin.getEsteMovil() - pozoInicio.getEsteMovil(), 2)
                    + pow(pozoFin.getNorteMovil() - pozoInicio.getNorteMovil(), 2));
            tuberia.setLongitud(Operaciones.formatearNumero(longitudTuberia, 2));
        }
        tuberia.setSrid(pozoInicio.getSrid());
        tuberia.setGeom(GeomUtils.crearLineaUTM(pozoInicio.getGeom(), pozoFin.getGeom(),
                pozoInicio.getSrid()));
        return repo.save(tuberia);
    }

    @Override
    public Tuberia update(Tuberia tuberia) {
        Pozo pozoInicio = pozoRepo.findById(tuberia.getPozoInicio().getIdPozo()).orElseThrow(() ->
                new ModeloNotFoundException("Pozo inicio no encontrado"));
        Pozo pozoFin = pozoRepo.findById(tuberia.getPozoFin().getIdPozo()).orElseThrow(() ->
                new ModeloNotFoundException("Pozo fin no encontrado"));
        if (tuberia.getLongitud() == 0) {
            double longitudTuberia = sqrt(pow(pozoFin.getEsteMovil() - pozoInicio.getEsteMovil(), 2)
                    + pow(pozoFin.getNorteMovil() - pozoInicio.getNorteMovil(), 2));
            tuberia.setLongitud(Operaciones.formatearNumero(longitudTuberia, 2));
        }
        tuberia.setSrid(pozoInicio.getSrid());
        tuberia.setGeom(GeomUtils.crearLineaUTM(pozoInicio.getGeom(), pozoFin.getGeom(),
                tuberia.getSrid()));
        return repo.save(tuberia);
    }

    @Override
    public Tuberia findById(Integer id) {
        Optional<Tuberia> tuberia = repo.findById(id);
        return tuberia.orElseGet(Tuberia::new);
    }

    @Override
    public List<Tuberia> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<Tuberia> findTuberiasByPozoInicio(Integer pozoId) {
        return repo.findTuberiasByPozoInicio(pozoId);
    }

    @Override
    public List<TuberiaAndroid> getAllAndroid() {
        List<Tuberia> tuberias = repo.findAll();
        return tuberias.stream()
                .map(t -> new TuberiaAndroid(
                        t.getIdTuberia(),
                        t.getOrientacion(),
                        t.getBase(),
                        t.getCorona(),
                        t.getDiametro(),
                        t.getMaterial(),
                        t.getFlujo(),
                        t.getFunciona(),
                        t.isSincronizado(),
                        t.getPozoInicio().getIdPozo(),
                        t.getPozoFin().getIdPozo()
                )).toList();
    }

    @Override
    public List<TuberiaMapa> getAllTuberiasWithCoordinates() {
        List<Object[]> resultados = repo.findTuberiasWithCoordinates();
        List<TuberiaMapa> tuberiasMapa = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Integer idTuberia = ((Integer) resultado[0]);
            Integer diametro = (Integer) resultado[1];
            String material = (String) resultado[2];
            String flujo = (String) resultado[3];
            String funciona = (String) resultado[4];
            String wkt = (String) resultado[5];

            TuberiaMapa tuberiaMapa = new TuberiaMapa();
            tuberiaMapa.setIdTuberia(idTuberia);
            tuberiaMapa.setDiametro(diametro);
            tuberiaMapa.setMaterial(material);
            tuberiaMapa.setFlujo(flujo);
            tuberiaMapa.setFunciona(funciona);
            tuberiaMapa.setCoordenadas(parseWKTtoCoordinates(wkt));
            tuberiasMapa.add(tuberiaMapa);
        }
        return tuberiasMapa;
    }

    @Override
    public int contarTodos() {
        return repo.countAll();
    }

    @Override
    public double sumarLongitudes() {
        return repo.sumAllLongitudes();
    }

    @Override
    public List<ConsultaTuberiaDTO> listarTuberiasPorFuncionamiento() {
        List<ConsultaTuberiaDTO> consultas = new ArrayList<>();
        repo.getTuberiasByFuncionamiento().forEach(x -> {
            ConsultaTuberiaDTO dto = new ConsultaTuberiaDTO();
            dto.setValor(String.valueOf(x[0]));
            dto.setCantidad(Integer.parseInt(String.valueOf(x[1])));
            dto.setPorcentajeCantidad(Double.parseDouble(String.valueOf(x[2])));
            dto.setLongitudTotal(Double.parseDouble(String.valueOf(x[3])));
            dto.setLongitudPromedio(Double.parseDouble(String.valueOf(x[4])));
            dto.setPorcentajeLongitud(Double.parseDouble(String.valueOf(x[5])));
            consultas.add(dto);
        });
        return consultas;
    }

    @Override
    public List<ConsultaTuberiaDTO> listarTuberiasPorDiametro() {
        List<ConsultaTuberiaDTO> consultas = new ArrayList<>();
        repo.getTuberiasByDiametro().forEach(x -> {
            ConsultaTuberiaDTO dto = new ConsultaTuberiaDTO();
            dto.setValor(String.valueOf(x[0]));
            dto.setCantidad(Integer.parseInt(String.valueOf(x[1])));
            dto.setPorcentajeCantidad(Double.parseDouble(String.valueOf(x[2])));
            dto.setLongitudTotal(Double.parseDouble(String.valueOf(x[3])));
            dto.setLongitudPromedio(Double.parseDouble(String.valueOf(x[4])));
            dto.setPorcentajeLongitud(Double.parseDouble(String.valueOf(x[5])));
            consultas.add(dto);
        });
        return consultas;
    }

    @Override
    public List<ConsultaTuberiaDTO> listarTuberiasPorMaterial() {
        List<ConsultaTuberiaDTO> consultas = new ArrayList<>();
        repo.getTuberiasByMaterial().forEach(x -> {
            ConsultaTuberiaDTO dto = new ConsultaTuberiaDTO();
            dto.setValor(String.valueOf(x[0]));
            dto.setCantidad(Integer.parseInt(String.valueOf(x[1])));
            dto.setPorcentajeCantidad(Double.parseDouble(String.valueOf(x[2])));
            dto.setLongitudTotal(Double.parseDouble(String.valueOf(x[3])));
            dto.setLongitudPromedio(Double.parseDouble(String.valueOf(x[4])));
            dto.setPorcentajeLongitud(Double.parseDouble(String.valueOf(x[5])));
            consultas.add(dto);
        });
        return consultas;
    }

    @Override
    public List<RangoTuberiasLongDTO> listarTuberiasRangoLongitud() {
        List<RangoTuberiasLongDTO> rangos = new ArrayList<>();
        repo.getTuberiasByRangoLongitud().forEach(x -> {
            RangoTuberiasLongDTO dto = new RangoTuberiasLongDTO();
            dto.setRangoLongitud(String.valueOf(x[0]));
            dto.setCantidad(Integer.parseInt(String.valueOf(x[1])));
            dto.setLongitudTotal(Double.parseDouble(String.valueOf(x[2])));
            dto.setLongitudPromedio(Double.parseDouble(String.valueOf(x[3])));
            rangos.add(dto);
        });
        return rangos;
    }

    private List<Coordenada> parseWKTtoCoordinates(String wkt) {
        List<Coordenada> coordenadas = new ArrayList<>();
        // Patrón para extraer coordenadas de LINESTRING
        Pattern pattern = Pattern.compile("(-?\\d+\\.?\\d*)\\s+(-?\\d+\\.?\\d*)");
        Matcher matcher = pattern.matcher(wkt);

        while (matcher.find()) {
            double longitud = Double.parseDouble(matcher.group(1));
            double latitud = Double.parseDouble(matcher.group(2));
            coordenadas.add(new Coordenada(latitud, longitud));
        }
        return coordenadas;
    }
}