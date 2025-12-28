package com.uce.sedral.utils;

import com.uce.sedral.models.dto.CotaAlturaTuberia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.PI;
import static com.uce.sedral.utils.ConstantesHidraulicas.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManejoCalculosHidraulicos {

    private double diametro;

    private double calcularPendiente(CotaAlturaTuberia cotaAlturaTuberia, double longitud) {
        return ((cotaAlturaTuberia.getCotaInicio() - cotaAlturaTuberia.getCotaFin()) / longitud) * 100;
    }

    private double calcularVelocidadManning(double pendiente, String material) {
        double manning = 0.00;
        if (material.equalsIgnoreCase("HS")) {
            manning = MANNING_HS;
        } else if (material.equalsIgnoreCase("PVC")) {
            manning = MANNING_PVC;
        }
        return (1 / manning) * pow(diametro / 4, (double) 2 / 3) * sqrt(pendiente);
    }

    private double calcularCaudalContinuidad(double velocidadManning) {
        return velocidadManning * PI * pow(diametro, 2) / 4;
    }

    private double calcularCaudalMedioDiario(double dotacion, double poblacion) {
        return poblacion * dotacion / 86400;
    }

    private double calcularCaudalTotal(double caudalMedioDiario, double areaAporte) {
        double caudalSanitario = caudalMedioDiario * 0.8;
        double caudalInfiltracion = areaAporte * 0.03;
        return caudalSanitario + CAUDAL_ILICITAS + caudalInfiltracion;
    }
}