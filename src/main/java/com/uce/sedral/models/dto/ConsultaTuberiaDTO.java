package com.uce.sedral.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ConsultaTuberiaDTO implements Serializable {
    private String valor;
    private int cantidad;
    private double porcentajeCantidad;
    private double longitudTotal;
    private double longitudPromedio;
    private double porcentajeLongitud;
}
