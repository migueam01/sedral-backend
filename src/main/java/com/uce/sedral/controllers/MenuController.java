package com.uce.sedral.controllers;

import com.uce.sedral.models.dto.UsuarioDTO;
import com.uce.sedral.models.entities.Menu;
import com.uce.sedral.services.IMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final IMenuService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Menu>> listar() {
        List<Menu> menus;
        menus = service.findAll();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @PostMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Menu>> listarMenuUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        List<Menu> menus = service.listarMenuPorUsuario(usuarioDTO.getUsername());
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
}