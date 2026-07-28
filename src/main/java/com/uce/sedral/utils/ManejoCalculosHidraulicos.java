package com.uce.sedral.utils;

import com.uce.sedral.models.dto.CotaAlturaTuberia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.sin;
import static com.uce.sedral.utils.ConstantesHidraulicas.*;

@Getter
@Setter
@NoArgsConstructor
public class ManejoCalculosHidraulicos {

    private double diametro;
    private double calado;
    private double anguloInferior;
    private double radioHidraulicoParcial;
    private double radioHidraulicoLleno;
    private double manning;

    public ManejoCalculosHidraulicos(double diametro, double calado) {
        this.diametro = diametro;
        this.calado = calado;
        this.anguloInferior = 2 * acos(1 - (2 * calado / diametro));
        this.radioHidraulicoParcial = (1 - (sin(anguloInferior) / anguloInferior)) * diametro / 4;
        this.radioHidraulicoLleno = diametro / 4;
    }

    public double calcularPendiente(CotaAlturaTuberia cotaAlturaTuberia, double longitud) {
        return (cotaAlturaTuberia.getCotaInicio() - cotaAlturaTuberia.getCotaFin()) / longitud;
    }

    public double calcularVelocidadManningParcial(double pendiente, String material) {
        manning = obtenerManning(material);
        return (1 / manning) * pow(radioHidraulicoParcial, (double) 2 / 3) * sqrt(pendiente);
    }

    public double calcularVelocidadManningLleno(double pendiente, String material) {
        manning = obtenerManning(material);
        return (1 / manning) * pow(radioHidraulicoLleno, (double) 2 / 3) * sqrt(pendiente);
    }

    private double obtenerManning(String material) {
        double manning = 0.00;
        if (material.equalsIgnoreCase("HS")) {
            manning = MANNING_HS;
        } else if (material.equalsIgnoreCase("PVC")) {
            manning = MANNING_PVC;
        }
        return manning;
    }

    public double calcularAreaParcial() {
        return (anguloInferior - sin(anguloInferior)) * pow(diametro, 2) / 8;
    }

    public double calcularAreaLlena() {
        return PI * pow(diametro, 2) / 4;
    }

    public double calcularCaudalParcial(double velocidadManningParcial, double areaParcial) {
        return velocidadManningParcial * areaParcial;
    }

    public double calcularCaudalLleno(double velocidadManningLleno, double areaLlena) {
        return velocidadManningLleno * areaLlena;
    }
}