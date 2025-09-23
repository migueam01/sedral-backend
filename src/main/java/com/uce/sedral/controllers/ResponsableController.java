package com.uce.sedral.controllers;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.entities.Responsable;
import com.uce.sedral.services.IResponsableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/responsables")
@RequiredArgsConstructor
public class ResponsableController {

    private final IResponsableService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Responsable>> findAll() {
        List<Responsable> responsables = service.findAll();
        return ResponseEntity.ok(responsables);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Responsable> findById(@PathVariable("id") Integer idResponsable) {
        Responsable responsable = service.findById(idResponsable);
        if (responsable.getIdResponsable() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idResponsable);
        }
        return new ResponseEntity<>(responsable, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Responsable> save(@Valid @RequestBody Responsable responsableBody) {
        Responsable responsable = service.save(responsableBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(responsable);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Responsable> update(@Valid @RequestBody Responsable responsable) {
        service.update(responsable);
        return ResponseEntity.ok(responsable);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idResponsable) {
        Responsable responsable = service.findById(idResponsable);
        if (responsable.getIdResponsable() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idResponsable);
        } else {
            service.delete(idResponsable);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
