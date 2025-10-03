package com.fixturepirata.controller;

import com.fixturepirata.dtos.EquipoDTO;
import com.fixturepirata.service.EquipoService;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipo")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoService equipoService;

    @PostMapping("/crear")
    private ResponseEntity<EquipoDTO> registrarEquipo(@RequestBody EquipoDTO equipoDTO){
        return ResponseEntity.ok(equipoService.crearEquipo(equipoDTO));
    }

    @GetMapping("/listar")
    private ResponseEntity<List<EquipoDTO>> listarEquipos(){
        return ResponseEntity.ok(equipoService.obtenerTodos());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EquipoDTO> actualizarEquipo(@PathVariable Long id,
                                                      @RequestBody EquipoDTO equipoDTO){
        return ResponseEntity.ok(equipoService.actualizarEquipo(id, equipoDTO));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarEquipo(@PathVariable Long id){
        equipoService.eliminarEquipo(id);
        return ResponseEntity.ok("Equipo eliminado correctamente");
    }
}
