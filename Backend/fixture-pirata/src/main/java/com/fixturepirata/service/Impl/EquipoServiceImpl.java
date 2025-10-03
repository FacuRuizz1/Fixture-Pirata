package com.fixturepirata.service.Impl;

import com.fixturepirata.dtos.EquipoDTO;
import com.fixturepirata.dtos.PartidoDTO;
import com.fixturepirata.model.Equipo;
import com.fixturepirata.model.Partido;
import com.fixturepirata.repository.EquipoRepository;
import com.fixturepirata.service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final ModelMapper modelMapper;

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
        equipo.setCiudad(equipo.getCiudad());

        Equipo equipoActualizado = equipoRepository.save(equipo);
        return mapToDTO(equipoActualizado);
    }

    @Override
    public void eliminarEquipo(Long id) {
       if(!equipoRepository.existsById(id)){
           throw new RuntimeException("Equipo no encontrado con el ID: " + id);
       }
       equipoRepository.deleteById(id);
    }


    //-------Metodos de Mapeo--------//
    private EquipoDTO mapToDTO(Equipo equipo) {
        return EquipoDTO.builder()
                .id(equipo.getId())
                .nombre(equipo.getNombre())
                .ciudad(equipo.getCiudad())
                .partidoDTOS(
                        equipo.getPartidosContraBelgrano() != null ?
                                equipo.getPartidosContraBelgrano()
                                        .stream()
                                        .map(this::mapPartidoToDTO)
                                        .collect(Collectors.toList())
                                : new ArrayList<>()
                )
                .build();
    }

    private Equipo mapToEntity(EquipoDTO dto) {
        Equipo equipo = new Equipo();
        equipo.setId(dto.getId());
        equipo.setNombre(dto.getNombre());
        equipo.setCiudad(dto.getCiudad());

        if (dto.getPartidoDTOS() != null) {
            equipo.setPartidosContraBelgrano(
                    dto.getPartidoDTOS().stream()
                            .map(this::mapPartidoToEntity)
                            .collect(Collectors.toList())
            );
        }

        return equipo;
    }

    private PartidoDTO mapPartidoToDTO(Partido partido) {
        return PartidoDTO.builder()
                .id(partido.getId())
                .fecha(partido.getFecha())
                .estadio(partido.getEstadio())
                .golesBelgrano(partido.getGolesBelgrano())
                .golesRival(partido.getGolesRival())
                .resultado(partido.getResultado())
                .condicion(partido.getCondicion())
                .build();
    }

    private Partido mapPartidoToEntity(PartidoDTO dto) {
        return Partido.builder()
                .id(dto.getId())
                .fecha(dto.getFecha())
                .estadio(dto.getEstadio())
                .golesBelgrano(dto.getGolesBelgrano())
                .golesRival(dto.getGolesRival())
                .resultado(dto.getResultado())
                .condicion(dto.getCondicion())
                .build();
    }
}


