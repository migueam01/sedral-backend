package com.uce.sedral.controllers;

import com.uce.sedral.models.dto.PerfilDTO;
import com.uce.sedral.services.IPerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/perfiles")
@RequiredArgsConstructor
public class PerfilController {

    private final IPerfilService service;

    @PostMapping
    public ResponseEntity<List<PerfilDTO>> obtenerPerfil(@RequestBody List<Integer> idsPozos) {

        if (idsPozos == null || idsPozos.size() < 2) {
            return ResponseEntity.badRequest().build();
        }

        List<PerfilDTO> perfil = service.obtenerPerfil(idsPozos);

        if (perfil.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(perfil);
    }
}
