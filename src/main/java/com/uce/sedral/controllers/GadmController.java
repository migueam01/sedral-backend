package com.uce.sedral.controllers;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.entities.Gadm;
import com.uce.sedral.services.IGadmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gadms")
@RequiredArgsConstructor
public class GadmController {

    private final IGadmService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Gadm>> findAll() {
        List<Gadm> gadms = service.findAll();
        return ResponseEntity.ok(gadms);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Gadm> findById(@PathVariable("id") Integer idGadm) {
        Gadm gadm = service.findById(idGadm);
        if (gadm.getIdGadm() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idGadm);
        }
        return new ResponseEntity<>(gadm, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Gadm> save(@Valid @RequestBody Gadm gadmBody) {
        Gadm gadm = service.save(gadmBody);
        /*URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(gadm.getIdGadm())
                .toUri();
        return ResponseEntity.created(ubicacion).build();*/
        return ResponseEntity.status(HttpStatus.CREATED).body(gadm);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Gadm> update(@Valid @RequestBody Gadm gadm) {
        service.update(gadm);
        return ResponseEntity.ok(gadm);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idGadm) {
        Gadm gadm = service.findById(idGadm);
        if (gadm.getIdGadm() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idGadm);
        } else {
            service.delete(idGadm);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}