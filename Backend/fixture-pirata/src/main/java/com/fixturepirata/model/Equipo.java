package com.fixturepirata.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipos")
@Builder
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String ciudad;

    @OneToMany(mappedBy = "rival",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Partido> partidosContraBelgrano;
}
