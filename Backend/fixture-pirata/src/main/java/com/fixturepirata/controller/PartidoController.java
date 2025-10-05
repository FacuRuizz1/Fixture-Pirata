package com.fixturepirata.controller;

import com.fixturepirata.dtos.PartidoDTO;
import com.fixturepirata.service.PartidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partido")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService partidoService;

    @PostMapping("/crear")
    public ResponseEntity<PartidoDTO> registrarPartido(@RequestBody PartidoDTO partidoDTO){
        return ResponseEntity.ok(partidoService.crearPartido(partidoDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PartidoDTO>> listarPartidos(){
        return ResponseEntity.ok(partidoService.obtenerTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PartidoDTO> buscarPartido(@PathVariable Long id){
        return ResponseEntity.ok(partidoService.obtenerPorId(id));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PartidoDTO> actualizarPartido(@PathVariable Long id,
                                                        @RequestBody PartidoDTO partidoDTO){

        return ResponseEntity.ok(partidoService.actualizarPartido(id, partidoDTO));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPartido(@PathVariable Long id){
        partidoService.eliminarPartido(id);
        return ResponseEntity.ok("Partido eliminado correctamente");
    }
}
