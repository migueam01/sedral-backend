package com.uce.sedral.services;

import com.uce.sedral.models.dto.*;
import com.uce.sedral.models.entities.Pozo;

import java.util.List;

public interface IPozoService extends ICRUD<Pozo> {

    Pozo buscarPorNombre(String nombre);

    Pozo savePozoAndroid(CreacionPozoDTO creacionPozoDTO);

    Pozo savePozoTuberia(CreaPozoTuberiaDTO creaPozoTuberiaDTO);

    void updatePozoAndroid(Integer idPozo, CreacionPozoDTO creacionPozoDTO);

    void updatePozoUbicacion(Integer idPozo, PantallaUbicacionDTO dto);

    void updatePozoDimensiones(Integer idPozo, PantallaDimensionesDTO dto);

    void updatePozoFin(Integer idPozo, PantallaFinDto dto);

    List<PozoAndroid> getAllAndroid();

    List<PozoMapa> getAllPozosWithCoordinates();

    //Servicios para dashboard
    int contarTodos();

    List<StatAlturaPozoDTO> listarStatsAltura();

    List<CantidadPozoDTO> listarPozosPorEstado();

    List<CantidadPozoDTO> listarPozosPorTapado();

    List<CantidadPozoDTO> listarPozosPorCalzada();

    List<RangoPozoAlturaDTO> listarPozosRangoAltura();
}