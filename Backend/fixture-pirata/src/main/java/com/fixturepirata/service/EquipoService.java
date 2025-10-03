package com.fixturepirata.service;

import com.fixturepirata.dtos.EquipoDTO;

import java.util.List;

public interface EquipoService {
    EquipoDTO crearEquipo(EquipoDTO equipoDTO);
    List<EquipoDTO> obtenerTodos();
    EquipoDTO obtenerEquipoPorId(Long id);
    EquipoDTO actualizarEquipo(Long id, EquipoDTO equipoDTO);
    void eliminarEquipo(Long id);
}
