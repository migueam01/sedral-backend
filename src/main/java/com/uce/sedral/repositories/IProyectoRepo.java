package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IProyectoRepo extends JpaRepository<Proyecto, Integer> {

    Optional<Proyecto> findByNombre(String nombre);

    @Query("from Proyecto p where p.gadm.idGadm =:gadmId")
    List<Proyecto> findProyectosByGadm(@Param("gadmId") Integer gadmId);
}
