package com.uce.sedral.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sectores")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSector;
    @Column(nullable = false, length = 250)
    private String nombre;
    private boolean sincronizado;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_proyecto", nullable = false, foreignKey = @ForeignKey(name = "fk_id_proyectos"))
    private Proyecto proyecto;
}
