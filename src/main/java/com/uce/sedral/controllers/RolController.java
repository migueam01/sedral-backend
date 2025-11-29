package com.uce.sedral.controllers;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.entities.Rol;
import com.uce.sedral.services.IRolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/roles")
@RequiredArgsConstructor
public class RolController {

    private final IRolService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Rol>> findAll() {
        List<Rol> roles = service.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rol> findById(@PathVariable("id") Integer idRol) {
        Rol rol = service.findById(idRol);
        if (rol.getIdRol() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idRol);
        }
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rol> save(@Valid @RequestBody Rol rolBody) {
        Rol rol = service.save(rolBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(rol);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rol> update(@Valid @RequestBody Rol rol) {
        service.update(rol);
        return ResponseEntity.ok(rol);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idRol) {
        Rol rol = service.findById(idRol);
        if (rol.getIdRol() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idRol);
        } else {
            service.delete(idRol);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}