package com.uce.sedral.services;

import com.uce.sedral.models.dto.ProyectoAndroid;
import com.uce.sedral.models.entities.Proyecto;

import java.util.List;

public interface IProyectoService extends ICRUD<Proyecto> {

    Proyecto buscarPorNombre(String nombre);

    List<Proyecto> findProyectosByGadm(Integer gadmId);

    List<ProyectoAndroid> getAllAndroid();

    Proyecto saveProyectoAndroid(ProyectoAndroid proyectoAndroid);

    Proyecto updateProyectoAndroid(ProyectoAndroid proyectoAndroid);
}
