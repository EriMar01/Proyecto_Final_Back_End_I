package com.dh.Clase15_SpringMVC.controller;

import com.dh.Clase15_SpringMVC.entity.Odontologo;
import com.dh.Clase15_SpringMVC.exception.ResourceNotFoundException;
import com.dh.Clase15_SpringMVC.service.impl.OdontologoServicioImpl;
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

import java.util.HashSet;

@WebMvcTest(OdontologoController.class)
public class OdontologoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OdontologoServicioImpl odontologoServicio;

    @Autowired
    private ObjectMapper objectMapper;  // Para convertir objetos en JSON

    @Test
    public void guardarOdontologoTest() throws Exception {
        // Arrange
        Odontologo odontologo = Odontologo.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .matricula("1234")
                .build();

        Mockito.when(odontologoServicio.guardar(any(Odontologo.class))).thenReturn(odontologo);

        // Act & Assert
        mockMvc.perform(post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(odontologo)))
                .andExpect(status().isOk())  // Verifica que el estado sea 200 OK
                .andExpect(jsonPath("$.nombre").value("Juan"))  // Verifica que el nombre es "Juan"
                .andExpect(jsonPath("$.apellido").value("Perez"));  // Verifica que el apellido es "Perez"
    }

    @Test
    public void obtenerOdontologoTest() throws Exception {
        // Arrange
        Odontologo odontologo = Odontologo.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .matricula("1234")
                .build();

        Mockito.when(odontologoServicio.buscarPorId(1L)).thenReturn(odontologo);

        // Act & Assert
        mockMvc.perform(get("/odontologos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verifica que el estado sea 200 OK
                .andExpect(jsonPath("$.nombre").value("Juan"))  // Verifica que el nombre es "Juan"
                .andExpect(jsonPath("$.apellido").value("Perez"));  // Verifica que el apellido es "Perez"
    }

    @Test
    public void obtenerOdontologoNoEncontradoTest() throws Exception {
        // Arrange
        Mockito.when(odontologoServicio.buscarPorId(1L)).thenThrow(new ResourceNotFoundException("No se encontró el odontólogo con id 1"));

        // Act & Assert
        mockMvc.perform(get("/odontologos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());  // Verifica que el estado sea 404 Not Found
    }
}