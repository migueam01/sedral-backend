package com.uce.sedral.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PozoMapa implements Serializable {
    private Integer idPozo;
    private String nombre;
    private String tapado;
    private double cota;
    private String estado;
    private double altura;
    private double latitud;
    private double longitud;
}
