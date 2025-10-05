package com.fixturepirata.model;

import com.fixturepirata.enums.Condicion;
import com.fixturepirata.enums.Resultado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partidos")
@Builder
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String estadio;
    private int golesBelgrano;
    private int golesRival;

    @Enumerated(EnumType.STRING)
    private Resultado resultado;

    @Enumerated(EnumType.STRING)
    private Condicion condicion;

    @ManyToOne
    @JoinColumn(name = "rival_id",nullable = false)
    private Equipo rival;



}
