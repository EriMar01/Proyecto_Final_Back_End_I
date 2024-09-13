package com.dh.Clase15_SpringMVC.controller;

import com.dh.Clase15_SpringMVC.entity.Domicilio;
import com.dh.Clase15_SpringMVC.entity.Odontologo;
import com.dh.Clase15_SpringMVC.entity.Paciente;
import com.dh.Clase15_SpringMVC.entity.Turno;
import com.dh.Clase15_SpringMVC.exception.ResourceNotFoundException;
import com.dh.Clase15_SpringMVC.service.IOdontologoServicio;
import com.dh.Clase15_SpringMVC.service.IPacienteServicio;
import com.dh.Clase15_SpringMVC.service.ITurnoServicio;
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
import java.time.LocalTime;

@WebMvcTest(TurnoController.class)
public class TurnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITurnoServicio turnoServicio;

    @MockBean
    private IOdontologoServicio odontologoServicio;

    @MockBean
    private IPacienteServicio pacienteServicio;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void guardarTurnoTest() throws Exception {
        // Arrange
        Turno turno = Turno.builder()
                .id(1L)
                .fecha(LocalDate.of(2024, 9, 10))
                .hora(LocalTime.of(10, 0))
                .odontologo(Odontologo.buildExample())
                .paciente(Paciente.buildExample())
                .build();

        Mockito.when(turnoServicio.guardar(any(Turno.class))).thenReturn(turno);

        // Act & Assert
        mockMvc.perform(post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(turno)))
                .andExpect(status().isOk())  // Verifica que el estado sea 200 OK
                .andExpect(jsonPath("$.fecha").value("2024-09-10"))
                .andExpect(jsonPath("$.hora").value("10:00:00"));
    }

    @Test
    public void obtenerTurnoTest() throws Exception {
        // Arrange
        Odontologo odontologo = Odontologo.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .matricula("1234")
                .build();

        Paciente paciente = Paciente.builder()
                .id(1L)
                .nombre("Maria")
                .apellido("Juana")
                .dni("12345678")
                .fechaAlta(LocalDate.now())
                .domicilio(Domicilio.buildExample())
                .build();

        Turno turno = Turno.builder()
                .id(1L)
                .fecha(LocalDate.of(2024, 9, 10))
                .hora(LocalTime.of(10, 0))
                .odontologo(odontologo)
                .paciente(paciente)
                .build();

        Mockito.when(turnoServicio.buscarPorId(1L)).thenReturn(turno);

        // Act & Assert
        mockMvc.perform(get("/turnos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verifica que el estado sea 200 OK
                .andExpect(jsonPath("$.fecha").value("2024-09-10"))
                .andExpect(jsonPath("$.hora").value("10:00:00")); // Ajusta el formato de la hora
    }


    @Test
    public void obtenerTurnoNoEncontradoTest() throws Exception {
        // Arrange
        Mockito.when(turnoServicio.buscarPorId(1L)).thenThrow(new ResourceNotFoundException("No se encontró el turno con id 1"));

        // Act & Assert
        mockMvc.perform(get("/turnos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())  // Verifica que el estado sea 404 Not Found
                .andExpect(content().string("No se encontró el turno con id 1")); // Verifica el mensaje de error
    }
}