package com.uce.sedral.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menus")
public class Menu {

    @Id
    private Integer idMenu;
    @Column(name = "icono", length = 20)
    private String icono;
    @Column(name = "nombre", length = 20)
    private String nombre;
    @Column(name = "url", length = 50)
    private String url;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "menus_roles", joinColumns = @JoinColumn(name = "id_menu", referencedColumnName = "idMenu"),
    inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol"))
    private List<Rol> roles;
}
