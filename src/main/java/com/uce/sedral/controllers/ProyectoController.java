package com.uce.sedral.controllers;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.dto.ProyectoAndroid;
import com.uce.sedral.models.entities.Proyecto;
import com.uce.sedral.services.IProyectoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final IProyectoService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Proyecto>> findAll() {
        List<Proyecto> proyectos = service.findAll();
        return ResponseEntity.ok(proyectos);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proyecto> findById(@PathVariable("id") Integer idProyecto) {
        Proyecto proyectos = service.findById(idProyecto);
        if (proyectos.getIdProyecto() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idProyecto);
        }
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    @GetMapping(value = "/gadms/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Proyecto>> findProyectosByGadm(@PathVariable("id") Integer idGadm) {
        List<Proyecto> proyectos = service.findProyectosByGadm(idGadm);
        return proyectos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(proyectos);
    }

    @GetMapping(value = "android", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProyectoAndroid>> getAllAndroid() {
        List<ProyectoAndroid> proyectosAndroid = service.getAllAndroid();
        return ResponseEntity.ok(proyectosAndroid);
    }

    @PostMapping(value = "crea-android", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proyecto> saveAndroid(@Valid @RequestBody ProyectoAndroid proyectoAndroid) {
        Proyecto proyecto = service.saveProyectoAndroid(proyectoAndroid);
        return ResponseEntity.status(HttpStatus.CREATED).body(proyecto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proyecto> save(@Valid @RequestBody Proyecto proyectoBody) {
        Proyecto proyecto = service.save(proyectoBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(proyecto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proyecto> update(@Valid @RequestBody Proyecto proyecto) {
        service.update(proyecto);
        return ResponseEntity.ok(proyecto);
    }

    @PutMapping(value = "actualiza-android", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proyecto> updateAndroid(@Valid @RequestBody ProyectoAndroid proyectoAndroid) {
        return ResponseEntity.ok(service.updateProyectoAndroid(proyectoAndroid));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idProyecto) {
        Proyecto proyecto = service.findById(idProyecto);
        if (proyecto.getIdProyecto() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idProyecto);
        } else {
            service.delete(idProyecto);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
