package com.uce.sedral.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "responsables")
public class Responsable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idResponsable;
    @Column(nullable = false, length = 25)
    private String nombre;
    @Column(nullable = false, length = 25)
    private String apellido;
    private String username;
    private String password;
    @Column(length = 11)
    private String telefono;
    private boolean habilitado;
    private String correo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "id_responsable", referencedColumnName = "idResponsable"),
            inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol"))
    private List<Rol> roles;

}
