package com.uce.sedral.services;

import com.uce.sedral.models.dto.PerfilDTO;

import java.util.List;

public interface IPerfilService {

    List<PerfilDTO> obtenerPerfil(List<Integer> idsPozos);
}
