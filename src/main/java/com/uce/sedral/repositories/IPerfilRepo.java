package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.Tuberia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPerfilRepo extends JpaRepository<Tuberia, Integer> {

    @Query(value = """
            SELECT
            	p1.id_pozo AS idPozoInicio,
            	p1.nombre AS nombrePozoInicio,
            	p1.altura AS alturaInicio,
            	p1.cota_movil AS cotaTerrenoInicio,
            	p2.id_pozo AS idPozoFin,
            	p2.nombre AS nombrePozoFin,
            	p2.altura AS alturaFin,
            	p2.cota_movil AS cotaTerrenoFin,
            	h.pendiente,
            	h.caudal,
            	h.velocidad,
            	t.diametro,
            	t.material,
            	t.longitud
            FROM tuberias t
            JOIN pozos p1 ON t.id_pozo_inicio = p1.id_pozo
            JOIN pozos p2 ON t.id_pozo_fin = p2.id_pozo
            LEFT JOIN calculos_hidraulicos h ON h.id_tuberia = t.id_tuberia
            WHERE (t.id_pozo_inicio = :pozoInicio AND t.id_pozo_fin = :pozoFin)
            	OR (t.id_pozo_inicio = :pozoFin AND t.id_pozo_fin = :pozoInicio)
            """, nativeQuery = true)
    List<Object[]> obtenerPerfilTramo(@Param("pozoInicio") Integer pozoInicio, @Param("pozoFin") Integer pozoFin);
}
