package com.uce.sedral.models.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CreaPozoTuberiaDTO implements Serializable {

    @NotEmpty(message = "Nombre del pozo no debe estar vacío")
    private final String nombrePozo;
    //falta la fecha
    //falta el path de los archivos en el telefono
    private final int actividadSincronizada;
    private final Integer idSector;
    private final Integer idResponsable;
}
