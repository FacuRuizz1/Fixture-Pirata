package com.fixturepirata.service;

import com.fixturepirata.dtos.PartidoDTO;
import jakarta.servlet.http.Part;

import java.util.List;

public interface PartidoService {
    PartidoDTO crearPartido(PartidoDTO partidoDTO);
    List<PartidoDTO> obtenerTodos();
    PartidoDTO obtenerPorId(Long id);
    PartidoDTO actualizarPartido(Long id, PartidoDTO partidoDTO);
    void eliminarPartido(Long id);

}
