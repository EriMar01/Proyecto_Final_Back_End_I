package com.dh.Clase15_SpringMVC.service.impl;

import com.dh.Clase15_SpringMVC.entity.Turno;
import com.dh.Clase15_SpringMVC.exception.InvalidDataException;
import com.dh.Clase15_SpringMVC.exception.ResourceNotFoundException;
import com.dh.Clase15_SpringMVC.repository.IOdontologoRepository;
import com.dh.Clase15_SpringMVC.repository.IPacienteRepository;
import com.dh.Clase15_SpringMVC.repository.ITurnoRepository;
import com.dh.Clase15_SpringMVC.service.ITurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class TurnoServicioImpl implements ITurnoServicio {

    @Autowired
    private ITurnoRepository iTurnoRepository;

    @Autowired private IOdontologoRepository iOdontologoRepository;
    @Autowired private IPacienteRepository iPacienteRepository;

    @Override
    public Turno guardar(Turno turno) {
        if (turno == null) {
            throw new InvalidDataException("El turno tiene datos inválidos.");
        }

        // Verifica la existencia del Odontologo
        if (turno.getOdontologo() == null || !iOdontologoRepository.existsById(turno.getOdontologo().getId())) {
            throw new ResourceNotFoundException("Odontólogo no encontrado.");
        }

        // Verifica la existencia del Paciente
        if (turno.getPaciente() == null || !iPacienteRepository.existsById(turno.getPaciente().getId())) {
            throw new ResourceNotFoundException("Paciente no encontrado.");
        }

        return iTurnoRepository.save(turno);
    }

    @Override
    public Turno buscarPorId(Long id) {
        Optional<Turno> turnoBuscado =  iTurnoRepository.findById(id);
        Turno turno = turnoBuscado.orElse(null);

        if (turno == null) {
            throw new ResourceNotFoundException("No se encontró el turno con id " + id);
        }
        return turno;  // Si el turno existe, se retorna
    }

    @Override
    public List<Turno> listarTodos() {
        List<Turno> turnos = iTurnoRepository.findAll();
        if (turnos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron turnos.");
        }
        return turnos;
    }

    @Override
    public void actualizar(Turno turno) {
        // Verifica si el turno existe por su id
        Optional<Turno> turnoExistente = iTurnoRepository.findById(turno.getId());

        if (turnoExistente.isPresent()) {
            // Verifica la existencia del Odontologo
            if (turno.getOdontologo() == null || !iOdontologoRepository.existsById(turno.getOdontologo().getId())) {
                throw new ResourceNotFoundException("Odontólogo no encontrado.");
            }

            // Verifica la existencia del Paciente
            if (turno.getPaciente() == null || !iPacienteRepository.existsById(turno.getPaciente().getId())) {
                throw new ResourceNotFoundException("Paciente no encontrado.");
            }

            // Si existe, actualiza los datos
            iTurnoRepository.save(turno);
        } else {
            // Si no existe, lanza la excepción
            throw new ResourceNotFoundException("No se puede actualizar, no se encontró el turno con id " + turno.getId());
        }
    }

    @Override
    public void eliminar(Long id) {
        if (!iTurnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, no se encontró el turno con id " + id);
        }
        iTurnoRepository.deleteById(id);
    }


}
