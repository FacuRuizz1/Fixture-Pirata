package com.fixturepirata.repository;

import com.fixturepirata.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo,Long> {
    Optional<Equipo> findByNombre(String nombre);

}
