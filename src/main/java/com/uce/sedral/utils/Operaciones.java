package com.uce.sedral.utils;

public class Operaciones {

    public static double formatearNumero(double numero, int numeroDecimales) {
        return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
    }
}
