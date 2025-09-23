package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.Descarga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDescargaRepo extends JpaRepository<Descarga, Integer> {

    Optional<Descarga> findByNombre(String nombre);
}
