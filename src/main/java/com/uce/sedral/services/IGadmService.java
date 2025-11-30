package com.uce.sedral.services;

import com.uce.sedral.models.entities.Gadm;

public interface IGadmService extends ICRUD<Gadm> {

    Gadm buscarPorNombre(String nombre);
}