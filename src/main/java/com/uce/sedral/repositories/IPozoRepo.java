package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.Pozo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IPozoRepo extends JpaRepository<Pozo, Integer> {

    Optional<Pozo> findByNombre(String nombre);

    @Query(value = "SELECT jsonb_build_object( 'type','Feature', 'geometry', " +
            "ST_AsGeoJSON(ST_Transform(geom, 4326))::jsonb, 'properties', " +
            "jsonb_build_object('id', id_pozo, 'nombre', nombre, 'tapado', tapado, 'norte', norte_movil, " +
            "'este', este_movil, 'cota', cota_movil, 'zona', zona, 'estado', estado, " +
            "'calzada', calzada, 'altura', altura, 'observacion', observacion)) FROM pozos", nativeQuery = true)
    List<Object> findAllGeoJson();

    @Query(value = "SELECT id_pozo, nombre, tapado, cota_movil, estado, altura, " +
            "ST_Y(ST_Transform(geom, 4326)) as latitud, " +
            "ST_X(ST_Transform(geom, 4326)) as longitud " +
            "FROM pozos",
            nativeQuery = true)
    List<Object[]> findPozosWithCoordinates();

    @Query(value = "SELECT ST_Y(ST_Transform(geom, 4326)) as latitud, " +
            "ST_X(ST_Transform(geom, 4326)) as longitud " +
            "FROM pozos WHERE id = :id",
            nativeQuery = true)
    Object[] findCoordinatesById(Long id);

    //Consultas para el dashboard
    @Query("SELECT COUNT(p) FROM Pozo p")
    int countAll();

    @Query(value = "SELECT " +
            "ROUND(AVG(altura)::numeric, 2), " +
            "ROUND(MAX(altura)::numeric, 2), " +
            "ROUND(MIN(altura)::numeric, 2)" +
            "FROM pozos " +
            "WHERE altura > 0", nativeQuery = true)
    List<Object[]> getStatsHeight();

    @Query(value = "SELECT p.estado, " +
            "COUNT(p), " +
            "ROUND((COUNT(p) * 100.00)/(SELECT COUNT(p) FROM Pozo p), 2) " +
            "FROM Pozo p " +
            "GROUP BY p.estado")
    List<Object[]> getPozosByEstado();

    @Query(value = "SELECT p.tapado, " +
            "COUNT(p), " +
            "ROUND((COUNT(p) * 100.00)/(SELECT COUNT(p) FROM Pozo p), 2) " +
            "FROM Pozo p " +
            "GROUP BY p.tapado")
    List<Object[]> getPozosByTapado();

    @Query(value = "SELECT p.calzada, " +
            "COUNT(p), " +
            "ROUND((COUNT(p) * 100.00)/(SELECT COUNT(p) FROM Pozo p), 2) " +
            "FROM Pozo p " +
            "GROUP BY p.calzada")
    List<Object[]> getPozosByCalzada();

    @Query(value = "SELECT " +
            "CASE " +
            "WHEN altura <= 1.5 THEN '0 - 1.5 m' " +
            "WHEN altura <= 2.5 THEN '1.6 - 2.5 m' " +
            "WHEN altura <= 3.5 THEN '2.6 - 3.5 m' " +
            "ELSE 'Mas de 3.5 m' " +
            "END as rango_altura, " +
            "COUNT(*) AS cantidad_pozos " +
            "FROM pozos " +
            "WHERE altura IS NOT NULL AND altura > 0 " +
            "GROUP BY " +
            "CASE " +
            "WHEN altura <= 1.5 THEN '0 - 1.5 m' " +
            "WHEN altura <= 2.5 THEN '1.6 - 2.5 m' " +
            "WHEN altura <= 3.5 THEN '2.6 - 3.5 m' " +
            "ELSE 'Mas de 3.5 m' " +
            "END " +
            "ORDER BY MIN(altura)",
            nativeQuery = true)
    List<Object[]> getPozosByRangoAltura();
}
