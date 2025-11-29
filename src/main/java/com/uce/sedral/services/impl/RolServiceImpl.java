package com.uce.sedral.services.impl;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.entities.Rol;
import com.uce.sedral.repositories.IRolRepo;
import com.uce.sedral.services.IRolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements IRolService {

    private final IRolRepo repo;

    @Override
    public Rol buscarPorNombre(String nombre) {
        return repo.findByNombre(nombre)
                .orElseThrow(() -> new ModeloNotFoundException("Rol no encontrado"));
    }

    @Override
    public Rol save(Rol rol) {
        return repo.save(rol);
    }

    @Override
    public Rol update(Rol rol) {
        Rol rolUpdate = repo.findById(rol.getIdRol())
                .orElseThrow(() -> new ModeloNotFoundException("Rol no encontrado"));
        rolUpdate.setNombre(rol.getNombre());
        rolUpdate.setDescripcion(rol.getDescripcion());
        rolUpdate.setPrioridad(rol.getPrioridad());
        return repo.save(rolUpdate);
    }

    @Override
    public Rol findById(Integer id) {
        Optional<Rol> rol = repo.findById(id);
        return rol.orElseGet(Rol::new);
    }

    @Override
    public List<Rol> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}