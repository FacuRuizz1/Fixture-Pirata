package com.fixturepirata.dtos;

import com.fixturepirata.enums.Condicion;
import com.fixturepirata.enums.Resultado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidoDTO {
    private Long id;
    private LocalDate fecha;
    private String estadio;
    private int golesBelgrano;
    private int golesRival;
    private Resultado resultado;
    private Condicion condicion;
    private EquipoDTO rival;
}
