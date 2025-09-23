package com.uce.sedral.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RangoTuberiasLongDTO implements Serializable {
    private String rangoLongitud;
    private int cantidad;
    private double longitudTotal;
    private double longitudPromedio;
}
