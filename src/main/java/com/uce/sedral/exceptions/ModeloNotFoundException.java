package com.uce.sedral.exceptions;

public class ModeloNotFoundException extends RuntimeException {

    public ModeloNotFoundException(String mensaje) {
        super(mensaje);
    }
}
