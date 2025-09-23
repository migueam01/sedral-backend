package com.uce.sedral.services;

import com.uce.sedral.models.entities.Descarga;

public interface IDescargaService extends ICRUD<Descarga> {

    Descarga buscarPorNombre(String nombre);
}
