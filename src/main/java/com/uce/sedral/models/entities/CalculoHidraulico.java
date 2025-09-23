package com.uce.sedral.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "calculos_hidraulicos")
public class CalculoHidraulico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCalculo;
    private double pendiente;
    private double caudal;
    private double velocidad;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tuberia", nullable = false,
            foreignKey = @ForeignKey(name = "fk_id_tuberia"))
    private Tuberia tuberia;
}
