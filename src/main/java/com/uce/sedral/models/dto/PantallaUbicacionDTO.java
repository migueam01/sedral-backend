package com.uce.sedral.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PantallaUbicacionDTO implements Serializable {
    private final String calleOE;
    private final String calleNS;
    @NotNull(message = "La coordenada norte no debe ser nullo")
    private final double coordNorte;
    @NotNull(message = "La coordenada este no debe ser nullo")
    private final double coordEste;
    @NotNull(message = "La cota no debe ser nullo")
    private final double cota;
    private final String zona;
    private final int srid;
    private final double aproximacion;
    private final String calzada;
    private final int actividadCompletada;
}
