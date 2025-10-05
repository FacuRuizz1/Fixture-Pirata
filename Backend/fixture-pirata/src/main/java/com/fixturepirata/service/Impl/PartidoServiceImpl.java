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
        Equipo rival = equipoRepository.findById(partidoDTO.getRivalId())
                .orElseThrow(() -> new RuntimeException("Rival no encontrado"));

        Partido partido = new Partido();
        partido.setFecha(partidoDTO.getFecha());
        partido.setEstadio(partidoDTO.getEstadio());
        partido.setGolesBelgrano(partidoDTO.getGolesBelgrano());
        partido.setGolesRival(partidoDTO.getGolesRival());
        partido.setCondicion(partidoDTO.getCondicion());
        partido.setRival(rival);

        //  Calcular el resultado automáticamente
        if (partido.getGolesBelgrano() > partido.getGolesRival()) {
            partido.setResultado(Resultado.VICTORIA);
        } else if (partido.getGolesBelgrano() < partido.getGolesRival()) {
            partido.setResultado(Resultado.DERROTA);
        } else {
            partido.setResultado(Resultado.EMPATE);
        }

        Partido partidoGuardado = partidoRepository.save(partido);
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
        partido.setCondicion(partidoDTO.getCondicion());

        // Calcular el resultado automáticamente basado en los goles
        if (partido.getGolesBelgrano() > partido.getGolesRival()) {
            partido.setResultado(Resultado.VICTORIA);
        } else if (partido.getGolesBelgrano() < partido.getGolesRival()) {
            partido.setResultado(Resultado.DERROTA);
        } else {
            partido.setResultado(Resultado.EMPATE);
        }

        if(partidoDTO.getRivalId() != null){
            Equipo rival = equipoRepository.findById(partidoDTO.getRivalId())
                    .orElseThrow(() -> new RuntimeException("Rival no encontrado con el id: " + partidoDTO.getRivalId()));
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
                .rivalId(partido.getRival() != null ? partido.getRival().getId() : null)
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
