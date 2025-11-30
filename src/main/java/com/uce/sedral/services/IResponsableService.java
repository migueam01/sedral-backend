package com.uce.sedral.services;

import com.uce.sedral.models.entities.Responsable;

public interface IResponsableService extends ICRUD<Responsable> {

    Responsable buscarPorUsername(String username);
}