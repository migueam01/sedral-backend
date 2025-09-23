package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.Gadm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGadmRepo extends JpaRepository<Gadm, Integer> {

    Optional<Gadm> findByNombre(String nombre);
}
