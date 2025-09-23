package com.uce.sedral.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CreacionPozoDTO implements Serializable {
    @NotEmpty(message = "El nombre no puede estar vacío")
    private final String nombre;
    private final String fechaCatastro;
    private final String fechaActualizacion;
    private final boolean sincronizado;
    private final String tapado;
    @NotEmpty(message = "El tipo de sistema no puede estar vacío")
    private final String sistema;
    private final String pathMedia;
    private final int actividadCompletada;
    @NotNull(message = "El Sector no puede ser nulo")
    private final Integer idSector;
    @NotNull(message = "El Responsable no puede ser nulo")
    private final Integer idResponsable;
    @NotNull(message = "La Descarga no puede ser nulo")
    private final Integer idDescarga;
}
