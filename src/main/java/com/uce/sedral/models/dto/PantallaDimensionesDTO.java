package com.uce.sedral.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PantallaDimensionesDTO implements Serializable {
    @NotNull(message = "Las dimensiones no deben ser nulas")
    private final double dimensionTapa;
    @NotNull(message = "Las dimensiones no deben ser nulas")
    private final double alturaPozo;
    @NotNull(message = "Las dimensiones no deben ser nulas")
    private final double ancho;
    @NotEmpty(message = "El tipo de fluido no puede estar vacío")
    private final String fluido;
    @NotEmpty(message = "El estado del pozo no puede estar vacío")
    private final String estadoPozo;
    private final int actividadCompletada;
}
