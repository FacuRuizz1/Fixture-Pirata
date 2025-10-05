package com.fixturepirata.service.Impl;

import com.fixturepirata.dtos.EquipoDTO;
import com.fixturepirata.model.Equipo;
import com.fixturepirata.repository.EquipoRepository;
import com.fixturepirata.service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;

    @Override
    public EquipoDTO crearEquipo(EquipoDTO equipoDTO) {
        Equipo equipo = mapToEntity(equipoDTO);
        Equipo equipoGuardado = equipoRepository.save(equipo);
        return mapToDTO(equipoGuardado);
    }

    @Override
    public List<EquipoDTO> obtenerTodos() {
        return equipoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EquipoDTO obtenerEquipoPorId(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
        return mapToDTO(equipo);
    }

    @Override
    public EquipoDTO actualizarEquipo(Long id, EquipoDTO equipoDTO) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con el ID: " + id));

        equipo.setNombre(equipoDTO.getNombre());
        equipo.setCiudad(equipoDTO.getCiudad());

        Equipo equipoActualizado = equipoRepository.save(equipo);
        return mapToDTO(equipoActualizado);
    }

    @Override
    public void eliminarEquipo(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado con el ID: " + id);
        }
        equipoRepository.deleteById(id);
    }

    // ------- Métodos de Mapeo -------- //
    private EquipoDTO mapToDTO(Equipo equipo) {
        return EquipoDTO.builder()
                .id(equipo.getId())
                .nombre(equipo.getNombre())
                .ciudad(equipo.getCiudad())
                .build();
    }

    private Equipo mapToEntity(EquipoDTO dto) {
        return Equipo.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .ciudad(dto.getCiudad())
                .build();
    }
}
