package com.uce.sedral.services;

import com.uce.sedral.models.dto.ConsultaTuberiaDTO;
import com.uce.sedral.models.dto.RangoTuberiasLongDTO;
import com.uce.sedral.models.dto.TuberiaAndroid;
import com.uce.sedral.models.dto.TuberiaMapa;
import com.uce.sedral.models.entities.Tuberia;

import java.util.List;

public interface ITuberiaService extends ICRUD<Tuberia> {

    List<Tuberia> findTuberiasByPozoInicio(Integer pozoId);

    List<TuberiaAndroid> getAllAndroid();

    List<TuberiaMapa> getAllTuberiasWithCoordinates();

    //Servicios para dashboard
    int contarTodos();

    double sumarLongitudes();

    List<ConsultaTuberiaDTO> listarTuberiasPorFuncionamiento();

    List<ConsultaTuberiaDTO> listarTuberiasPorDiametro();

    List<ConsultaTuberiaDTO> listarTuberiasPorMaterial();

    List<RangoTuberiasLongDTO> listarTuberiasRangoLongitud();
}
