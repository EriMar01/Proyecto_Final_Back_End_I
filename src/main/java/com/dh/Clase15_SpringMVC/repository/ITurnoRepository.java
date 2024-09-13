package com.dh.Clase15_SpringMVC.repository;

import com.dh.Clase15_SpringMVC.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long> {

    boolean existsByPacienteId(Long pacienteId); // Método para verificar si existen turnos por ID del paciente
    boolean existsByOdontologoId(Long odontologoId);
}
