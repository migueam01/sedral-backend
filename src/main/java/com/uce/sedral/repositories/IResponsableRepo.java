package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IResponsableRepo extends JpaRepository<Responsable, Integer> {

    Optional<Responsable> findByNombre(String nombre);
}
