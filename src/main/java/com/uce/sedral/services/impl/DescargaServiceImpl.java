package com.uce.sedral.services.impl;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.entities.Descarga;
import com.uce.sedral.repositories.IDescargaRepo;
import com.uce.sedral.repositories.IPozoRepo;
import com.uce.sedral.services.IDescargaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DescargaServiceImpl implements IDescargaService {

    private final IDescargaRepo descargaRepo;
    private final IPozoRepo pozoRepo;

    @Override
    public Descarga save(Descarga descarga) {
        return descargaRepo.save(descarga);
    }

    @Override
    public Descarga update(Descarga descarga) {
        return descargaRepo.save(descarga);
    }

    @Override
    public Descarga findById(Integer id) {
        Optional<Descarga> descarga = descargaRepo.findById(id);
        return descarga.orElseGet(Descarga::new);
    }

    @Override
    public List<Descarga> findAll() {
        return descargaRepo.findAll();
    }

    @Override
    public void delete(Integer id) {
        descargaRepo.deleteById(id);
    }

    @Override
    public Descarga buscarPorNombre(String nombre) {
        return descargaRepo.findByNombre(nombre)
                .orElseThrow(() -> new ModeloNotFoundException("Descarga no encontrado"));
    }
}
