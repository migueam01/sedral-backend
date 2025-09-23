package com.uce.sedral.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CotaAlturaTuberia implements Serializable {
    private double cotaInicio;
    private double alturaInicio;
    private double cotaFin;
    private double alturaFin;
}
