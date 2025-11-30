package com.uce.sedral.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculoHidraulicoDTO {
    private Integer idCalculo;
    private Integer idTuberia;
    private String nombrePozoInicio;
    private String nombrePozoFin;
    private double pendiente;
    private double velocidad;
    private double caudal;
}