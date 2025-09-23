package com.uce.sedral.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.LineString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tuberias")
public class Tuberia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTuberia;
    @Column(nullable = false, length = 6)
    private String orientacion;
    @Column(nullable = false)
    private double base;
    @Column(nullable = false)
    private double corona;
    @Column(nullable = false)
    private int diametro;
    @Column(nullable = false, length = 16)
    private String material;
    @Column(nullable = false)
    private double longitud;
    @Column(nullable = false, length = 7)
    private String flujo;
    @Column(nullable = false, length = 3)
    private String funciona;
    private boolean sincronizado;
    private double areaAporte;
    @JsonIgnore
    @Column(columnDefinition = "geometry(LineString,0)")
    private LineString geom;
    @JsonIgnore
    private int srid;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pozo_inicio", nullable = false,
            foreignKey = @ForeignKey(name = "fk_id_pozo_inicio"))
    private Pozo pozoInicio;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pozo_fin", nullable = false,
            foreignKey = @ForeignKey(name = "fk_id_pozo_fin"))
    private Pozo pozoFin;
}
