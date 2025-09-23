package com.uce.sedral.services.impl;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.dto.*;
import com.uce.sedral.models.entities.Descarga;
import com.uce.sedral.models.entities.Pozo;
import com.uce.sedral.models.entities.Responsable;
import com.uce.sedral.models.entities.Sector;
import com.uce.sedral.repositories.IDescargaRepo;
import com.uce.sedral.repositories.IPozoRepo;
import com.uce.sedral.repositories.IResponsableRepo;
import com.uce.sedral.repositories.ISectorRepo;
import com.uce.sedral.services.GeomUtils;
import com.uce.sedral.services.IPozoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.uce.sedral.utils.ManejoFechas.*;

@Service
@RequiredArgsConstructor
public class PozoServiceImpl implements IPozoService {

    private final IPozoRepo pozoRepo;
    private final ISectorRepo sectorRepo;
    private final IResponsableRepo responsableRepo;
    private final IDescargaRepo descargaRepo;


    @Override
    public Pozo save(Pozo pozo) {
        pozo.setGeom(GeomUtils.crearPuntoUTM(pozo.getEsteMovil(), pozo.getNorteMovil(), pozo.getSrid()));
        return pozoRepo.save(pozo);
    }

    @Override
    public Pozo update(Pozo pozo) {
        pozo.setGeom(GeomUtils.crearPuntoUTM(pozo.getEsteMovil(), pozo.getNorteMovil(), pozo.getSrid()));
        return pozoRepo.save(pozo);
    }

    @Override
    public Pozo findById(Integer id) {
        Optional<Pozo> pozo = pozoRepo.findById(id);
        return pozo.orElseGet(Pozo::new);
    }

    @Override
    public List<Pozo> findAll() {
        return pozoRepo.findAll();
    }

    @Override
    public void delete(Integer id) {
        pozoRepo.deleteById(id);
    }

    @Override
    public Pozo buscarPorNombre(String nombre) {
        return pozoRepo.findByNombre(nombre)
                .orElseThrow(() -> new ModeloNotFoundException("Pozo no encontrado"));
    }

    @Override
    public Pozo savePozoAndroid(CreacionPozoDTO dto) {
        Responsable responsable;
        Sector sector;
        Descarga descarga;
        Optional<Responsable> responsableOpt = responsableRepo.findById(dto.getIdResponsable());
        Optional<Sector> sectorOpt = sectorRepo.findById(dto.getIdSector());
        Optional<Descarga> descargaOpt = descargaRepo.findById(dto.getIdDescarga());
        if (responsableOpt.isPresent()) {
            responsable = responsableOpt.get();
        } else {
            throw new ModeloNotFoundException("Responsable no encontrado");
        }
        if (sectorOpt.isPresent()) {
            sector = sectorOpt.get();
        } else {
            throw new ModeloNotFoundException("Sector no encontrado");
        }
        if (descargaOpt.isPresent()) {
            descarga = descargaOpt.get();
        } else {
            throw new ModeloNotFoundException("Descarga no encontrada");
        }
        Pozo pozo = new Pozo();
        pozo.setNombre(dto.getNombre());
        pozo.setFechaCatastro(convertirFechaADateTime(dto.getFechaCatastro()));
        pozo.setFechaActualizacion(convertirFechaADateTime(dto.getFechaActualizacion()));
        pozo.setSincronizado(dto.isSincronizado());
        pozo.setTapado(dto.getTapado());
        pozo.setSistema(dto.getSistema());
        pozo.setPathMedia(dto.getPathMedia());
        pozo.setActividadCompletada(dto.getActividadCompletada());
        pozo.setResponsable(responsable);
        pozo.setSector(sector);
        pozo.setDescarga(descarga);
        return pozoRepo.save(pozo);
    }

    @Override
    public Pozo savePozoTuberia(CreaPozoTuberiaDTO creaPozoTuberiaDTO) {
        Responsable responsable;
        Sector sector;
        Optional<Responsable> responsableOpt = responsableRepo.findById(creaPozoTuberiaDTO.getIdResponsable());
        Optional<Sector> sectorOpt = sectorRepo.findById(creaPozoTuberiaDTO.getIdSector());
        if (responsableOpt.isPresent()) {
            responsable = responsableOpt.get();
        } else {
            throw new ModeloNotFoundException("Responsable no encontrado");
        }
        if (sectorOpt.isPresent()) {
            sector = sectorOpt.get();
        } else {
            throw new ModeloNotFoundException("Sector no encontrado");
        }
        Pozo pozo = new Pozo();
        pozo.setNombre(creaPozoTuberiaDTO.getNombrePozo());
        pozo.setActividadCompletada(creaPozoTuberiaDTO.getActividadSincronizada());
        pozo.setSector(sector);
        pozo.setResponsable(responsable);
        return pozoRepo.save(pozo);
    }

    @Override
    public void updatePozoAndroid(Integer idPozo, CreacionPozoDTO dto) {
        Pozo pozo = buscarPozo(idPozo);
        Responsable responsable = responsableRepo.findById(dto.getIdResponsable())
                .orElseThrow(() -> new ModeloNotFoundException("Responsable no encontrado"));
        Sector sector = sectorRepo.findById(dto.getIdSector())
                .orElseThrow(() -> new ModeloNotFoundException("Sector no encontrado"));
        Descarga descarga = descargaRepo.findById(dto.getIdDescarga())
                .orElseThrow(() -> new ModeloNotFoundException("Descarga no encontrada"));
        pozo.setNombre(dto.getNombre());
        pozo.setFechaCatastro(convertirFechaADateTime(dto.getFechaCatastro()));
        pozo.setSincronizado(dto.isSincronizado());
        pozo.setTapado(dto.getTapado());
        pozo.setSistema(dto.getSistema());
        pozo.setPathMedia(dto.getPathMedia());
        pozo.setActividadCompletada(dto.getActividadCompletada());
        pozo.setResponsable(responsable);
        pozo.setSector(sector);
        pozo.setDescarga(descarga);
        pozoRepo.save(pozo);
    }

    @Override
    public void updatePozoUbicacion(Integer idPozo, PantallaUbicacionDTO dto) {
        Pozo pozo = buscarPozo(idPozo);
        pozo.setCalleNS(dto.getCalleNS());
        pozo.setCalleOE(dto.getCalleOE());
        pozo.setNorteMovil(dto.getCoordNorte());
        pozo.setEsteMovil(dto.getCoordEste());
        pozo.setCotaMovil(dto.getCota());
        pozo.setZona(dto.getZona());
        pozo.setAproximacion(dto.getAproximacion());
        pozo.setCalzada(dto.getCalzada());
        pozo.setSrid(dto.getSrid());
        pozo.setActividadCompletada(dto.getActividadCompletada());
        pozo.setGeom(GeomUtils.crearPuntoUTM(dto.getCoordEste(), dto.getCoordNorte(), dto.getSrid()));
        pozoRepo.save(pozo);
    }

    @Override
    public void updatePozoDimensiones(Integer idPozo, PantallaDimensionesDTO dto) {
        Pozo pozo = buscarPozo(idPozo);
        pozo.setDimensionTapa(dto.getDimensionTapa());
        pozo.setAltura(dto.getAlturaPozo());
        pozo.setAncho(dto.getAncho());
        pozo.setFluido(dto.getFluido());
        pozo.setEstado(dto.getEstadoPozo());
        pozo.setActividadCompletada(dto.getActividadCompletada());
        pozoRepo.save(pozo);
    }

    @Override
    public void updatePozoFin(Integer idPozo, PantallaFinDto dto) {
        Pozo pozo = buscarPozo(idPozo);
        pozo.setObservacion(dto.getObservacion());
        pozo.setActividadCompletada(dto.getActividadCompletada());
        pozoRepo.save(pozo);
    }

    @Override
    public List<PozoAndroid> getAllAndroid() {
        List<Pozo> pozos = pozoRepo.findAll();
        return pozos.stream()
                .map(p -> new PozoAndroid(
                        p.getIdPozo(),
                        p.getNombre(),
                        p.getSistema(),
                        convertirFechaAString(p.getFechaCatastro()),
                        convertirFechaAString(p.getFechaActualizacion()),
                        p.getTapado(),
                        p.getNorteMovil(),
                        p.getEsteMovil(),
                        p.getCotaMovil(),
                        p.getAproximacion(),
                        p.getZona(),
                        p.getSrid(),
                        p.getEstado(),
                        p.getCalzada(),
                        p.getFluido(),
                        p.getDimensionTapa(),
                        p.getAltura(),
                        p.getAncho(),
                        p.getCalleOE(),
                        p.getCalleNS(),
                        p.getObservacion(),
                        p.getPathMedia(),
                        p.isSincronizado(),
                        p.getActividadCompletada(),
                        p.getSector().getIdSector(),
                        p.getResponsable().getIdResponsable(),
                        p.getDescarga().getIdDescarga()
                )).toList();
    }

    @Override
    public List<PozoMapa> getAllPozosWithCoordinates() {
        List<Object[]> resultados = pozoRepo.findPozosWithCoordinates();
        return resultados.stream().map(resultado -> {
            PozoMapa mapa = new PozoMapa();
            mapa.setIdPozo(((Integer) resultado[0]));
            mapa.setNombre(((String) resultado[1]));
            mapa.setTapado(((String) resultado[2]));
            mapa.setCota(((Double) resultado[3]));
            mapa.setEstado(((String) resultado[4]));
            mapa.setAltura(((Double) resultado[5]));
            mapa.setLatitud(((Double) resultado[6]));
            mapa.setLongitud(((Double) resultado[7]));
            return mapa;
        }).collect(Collectors.toList());
    }

    @Override
    public int contarTodos() {
        return pozoRepo.countAll();
    }

    @Override
    public List<StatAlturaPozoDTO> listarStatsAltura() {
        List<StatAlturaPozoDTO> stats = new ArrayList<>();
        pozoRepo.getStatsHeight().forEach(x -> {
            StatAlturaPozoDTO dto = new StatAlturaPozoDTO();
            dto.setAlturaPromedio(Double.parseDouble(String.valueOf(x[0])));
            dto.setAlturaMaxima(Double.parseDouble(String.valueOf(x[1])));
            dto.setAlturaMinima(Double.parseDouble(String.valueOf(x[2])));
            stats.add(dto);
        });
        return stats;
    }


    @Override
    public List<CantidadPozoDTO> listarPozosPorEstado() {
        List<CantidadPozoDTO> cantidades = new ArrayList<>();
        pozoRepo.getPozosByEstado().forEach(x -> {
            CantidadPozoDTO dto = new CantidadPozoDTO();
            dto.setValor(String.valueOf(x[0]));
            dto.setCantidad(Integer.parseInt(String.valueOf(x[1])));
            dto.setPorcentaje(Double.parseDouble(String.valueOf(x[2])));
            cantidades.add(dto);
        });
        return cantidades;
    }

    @Override
    public List<CantidadPozoDTO> listarPozosPorTapado() {
        List<CantidadPozoDTO> cantidades = new ArrayList<>();
        pozoRepo.getPozosByTapado().forEach(x -> {
            CantidadPozoDTO dto = new CantidadPozoDTO();
            dto.setValor(String.valueOf(x[0]));
            dto.setCantidad(Integer.parseInt(String.valueOf(x[1])));
            dto.setPorcentaje(Double.parseDouble(String.valueOf(x[2])));
            cantidades.add(dto);
        });
        return cantidades;
    }

    @Override
    public List<CantidadPozoDTO> listarPozosPorCalzada() {
        List<CantidadPozoDTO> cantidades = new ArrayList<>();
        pozoRepo.getPozosByCalzada().forEach(x -> {
            CantidadPozoDTO dto = new CantidadPozoDTO();
            dto.setValor(String.valueOf(x[0]));
            dto.setCantidad(Integer.parseInt(String.valueOf(x[1])));
            dto.setPorcentaje(Double.parseDouble(String.valueOf(x[2])));
            cantidades.add(dto);
        });
        return cantidades;
    }

    @Override
    public List<RangoPozoAlturaDTO> listarPozosRangoAltura() {
        List<RangoPozoAlturaDTO> rangos = new ArrayList<>();
        pozoRepo.getPozosByRangoAltura().forEach(x -> {
            RangoPozoAlturaDTO dto = new RangoPozoAlturaDTO();
            dto.setRangoAltura(String.valueOf(x[0]));
            dto.setCantidad(Integer.parseInt(String.valueOf(x[1])));
            rangos.add(dto);
        });
        return rangos;
    }

    private Pozo buscarPozo(Integer idPozo) {
        return pozoRepo.findById(idPozo)
                .orElseThrow(() -> new ModeloNotFoundException("Pozo no encontrado"));
    }
}
