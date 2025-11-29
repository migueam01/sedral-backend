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
public class SectorAndroid implements Serializable {
    private Integer idSector;
    private String nombre;
    private Integer idProyecto;
}
