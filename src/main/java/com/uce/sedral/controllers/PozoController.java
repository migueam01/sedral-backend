package com.uce.sedral.controllers;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.dto.*;
import com.uce.sedral.models.entities.Pozo;
import com.uce.sedral.services.IPozoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/v1/pozos")
@RequiredArgsConstructor
public class PozoController {

    private final IPozoService service;

    @GetMapping(value = "obtiene-android", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PozoAndroid>> getAllAndroid() {
        List<PozoAndroid> pozos = service.getAllAndroid();
        return ResponseEntity.ok(pozos);
    }

    @GetMapping("/mapas")
    public ResponseEntity<List<PozoMapa>> getPozosMapa() {
        return ResponseEntity.ok(service.getAllPozosWithCoordinates());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Pozo>> findAll() {
        List<Pozo> pozos = service.findAll();
        return ResponseEntity.ok(pozos);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pozo> findById(@PathVariable("id") Integer idPozo) {
        Pozo pozo = service.findById(idPozo);
        if (pozo.getIdPozo() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idPozo);
        }
        return new ResponseEntity<>(pozo, HttpStatus.OK);
    }

    @GetMapping(value = "/buscar/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pozo> findByNombre(@PathVariable("nombre") String nombre) {
        Pozo pozo = service.buscarPorNombre(nombre);
        return new ResponseEntity<>(pozo, HttpStatus.OK);
    }

    @GetMapping(value = "/totales")
    public ResponseEntity<Integer> obtenerTotal() {
        return ResponseEntity.ok(service.contarTodos());
    }

    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StatAlturaPozoDTO>> listarStatsAlturas() {
        List<StatAlturaPozoDTO> alturas;
        alturas = service.listarStatsAltura();
        return new ResponseEntity<>(alturas, HttpStatus.OK);
    }

    @GetMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CantidadPozoDTO>> listarEstadoPozos() {
        List<CantidadPozoDTO> estados;
        estados = service.listarPozosPorEstado();
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }

    @GetMapping(value = "/tapados", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CantidadPozoDTO>> listarPozosTapados() {
        List<CantidadPozoDTO> tapados;
        tapados = service.listarPozosPorTapado();
        return new ResponseEntity<>(tapados, HttpStatus.OK);
    }

    @GetMapping(value = "/calzadas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CantidadPozoDTO>> listarPozosCalzadas() {
        List<CantidadPozoDTO> calzadas;
        calzadas = service.listarPozosPorCalzada();
        return new ResponseEntity<>(calzadas, HttpStatus.OK);
    }

    @GetMapping(value = "/rangos-altura", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RangoPozoAlturaDTO>> listarPozosRangosAlturas() {
        List<RangoPozoAlturaDTO> consultas;
        consultas = service.listarPozosRangoAltura();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pozo> save(@Valid @RequestBody Pozo pozoBody) {
        Pozo pozo = service.save(pozoBody);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(pozo.getIdPozo())
                .toUri();
        return ResponseEntity.created(ubicacion).build();
    }

    @PostMapping(value = "/android", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pozo> savePozo(@Valid @RequestBody CreacionPozoDTO creacionPozoDTO) {
        Pozo pozo = service.savePozoAndroid(creacionPozoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pozo);
    }

    @PostMapping(value = "/android/tuberia", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pozo> savePozoTuberia(@Valid @RequestBody CreaPozoTuberiaDTO creaPozoTuberiaDTO) {
        Pozo pozo = service.savePozoTuberia(creaPozoTuberiaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pozo);
    }

    @PutMapping(value = "/android/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreacionPozoDTO> updatePozo(@PathVariable("id") Integer idPozo,
                                                      @Valid @RequestBody CreacionPozoDTO dto) {
        service.updatePozoAndroid(idPozo, dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/ubicacion/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PantallaUbicacionDTO> updateUbicacion(@PathVariable("id") Integer idPozo,
                                                                @Valid @RequestBody PantallaUbicacionDTO dto) {
        service.updatePozoUbicacion(idPozo, dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/dimensiones/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PantallaDimensionesDTO> updateDimensiones(@PathVariable("id") Integer idPozo,
                                                                    @Valid @RequestBody PantallaDimensionesDTO dto) {
        service.updatePozoDimensiones(idPozo, dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/fin/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PantallaFinDto> updateFin(@PathVariable("id") Integer idPozo,
                                                    @Valid @RequestBody PantallaFinDto dto) {
        service.updatePozoFin(idPozo, dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pozo> update(@Valid @RequestBody Pozo pozo) {
        service.update(pozo);
        return ResponseEntity.ok(pozo);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idPozo) {
        Pozo pozo = service.findById(idPozo);
        if (pozo.getIdPozo() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idPozo);
        } else {
            service.delete(idPozo);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
