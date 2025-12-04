package com.uce.sedral.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class TuberiaMapa implements Serializable {
    private Integer idTuberia;
    private double diametro;
    private String material;
    private String funciona;
    private double pendiente;
    private double caudal;
    private double velocidad;
    private List<Coordenada> coordenadas;
}