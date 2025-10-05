package com.fixturepirata.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private String estadio;
    private int golesBelgrano;
    private int golesRival;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Resultado resultado;
    private Condicion condicion;
    private Long rivalId;
}
