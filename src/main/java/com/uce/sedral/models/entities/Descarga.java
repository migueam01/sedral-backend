package com.uce.sedral.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "descargas")
public class Descarga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDescarga;
    @Column(nullable = false, length = 25)
    private String nombre;
    @Column(length = 150)
    private String ubicacion;
    private boolean sincronizado;
}
