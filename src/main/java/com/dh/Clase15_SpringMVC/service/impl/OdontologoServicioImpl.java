package com.dh.Clase15_SpringMVC.service.impl;

import com.dh.Clase15_SpringMVC.entity.Odontologo;
import com.dh.Clase15_SpringMVC.exception.InvalidDataException;
import com.dh.Clase15_SpringMVC.exception.ResourceNotFoundException;
import com.dh.Clase15_SpringMVC.repository.IOdontologoRepository;
import com.dh.Clase15_SpringMVC.repository.ITurnoRepository;
import com.dh.Clase15_SpringMVC.service.IOdontologoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoServicioImpl implements IOdontologoServicio {

    @Autowired
    private IOdontologoRepository iOdontologoRepository;
    @Autowired
    private ITurnoRepository iTurnoRepository;

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        if (odontologo == null || odontologo.getNombre() == null || odontologo.getApellido() == null) {
            throw new InvalidDataException("El odontólogo tiene datos inválidos.");
        }
        return iOdontologoRepository.save(odontologo);
    }

    @Override
    public Odontologo buscarPorId(Long id){
        //va a buscar al odontologo y lo va a guardar en odontologoBuscado
        //o va a guardar un null en el odontologoBuscado
        Optional<Odontologo> odontologoBuscado = iOdontologoRepository.findById(id);
        if (odontologoBuscado.isPresent()) {
            return odontologoBuscado.get();
        } else {
            //acá vamos a lanzar la excepción
            //return null;

            throw new ResourceNotFoundException("No se encontró el odontólogo con id " + id);
        }
    }


    @Override
    public void eliminar(Long id) {
        if (!iOdontologoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, no se encontró el odontólogo con id " + id);
        }
        // Verifica si el paciente tiene turnos asociados
        if (iTurnoRepository.existsByOdontologoId(id)) { // Método en el repositorio para buscar turnos por ID del paciente
            throw new ResourceNotFoundException("No se puede eliminar, el odontologo tiene turnos asociados.");
        }
        iOdontologoRepository.deleteById(id);
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        // Verifica si el odontólogo existe por su id
        Optional<Odontologo> odontologoExistente = iOdontologoRepository.findById(odontologo.getId());

        if (odontologoExistente.isPresent()) {
            // Si existe, actualiza los datos
            iOdontologoRepository.save(odontologo);
        } else {
            // Si no existe, lanza la excepción
            throw new ResourceNotFoundException("No se puede actualizar, no se encontró el odontólogo con id " + odontologo.getId());
        }
    }

    @Override
    public List<Odontologo> listarTodos() {
        List<Odontologo> odontologos = iOdontologoRepository.findAll();
        if (odontologos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron odontólogos.");
        }
        return odontologos;
    }

    @Override
    public Odontologo buscarPorMatricula(String matricula) {
        Odontologo odontologo = iOdontologoRepository.findByMatricula(matricula);
        if (odontologo == null) {
            throw new ResourceNotFoundException("No se encontró ningún odontólogo con la matrícula " + matricula);
        }
        return odontologo;
    }

}
