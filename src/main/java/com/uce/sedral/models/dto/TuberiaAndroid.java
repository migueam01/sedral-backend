package com.uce.sedral.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TuberiaAndroid implements Serializable {
    private Integer idTuberia;
    private String orientacion;
    private double base;
    private double corona;
    private int diametro;
    private String material;
    private String flujo;
    private String funciona;
    private Integer idPozoInicio;
    private Integer idPozoFin;
}
