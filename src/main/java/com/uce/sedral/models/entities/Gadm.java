package com.uce.sedral.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gadms")
public class Gadm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGadm;
    @Size(min = 3, message = "Nombre del GADM debe tener mínimo tres caracteres")
    @Column(nullable = false, length = 250)
    private String nombre;
    @Column(length = 250)
    private String alias;
    private boolean sincronizado;
}
