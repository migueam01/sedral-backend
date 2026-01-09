package com.uce.sedral.utils;

public class ConversorUnidades {

    public static double convertirM3SegALitrosSeg(double valor) {
        return valor * 1000;
    }

    public static double convertirLitroDiaALitroSeg(double valor) {
        return valor / 86400;
    }

    public static double convertirMilimetrosAMetros(int valor) {
        return (double) valor / 1000;
    }
}