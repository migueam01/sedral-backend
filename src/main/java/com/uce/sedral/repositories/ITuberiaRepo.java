package com.uce.sedral.repositories;

import com.uce.sedral.models.dto.CotaAlturaTuberia;
import com.uce.sedral.models.entities.Tuberia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITuberiaRepo extends JpaRepository<Tuberia, Integer> {

    @Query("from Tuberia t where t.pozoInicio.idPozo =:pozoId")
    List<Tuberia> findTuberiasByPozoInicio(@Param("pozoId") Integer pozoInicio);

    @Query(value = "SELECT jsonb_build_object( 'type','Feature', 'geometry', " +
            "ST_AsGeoJSON(ST_Transform(geom, 4326))::jsonb, 'properties', " +
            "jsonb_build_object('id', id_tuberia, 'diametro', diametro, 'material', material, " +
            "'flujo', flujo, 'funciona', funciona)) FROM tuberias", nativeQuery = true)
    List<Object> findAllGeoJson();

    @Query(value = "SELECT id_tuberia, diametro, material, flujo, funciona, " +
            "ST_AsText(ST_Transform(geom, 4326)) as wkt " +
            "FROM tuberias",
            nativeQuery = true)
    List<Object[]> findTuberiasWithCoordinates();

    //Consultas para el dashboard
    @Query(value = "SELECT COUNT(t) FROM Tuberia t")
    int countAll();

    @Query(value = "SELECT ROUND(SUM(t.longitud), 2) FROM Tuberia t")
    double sumAllLongitudes();

    @Query(value = "SELECT t.funciona, " +
            "COUNT(t), " +
            "ROUND((COUNT(t) * 100.00)/(SELECT COUNT(t) FROM Tuberia t WHERE t.longitud > 0), 2), " +
            "ROUND(SUM(t.longitud), 2), " +
            "ROUND(AVG(t.longitud), 2), " +
            "ROUND(((SUM(t.longitud) * 100.00)/(SELECT SUM(t.longitud) FROM Tuberia t)), 2)" +
            "FROM Tuberia t " +
            "WHERE t.longitud IS NOT NULL AND t.longitud > 0" +
            "GROUP BY t.funciona")
    List<Object[]> getTuberiasByFuncionamiento();

    @Query(value = "SELECT t.diametro, " +
            "COUNT(t), " +
            "ROUND((COUNT(t) * 100.00)/(SELECT COUNT(t) FROM Tuberia t WHERE t.longitud > 0), 2), " +
            "ROUND(SUM(t.longitud), 2), " +
            "ROUND(AVG(t.longitud), 2), " +
            "ROUND(((SUM(t.longitud) * 100.00)/(SELECT SUM(t.longitud) FROM Tuberia t)), 2)" +
            "FROM Tuberia t " +
            "WHERE t.longitud IS NOT NULL AND t.longitud > 0" +
            "GROUP BY t.diametro")
    List<Object[]> getTuberiasByDiametro();

    @Query(value = "SELECT t.material, " +
            "COUNT(t), " +
            "ROUND((COUNT(t) * 100.00)/(SELECT COUNT(t) FROM Tuberia t WHERE t.longitud > 0), 2), " +
            "ROUND(SUM(t.longitud), 2), " +
            "ROUND(AVG(t.longitud), 2), " +
            "ROUND(((SUM(t.longitud) * 100.00)/(SELECT SUM(t.longitud) FROM Tuberia t)), 2)" +
            "FROM Tuberia t " +
            "WHERE t.longitud IS NOT NULL AND t.longitud > 0" +
            "GROUP BY t.material")
    List<Object[]> getTuberiasByMaterial();

    @Query(value = "SELECT " +
            "CASE " +
            "WHEN longitud <= 10 THEN '0-10'" +
            "WHEN longitud <= 20 THEN '11-20' " +
            "WHEN longitud <= 50 THEN '21-50' " +
            "WHEN longitud <= 100 THEN '51-100' " +
            "ELSE 'Más de 100' " +
            "END, " +
            "COUNT(*), " +
            "ROUND(SUM(longitud)::numeric, 2), " +
            "ROUND(AVG(longitud)::numeric, 2) " +
            "FROM tuberias " +
            "WHERE longitud IS NOT NULL AND longitud > 0 " +
            "GROUP BY " +
            "CASE " +
            "WHEN longitud <= 10 THEN '0-10' " +
            "WHEN longitud <= 20 THEN '11-20' " +
            "WHEN longitud <= 50 THEN '21-50' " +
            "WHEN longitud <= 100 THEN '51-100' " +
            "ELSE 'Más de 100' " +
            "END " +
            "ORDER BY MIN(longitud)",
            nativeQuery = true)
    List<Object[]> getTuberiasByRangoLongitud();

    @Query("SELECT new com.uce.sedral.models.dto.CotaAlturaTuberia(" +
            "t.pozoInicio.cotaMovil, " +
            "t.pozoInicio.altura, " +
            "t.pozoFin.cotaMovil, " +
            "t.pozoFin.altura) " +
            "FROM Tuberia t " +
            "WHERE t.idTuberia = :idTuberia")
    CotaAlturaTuberia getCotasAlturasTuberias(@Param("idTuberia") Integer idTuberia);
}
