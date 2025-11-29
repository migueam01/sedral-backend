package com.uce.sedral.services;

import com.uce.sedral.models.entities.Rol;

public interface IRolService extends ICRUD<Rol> {

    Rol buscarPorNombre(String nombre);
}