package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.CalculoHidraulico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICalculoHidraulicoRepo extends JpaRepository<CalculoHidraulico, Integer> {

    Optional<CalculoHidraulico> findByTuberiaIdTuberia(Integer idTuberia);
}
