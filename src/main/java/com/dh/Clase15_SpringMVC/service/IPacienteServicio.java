package com.dh.Clase15_SpringMVC.service;

import com.dh.Clase15_SpringMVC.entity.Paciente;

import java.util.List;

public interface IPacienteServicio {
    //CRUD - ABM
    Paciente guardar (Paciente paciente);
    Paciente buscarPorId(Long id);
    List<Paciente> listarTodos();
    void actualizar(Paciente paciente);
    void eliminar(Long id);
}
