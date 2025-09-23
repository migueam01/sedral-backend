package com.uce.sedral.controllers;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.entities.Descarga;
import com.uce.sedral.services.IDescargaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/descargas")
@RequiredArgsConstructor
public class DescargaController {

    private final IDescargaService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Descarga>> findAll() {
        List<Descarga> descargas = service.findAll();
        return ResponseEntity.ok(descargas);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Descarga> findById(@PathVariable("id") Integer idDescarga) {
        Descarga descarga = service.findById(idDescarga);
        if (descarga.getIdDescarga() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idDescarga);
        }
        return new ResponseEntity<>(descarga, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Descarga> save(@Valid @RequestBody Descarga descargaBody) {
        Descarga descarga = service.save(descargaBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(descarga);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Descarga> update(@Valid @RequestBody Descarga descarga) {
        service.update(descarga);
        return ResponseEntity.ok(descarga);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idDescarga) {
        Descarga descarga = service.findById(idDescarga);
        if (descarga.getIdDescarga() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idDescarga);
        } else {
            service.delete(idDescarga);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
