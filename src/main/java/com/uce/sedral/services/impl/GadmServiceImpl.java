package com.uce.sedral.services.impl;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.entities.Gadm;
import com.uce.sedral.repositories.IGadmRepo;
import com.uce.sedral.services.IGadmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GadmServiceImpl implements IGadmService {

    private final IGadmRepo repo;

    @Override
    public Gadm save(Gadm gadm) {
        return repo.save(gadm);
    }

    @Override
    public Gadm update(Gadm gadm) {
        return repo.save(gadm);
    }

    @Override
    public Gadm findById(Integer id) {
        Optional<Gadm> gadm = repo.findById(id);
        return gadm.orElseGet(Gadm::new);
    }

    @Override
    public List<Gadm> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Gadm buscarPorNombre(String nombre) {
        return repo.findByNombre(nombre)
                .orElseThrow(() -> new ModeloNotFoundException("Gadm no encontrado"));
    }
}
