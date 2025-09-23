package com.uce.sedral.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExceptionResponse {
    private Date fechaActual;
    private String mensaje;
    private String detalles;

    public ExceptionResponse(Date fechaActual, String mensaje, String detalles) {
        this.fechaActual = fechaActual;
        this.mensaje = mensaje;
        this.detalles = detalles;
    }
}
