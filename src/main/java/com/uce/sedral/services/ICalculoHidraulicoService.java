package com.uce.sedral.services;

import com.uce.sedral.models.entities.CalculoHidraulico;

import java.util.List;

public interface ICalculoHidraulicoService {

    CalculoHidraulico calcularPorTuberia(Integer idTuberia, Integer idProyecto);

    List<CalculoHidraulico> calcularTodas(Integer idProyecto);
}
