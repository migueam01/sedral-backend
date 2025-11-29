package com.uce.sedral.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PozoAndroid implements Serializable {
    private Integer idPozo;
    private String nombre;
    private String sistema;
    private String fechaCatastro;
    private String fechaActualizacion;
    private String tapado;
    private double coordNorte;
    private double coordEste;
    private double cota;
    private double aproximacion;
    private String zona;
    private int srid;
    private String estado;
    private String calzada;
    private String fluido;
    private double dimensionTapa;
    private double altura;
    private double ancho;
    private String calleOE;
    private String calleNS;
    private String observacion;
    private String pathMedia;
    private int actividadCompletada;
    private Integer idSector;
    private Integer idResponsable;
    private Integer idDescarga;
}
