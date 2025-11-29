package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolRepo extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByNombre(String nombre);
}