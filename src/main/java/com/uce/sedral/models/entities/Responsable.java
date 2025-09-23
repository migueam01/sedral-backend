package com.uce.sedral.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(length = 11)
    private String telefono;
    private boolean sincronizado;
}
