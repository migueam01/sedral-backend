package com.uce.sedral.services.impl;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.dto.SectorAndroid;
import com.uce.sedral.models.entities.Proyecto;
import com.uce.sedral.models.entities.Sector;
import com.uce.sedral.repositories.IProyectoRepo;
import com.uce.sedral.repositories.ISectorRepo;
import com.uce.sedral.services.ISectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl implements ISectorService {

    private final ISectorRepo sectorRepo;
    private final IProyectoRepo proyectoRepo;

    @Override
    public Sector save(Sector sector) {
        return sectorRepo.save(sector);
    }

    @Override
    public Sector update(Sector sector) {
        return sectorRepo.save(sector);
    }

    @Override
    public Sector findById(Integer id) {
        Optional<Sector> sector = sectorRepo.findById(id);
        return sector.orElseGet(Sector::new);
    }

    @Override
    public List<Sector> findAll() {
        return sectorRepo.findAll();
    }

    @Override
    public void delete(Integer id) {
        sectorRepo.deleteById(id);
    }

    @Override
    public Sector buscarPorNombre(String nombre) {
        return sectorRepo.findByNombre(nombre)
                .orElseThrow(() -> new ModeloNotFoundException("Sector no encontrado"));
    }

    @Override
    public List<Sector> findSectoresByProyecto(Integer proyectoId) {
        return sectorRepo.findSectoresByProyecto(proyectoId);
    }

    @Override
    public List<SectorAndroid> getAllAndroid() {
        List<Sector> sectores = sectorRepo.findAll();
        return sectores.stream()
                .map(s -> new SectorAndroid(
                        s.getIdSector(),
                        s.getNombre(),
                        s.isSincronizado(),
                        s.getProyecto().getIdProyecto()
                )).toList();
    }

    @Override
    public Sector saveSectorAndroid(SectorAndroid sectorAndroid) {
        Proyecto proyecto;
        Optional<Proyecto> optionalProyecto = proyectoRepo.findById(sectorAndroid.getIdProyecto());
        if (optionalProyecto.isPresent()) {
            proyecto = optionalProyecto.get();
        } else {
            throw new ModeloNotFoundException("Proyecto no encontrado");
        }
        Sector sector = new Sector();
        sector.setNombre(sectorAndroid.getNombre());
        sector.setSincronizado(sectorAndroid.isSincronizado());
        sector.setProyecto(proyecto);
        return sectorRepo.save(sector);
    }

    @Override
    public Sector updateSectorAndroid(SectorAndroid sectorAndroid) {
        Sector sector = sectorRepo.findById(sectorAndroid.getIdProyecto())
                .orElseThrow(() -> new ModeloNotFoundException("Sector no encontrado"));
        Proyecto proyecto;
        Optional<Proyecto> optionalProyecto = proyectoRepo.findById(sectorAndroid.getIdProyecto());
        if (optionalProyecto.isPresent()) {
            proyecto = optionalProyecto.get();
        } else {
            throw new ModeloNotFoundException("Proyecto no encontrado");
        }
        sector.setNombre(sectorAndroid.getNombre());
        sector.setSincronizado(sectorAndroid.isSincronizado());
        sector.setProyecto(proyecto);
        return sectorRepo.save(sector);
    }
}
