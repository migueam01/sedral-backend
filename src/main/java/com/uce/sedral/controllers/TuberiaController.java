package com.uce.sedral.controllers;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.dto.ConsultaTuberiaDTO;
import com.uce.sedral.models.dto.RangoTuberiasLongDTO;
import com.uce.sedral.models.dto.TuberiaAndroid;
import com.uce.sedral.models.dto.TuberiaMapa;
import com.uce.sedral.models.entities.Tuberia;
import com.uce.sedral.services.ITuberiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/tuberias")
@RequiredArgsConstructor
public class TuberiaController {

    private final ITuberiaService service;

    @GetMapping("/mapas")
    public ResponseEntity<List<TuberiaMapa>> getTuberiasMapa() {
        return ResponseEntity.ok(service.getAllTuberiasWithCoordinates());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tuberia>> findAll() {
        List<Tuberia> tuberias = service.findAll();
        return ResponseEntity.ok(tuberias);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tuberia> findById(@PathVariable("id") Integer idTuberia) {
        Tuberia tuberia = service.findById(idTuberia);
        if (tuberia.getIdTuberia() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idTuberia);
        }
        return new ResponseEntity<>(tuberia, HttpStatus.OK);
    }

    @GetMapping(value = "/pozos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tuberia>> findTuberiaByPozoInicio(@PathVariable("id") Integer idPozo) {
        List<Tuberia> tuberias = service.findTuberiasByPozoInicio(idPozo);
        return tuberias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tuberias);
    }

    @GetMapping(value = "android", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TuberiaAndroid>> getAllAndroid() {
        List<TuberiaAndroid> tuberiasAndroid = service.getAllAndroid();
        return ResponseEntity.ok(tuberiasAndroid);
    }

    @GetMapping(value = "/totales")
    public ResponseEntity<Integer> obtenerTotal() {
        return ResponseEntity.ok(service.contarTodos());
    }

    @GetMapping(value = "/suma-longitudes")
    public ResponseEntity<Double> obtenerSumaLongitudes() {
        return ResponseEntity.ok(service.sumarLongitudes());
    }

    @GetMapping(value = "/funcionamientos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConsultaTuberiaDTO>> listarTuberiasFuncionamiento() {
        List<ConsultaTuberiaDTO> consultas;
        consultas = service.listarTuberiasPorFuncionamiento();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping(value = "/diametros", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConsultaTuberiaDTO>> listarTuberiasDiametro() {
        List<ConsultaTuberiaDTO> consultas;
        consultas = service.listarTuberiasPorDiametro();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping(value = "/materiales", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConsultaTuberiaDTO>> listarTuberiasMaterial() {
        List<ConsultaTuberiaDTO> consultas;
        consultas = service.listarTuberiasPorMaterial();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping(value = "/rangos-longitud", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RangoTuberiasLongDTO>> listarTuberiasRangoLongitud() {
        List<RangoTuberiasLongDTO> consultas;
        consultas = service.listarTuberiasRangoLongitud();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tuberia> save(@Valid @RequestBody Tuberia tuberiaBody) {
        Tuberia tuberia = service.save(tuberiaBody);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(tuberia.getIdTuberia())
                .toUri();
        return ResponseEntity.created(ubicacion).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tuberia> update(@Valid @RequestBody Tuberia tuberia) {
        service.update(tuberia);
        return ResponseEntity.ok(tuberia);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idTuberia) {
        Tuberia tuberia = service.findById(idTuberia);
        if (tuberia.getIdTuberia() == null) {
            throw new ModeloNotFoundException("Id no encontrado " + idTuberia);
        } else {
            service.delete(idTuberia);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
