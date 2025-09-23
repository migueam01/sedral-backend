package com.uce.sedral.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pozos")
public class Pozo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPozo;
    @Column(nullable = false, length = 10, unique = true)
    private String nombre;
    @Column(length = 10)
    private String sistema;
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = "dd/MM/yy HH:mm:ss")
    private LocalDateTime fechaCatastro;
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = "dd/MM/yy HH:mm:ss")
    private LocalDateTime fechaActualizacion;
    @Column(length = 3)
    private String tapado;
    private double norteMovil;
    private double esteMovil;
    private double cotaMovil;
    private double norteTopo;
    private double esteTopo;
    private double cotaTopo;
    private double aproximacion;
    @Column(length = 5)
    private String zona;
    private int srid;
    @Column(length = 8)
    private String estado;
    @Column(length = 20)
    private String calzada;
    @Column(length = 10)
    private String fluido;
    private double dimensionTapa;
    private double altura;
    private double ancho;
    @Column(name = "calle_oe", length = 30)
    private String calleOE;
    @Column(name = "calle_ns", length = 30)
    private String calleNS;
    @Column(length = 100)
    private String observacion;
    @Column(length = 200)
    private String pathMedia;
    private boolean sincronizado;
    private int actividadCompletada;
    @JsonIgnore
    @Column(columnDefinition = "geometry(Point, 0)")
    private Point geom;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sector", nullable = false, foreignKey = @ForeignKey(name = "fk_id_sector"))
    private Sector sector;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_responsable", nullable = false,
            foreignKey = @ForeignKey(name = "fk_id_responsable"))
    private Responsable responsable;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_descarga", nullable = false, foreignKey = @ForeignKey(name = "fk_id_descarga"))
    private Descarga descarga;
}
