package com.uce.sedral.controllers;

import com.uce.sedral.models.dto.CalculoHidraulicoDTO;
import com.uce.sedral.models.entities.CalculoHidraulico;
import com.uce.sedral.services.ICalculoHidraulicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/calculos")
@RequiredArgsConstructor
public class CalculoController {

    private final ICalculoHidraulicoService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CalculoHidraulicoDTO>> obtenerTodos() {
        List<CalculoHidraulicoDTO> calculos = service.obtenerTodos();
        return ResponseEntity.ok(calculos);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<List<CalculoHidraulico>> calcularTodos(@PathVariable("id") Integer idProyecto) {
        List<CalculoHidraulico> calculos = service.calcularTodas(idProyecto);
        return ResponseEntity.ok(calculos);
    }

    @PostMapping(value = "/{idTuberia}/{idProyecto}")
    public ResponseEntity<CalculoHidraulico> calcularPorTuberia(@PathVariable("idTuberia") Integer idTuberia,
                                                                @PathVariable("idProyecto") Integer idProyecto) {
        CalculoHidraulico calculo = service.calcularPorTuberia(idTuberia, idProyecto);
        return ResponseEntity.ok(calculo);
    }
}