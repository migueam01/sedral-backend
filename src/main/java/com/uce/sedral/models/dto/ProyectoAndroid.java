package com.uce.sedral.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoAndroid implements Serializable {
    private Integer idProyecto;
    private String nombre;
    private String alias;
    private Integer idGadm;
}
