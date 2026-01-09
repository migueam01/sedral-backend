package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.CalculoHidraulico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ICalculoHidraulicoRepo extends JpaRepository<CalculoHidraulico, Integer> {

    Optional<CalculoHidraulico> findByTuberiaIdTuberia(Integer idTuberia);

    @Query(value = """
            SELECT
            ch.id_calculo AS id_Calculo,
            ch.id_tuberia AS id_Tuberia,
            p1.nombre AS nombrePozoInicio,
            p2.nombre AS nombrePozoFin,
            t.diametro AS diametro,
            t.material AS material,
            t.calado AS calado,
            ch.manning AS manning,
            ch.pendiente AS pendiente,
            ch.velocidad AS velocidad,
            ch.caudal AS caudal,
            ch.relacion_caudal AS relacionCaudal,
            ch.relacion_velocidad AS relacionVelocidad,
            ch.relacion_area AS relacionArea
            FROM calculos_hidraulicos ch
            JOIN tuberias t
            ON ch.id_tuberia = t.id_tuberia
            JOIN pozos p1
            ON t.id_pozo_inicio = p1.id_pozo
            JOIN pozos p2
            ON t.id_pozo_fin = p2.id_pozo
            """, nativeQuery = true)
    List<Object[]> obtenerCalculosConPozos();
}