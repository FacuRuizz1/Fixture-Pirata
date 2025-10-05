package com.fixturepirata.service.Impl;

import com.fixturepirata.dtos.EquipoDTO;
import com.fixturepirata.dtos.PartidoDTO;
import com.fixturepirata.enums.Resultado;
import com.fixturepirata.model.Equipo;
import com.fixturepirata.model.Partido;
import com.fixturepirata.repository.EquipoRepository;
import com.fixturepirata.repository.PartidoRepository;
import com.fixturepirata.service.PartidoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartidoServiceImpl implements PartidoService {

    private final PartidoRepository partidoRepository;
    private final EquipoRepository equipoRepository;
    private final ModelMapper modelMapper;

    @Override
    public PartidoDTO crearPartido(PartidoDTO partidoDTO) {
        // Buscar el equipo rival por su nombre
        Equipo rival = equipoRepository.findByNombre(partidoDTO.getRival().getNombre())
                .orElseThrow(() -> new RuntimeException("Rival no encontrado"));

        // Crear el partido usando el DTO
        Partido partido = mapToEntity(partidoDTO);

        //Calculo de resultado automaticamente
        if(partidoDTO.getGolesBelgrano() > partidoDTO.getGolesRival()){
            partido.setResultado(Resultado.VICTORIA);
        } else if (partidoDTO.getGolesBelgrano() == partidoDTO.getGolesRival()) {
            partido.setResultado(Resultado.EMPATE);
        }
        else {
            partido.setResultado(Resultado.DERROTA);
        }

        //Asignar el equipo rival encontrado
        partido.setRival(rival);

        //Guardar el partido en la bdd
        Partido partidoGuardado = partidoRepository.save(partido);

        //Retornar el DTO del partido creado
        return mapToDTO(partidoGuardado);
    }

    @Override
    public List<PartidoDTO> obtenerTodos() {
        return partidoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PartidoDTO obtenerPorId(Long id) {
        Partido partido = partidoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Partido no encontrado con el ID: " + id));

        return mapToDTO(partido);
    }

    @Override
    public PartidoDTO actualizarPartido(Long id, PartidoDTO partidoDTO) {
        Partido partido = partidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con el ID: " + id));

        partido.setFecha(partidoDTO.getFecha());
        partido.setEstadio(partidoDTO.getEstadio());
        partido.setGolesBelgrano(partidoDTO.getGolesBelgrano());
        partido.setGolesRival(partidoDTO.getGolesRival());
        partido.setResultado(partidoDTO.getResultado());
        partido.setCondicion(partidoDTO.getCondicion());

        if(partidoDTO.getRival() != null && partidoDTO.getRival().getId() != null){
            Equipo rival = equipoRepository.findById(partidoDTO.getRival().getId())
                    .orElseThrow(()-> new RuntimeException("Rival no encontrado con el id: " + partidoDTO.getRival().getId()));
            partido.setRival(rival);
        }

        Partido partidoActualizado = partidoRepository.save(partido);
        return mapToDTO(partidoActualizado);
    }

    @Override
    public void eliminarPartido(Long id) {
         if(!partidoRepository.existsById(id)){
             throw new RuntimeException("Partido no encontrado con id: " + id);
         }
         partidoRepository.deleteById(id);
    }

    // ------------------------------------
    // Métodos de mapeo
    // ------------------------------------
    private PartidoDTO mapToDTO(Partido partido) {
        return PartidoDTO.builder()
                .id(partido.getId())
                .fecha(partido.getFecha())
                .estadio(partido.getEstadio())
                .golesBelgrano(partido.getGolesBelgrano())
                .golesRival(partido.getGolesRival())
                .resultado(partido.getResultado())
                .condicion(partido.getCondicion())
                .rival(
                        partido.getRival() != null
                                ? EquipoDTO.builder()
                                .id(partido.getRival().getId())
                                .nombre(partido.getRival().getNombre())
                                .ciudad(partido.getRival().getCiudad())
                                .build()
                                : null
                )
                .build();
    }

    private Partido mapToEntity(PartidoDTO dto) {
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
