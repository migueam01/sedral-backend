package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ISectorRepo extends JpaRepository<Sector, Integer> {

    Optional<Sector> findByNombre(String nombre);

    @Query("from Sector s where s.proyecto.idProyecto =:proyectoId")
    List<Sector> findSectoresByProyecto(@Param("proyectoId") Integer proyectoId);
}
