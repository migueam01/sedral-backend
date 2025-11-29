package com.uce.sedral.repositories;

import com.uce.sedral.models.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMenuRepo extends JpaRepository<Menu, Integer> {

    @Query(value = """
            SELECT m.*
            FROM menus_roles mr
            INNER JOIN menus m ON m.id_menu = mr.id_menu
            WHERE mr.id_rol = (
            SELECT r.id_rol
            FROM usuarios_roles ur
            INNER JOIN roles r ON r.id_rol = ur.id_rol
            INNER JOIN responsables u ON u.id_responsable = ur.id_responsable
            WHERE u.username = :nombre
            ORDER BY r.prioridad DESC
            LIMIT 1)
            ORDER BY m.id_menu;
            """, nativeQuery = true)
    List<Object[]> listarMenuPorUsuario(@Param("nombre") String nombre);
}