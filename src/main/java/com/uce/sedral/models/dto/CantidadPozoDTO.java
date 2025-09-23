package com.uce.sedral.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CantidadPozoDTO implements Serializable {
    private String valor;
    private int cantidad;
    private double porcentaje;
}
