package com.dh.Clase15_SpringMVC.service.impl;

import com.dh.Clase15_SpringMVC.entity.Odontologo;
import com.dh.Clase15_SpringMVC.entity.Paciente;
import com.dh.Clase15_SpringMVC.entity.Turno;
import com.dh.Clase15_SpringMVC.exception.InvalidDataException;
import com.dh.Clase15_SpringMVC.exception.ResourceNotFoundException;
import com.dh.Clase15_SpringMVC.repository.IPacienteRepository;
import com.dh.Clase15_SpringMVC.repository.ITurnoRepository;
import com.dh.Clase15_SpringMVC.service.IPacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServicioImpl implements IPacienteServicio {

    @Autowired
    private IPacienteRepository iPacienteRepository;

    @Autowired
    private ITurnoRepository iTurnoRepository;

    @Override
    public Paciente guardar(Paciente paciente) {
        if (paciente == null || paciente.getNombre() == null || paciente.getApellido() == null) {
            throw new InvalidDataException("El paciente tiene datos inválidos.");
        }
        return iPacienteRepository.save(paciente);
    }

    @Override
    public Paciente buscarPorId(Long id) {
        Optional<Paciente> pacienteBuscado = iPacienteRepository.findById(id);
        if(!pacienteBuscado.isPresent())  {
            throw new ResourceNotFoundException("No se encontró el paciente con id " + id);
        } else {
            return pacienteBuscado.get();
        }

    }

    @Override
    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = iPacienteRepository.findAll();
        if (pacientes.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron pacientes.");
        }
        return pacientes;
    }

    @Override
    public void actualizar(Paciente paciente) {
        // Verifica si el paciente existe por su id
        Optional<Paciente> pacienteExistente = iPacienteRepository.findById(paciente.getId());

        if (pacienteExistente.isPresent()) {
            // Si existe, actualiza los datos
            iPacienteRepository.save(paciente);
        } else {
            // Si no existe, lanza la excepción
            throw new ResourceNotFoundException("No se puede actualizar, no se encontró el paciente con id " + paciente.getId());
        }
    }

    @Override
    public void eliminar(Long id) {
        // Verifica si el paciente existe
        if (!iPacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, no se encontró el paciente con id " + id);
        }

        // Verifica si el paciente tiene turnos asociados
        if (iTurnoRepository.existsByPacienteId(id)) { // Método en el repositorio para buscar turnos por ID del paciente
            throw new ResourceNotFoundException("No se puede eliminar, el paciente tiene turnos asociados.");
        }

        // Elimina el paciente si no tiene turnos asociados
        iPacienteRepository.deleteById(id);
    }
}
