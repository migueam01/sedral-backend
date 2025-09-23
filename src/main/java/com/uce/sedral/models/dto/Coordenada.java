package com.uce.sedral.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Coordenada implements Serializable {
    private double latitud;
    private double longitud;

    public Coordenada(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
