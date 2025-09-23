package com.uce.sedral.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RangoPozoAlturaDTO implements Serializable {
    private String rangoAltura;
    private int cantidad;
}
