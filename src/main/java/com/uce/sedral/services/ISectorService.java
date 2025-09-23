package com.uce.sedral.services;

import com.uce.sedral.models.dto.SectorAndroid;
import com.uce.sedral.models.entities.Sector;

import java.util.List;

public interface ISectorService extends ICRUD<Sector> {

    Sector buscarPorNombre(String nombre);

    List<Sector> findSectoresByProyecto(Integer proyectoId);

    List<SectorAndroid> getAllAndroid();

    Sector saveSectorAndroid(SectorAndroid sectorAndroid);

    Sector updateSectorAndroid(SectorAndroid sectorAndroid);

}
