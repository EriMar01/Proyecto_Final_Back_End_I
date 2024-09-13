package com.dh.Clase15_SpringMVC.controller;

import com.dh.Clase15_SpringMVC.entity.Domicilio;
import com.dh.Clase15_SpringMVC.entity.Paciente;
import com.dh.Clase15_SpringMVC.exception.ResourceNotFoundException;
import com.dh.Clase15_SpringMVC.service.impl.PacienteServicioImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

@WebMvcTest(PacienteController.class)
public class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteServicioImpl pacienteServicio;

    @Autowired
    private ObjectMapper objectMapper;  // Para convertir objetos en JSON

    @Test
    public void guardarPacienteTest() throws Exception {
        // Arrange
        Paciente paciente = Paciente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .dni("12345678")
                .fechaAlta(LocalDate.now())
                .domicilio(Domicilio.buildExample())
                .build();

        Mockito.when(pacienteServicio.guardar(any(Paciente.class))).thenReturn(paciente);

        // Act & Assert
        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isOk())  // Verifica que el estado sea 200 OK
                .andExpect(jsonPath("$.nombre").value("Juan"))  // Verifica que el nombre es "Juan"
                .andExpect(jsonPath("$.apellido").value("Perez"));  // Verifica que el apellido es "Perez"
    }

    @Test
    public void obtenerPacienteTest() throws Exception {
        // Arrange
        Paciente paciente = Paciente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .dni("12345678")
                .fechaAlta(LocalDate.now())
                .domicilio(Domicilio.buildExample())
                .build();

        Mockito.when(pacienteServicio.buscarPorId(1L)).thenReturn(paciente);

        // Act & Assert
        mockMvc.perform(get("/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verifica que el estado sea 200 OK
                .andExpect(jsonPath("$.nombre").value("Juan"))  // Verifica que el nombre es "Juan"
                .andExpect(jsonPath("$.apellido").value("Perez"));  // Verifica que el apellido es "Perez"
    }

    @Test
    public void obtenerPacienteNoEncontradoTest() throws Exception {
        // Arrange
        Mockito.when(pacienteServicio.buscarPorId(1L)).thenThrow(new ResourceNotFoundException("No se encontró el paciente con id 1"));

        // Act & Assert
        mockMvc.perform(get("/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // Verifica que el estado sea 404 Not Found
                .andExpect(content().string("No se encontró el paciente con id 1")); // Verifica el mensaje de error
    }
}