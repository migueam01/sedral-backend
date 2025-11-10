package com.uce.sedral.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PerfilDTO implements Serializable {
    private Integer idPozoInicio;
    private String nombrePozoInicio;
    private double alturaInicio;
    private double cotaTerrenoInicio;
    private Integer idPozoFin;
    private String nombrePozoFin;
    private double alturaFin;
    private double cotaTerrenoFin;
    private double pendiente;
    private double caudal;
    private double velocidad;
    private int diametro;
    private String material;
    private double longitud;
}