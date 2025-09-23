package com.uce.sedral.services.impl;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.dto.ProyectoAndroid;
import com.uce.sedral.models.entities.Gadm;
import com.uce.sedral.models.entities.Proyecto;
import com.uce.sedral.repositories.IGadmRepo;
import com.uce.sedral.repositories.IProyectoRepo;
import com.uce.sedral.services.IProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProyectoServiceImpl implements IProyectoService {

    private final IProyectoRepo proyectoRepo;
    private final IGadmRepo gadmRepo;

    @Override
    public Proyecto save(Proyecto proyecto) {
        return proyectoRepo.save(proyecto);
    }

    @Override
    public Proyecto update(Proyecto proyecto) {
        return proyectoRepo.save(proyecto);
    }

    @Override
    public Proyecto findById(Integer id) {
        Optional<Proyecto> proyecto = proyectoRepo.findById(id);
        return proyecto.orElseGet(Proyecto::new);
    }

    @Override
    public List<Proyecto> findAll() {
        return proyectoRepo.findAll();
    }

    @Override
    public void delete(Integer id) {
        proyectoRepo.deleteById(id);
    }

    @Override
    public Proyecto buscarPorNombre(String nombre) {
        return proyectoRepo.findByNombre(nombre)
                .orElseThrow(() -> new ModeloNotFoundException("Proyecto no encontrado"));
    }

    @Override
    public List<Proyecto> findProyectosByGadm(Integer gadmId) {
        return proyectoRepo.findProyectosByGadm(gadmId);
    }

    @Override
    public List<ProyectoAndroid> getAllAndroid() {
        List<Proyecto> proyectos = proyectoRepo.findAll();
        return proyectos.stream()
                .map(p -> new ProyectoAndroid(
                        p.getIdProyecto(),
                        p.getNombre(),
                        p.getAlias(),
                        p.isSincronizado(),
                        p.getGadm().getIdGadm()
                )).toList();
    }

    @Override
    public Proyecto saveProyectoAndroid(ProyectoAndroid proyectoAndroid) {
        Gadm gadm;
        Optional<Gadm> optionalGadm = gadmRepo.findById(proyectoAndroid.getIdGadm());
        if (optionalGadm.isPresent()) {
            gadm = optionalGadm.get();
        } else {
            throw new ModeloNotFoundException("Gadm no encontrado");
        }
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(proyectoAndroid.getNombre());
        proyecto.setAlias(proyectoAndroid.getAlias());
        proyecto.setSincronizado(proyectoAndroid.isSincronizado());
        proyecto.setGadm(gadm);
        return proyectoRepo.save(proyecto);
    }

    @Override
    public Proyecto updateProyectoAndroid(ProyectoAndroid proyectoAndroid) {
        Proyecto proyecto = proyectoRepo.findById(proyectoAndroid.getIdProyecto())
                .orElseThrow(() -> new ModeloNotFoundException("Proyecto no encontrado"));
        Gadm gadm;
        Optional<Gadm> optionalGadm = gadmRepo.findById(proyectoAndroid.getIdGadm());
        if (optionalGadm.isPresent()) {
            gadm = optionalGadm.get();
        } else {
            throw new ModeloNotFoundException("Gadm no encontrado");
        }
        proyecto.setNombre(proyectoAndroid.getNombre());
        proyecto.setAlias(proyectoAndroid.getAlias());
        proyecto.setSincronizado(proyectoAndroid.isSincronizado());
        proyecto.setGadm(gadm);
        return proyectoRepo.save(proyecto);
    }
}
