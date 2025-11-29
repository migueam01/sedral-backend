package com.uce.sedral.controllers;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.dto.SectorAndroid;
import com.uce.sedral.models.entities.Sector;
import com.uce.sedral.services.ISectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/sectores")
@RequiredArgsConstructor
public class SectorController {

    private final ISectorService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Sector>> findAll() {
        List<Sector> sectores = service.findAll();
        return ResponseEntity.ok(sectores);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sector> findById(@PathVariable("id") Integer idSector) {
        Sector sector = service.findById(idSector);
        if (sector.getIdSector() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idSector);
        }
        return new ResponseEntity<>(sector, HttpStatus.OK);
    }

    @GetMapping(value = "/proyectos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Sector>> findSectoresByProyecto(@PathVariable("id") Integer idProyecto) {
        List<Sector> sectores = service.findSectoresByProyecto(idProyecto);
        return sectores.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(sectores);
    }

    @GetMapping(value = "android", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SectorAndroid>> getAllAndroid() {
        List<SectorAndroid> sectoresAndroid = service.getAllAndroid();
        return ResponseEntity.ok(sectoresAndroid);
    }

    @PostMapping(value = "crea-android", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sector> saveAndroid(@Valid @RequestBody SectorAndroid sectorAndroid) {
        Sector sector = service.saveSectorAndroid(sectorAndroid);
        return ResponseEntity.status(HttpStatus.CREATED).body(sector);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sector> save(@Valid @RequestBody Sector sectorBody) {
        Sector sector = service.save(sectorBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(sector);
    }

    @PutMapping(value = "actualiza-android", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sector> updateAndroid(@Valid @RequestBody SectorAndroid sectorAndroid) {
        return ResponseEntity.ok(service.updateSectorAndroid(sectorAndroid));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sector> update(@Valid @RequestBody Sector sector) {
        service.update(sector);
        return ResponseEntity.ok(sector);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idSector) {
        Sector sector = service.findById(idSector);
        if (sector.getIdSector() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idSector);
        } else {
            service.delete(idSector);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
