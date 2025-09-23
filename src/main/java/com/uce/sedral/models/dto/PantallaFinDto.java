package com.uce.sedral.models.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PantallaFinDto implements Serializable {
    private final String observacion;
    private final int actividadCompletada;
}
