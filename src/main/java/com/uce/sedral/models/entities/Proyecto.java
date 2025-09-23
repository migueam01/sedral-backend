package com.uce.sedral.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProyecto;
    @Column(nullable = false, length = 250)
    private String nombre;
    @Column(length = 250)
    private String alias;
    private boolean sincronizado;
    private double densidadPoblacional;
    private double dotacion;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_gadm", nullable = false, foreignKey = @ForeignKey(name = "fk_id_gadm"))
    private Gadm gadm;
}
