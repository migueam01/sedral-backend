package com.uce.sedral.services;

import com.uce.sedral.models.entities.Menu;

import java.util.List;

public interface IMenuService extends ICRUD<Menu> {

    List<Menu> listarMenuPorUsuario(String nombre);
}
