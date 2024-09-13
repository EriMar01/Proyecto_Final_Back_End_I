package com.dh.Clase15_SpringMVC.service.impl;

import com.dh.Clase15_SpringMVC.entity.Domicilio;
import com.dh.Clase15_SpringMVC.entity.Odontologo;
import com.dh.Clase15_SpringMVC.entity.Paciente;
import com.dh.Clase15_SpringMVC.entity.Turno;
import com.dh.Clase15_SpringMVC.exception.ResourceNotFoundException;
import com.dh.Clase15_SpringMVC.repository.IOdontologoRepository;
import com.dh.Clase15_SpringMVC.repository.IPacienteRepository;
import com.dh.Clase15_SpringMVC.repository.ITurnoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TurnoServicioImplTest {

    @InjectMocks
    private TurnoServicioImpl turnoService;

    @Mock
    private IOdontologoRepository odontologoRepository;

    @Mock
    private IPacienteRepository pacienteRepository;

    @Mock
    private ITurnoRepository turnoRepository;

    @Test
    public void guardarTurnoTest() {
        // Arrange
        Paciente paciente = Paciente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .dni("fafa")
                .fechaAlta(LocalDate.of(2024, 1, 12))
                .domicilio(Domicilio.buildExample())
                .build();

        Odontologo odontologo = Odontologo.builder()
                .id(1L)
                .nombre("Dr. Lopez")
                .apellido("Lopez")
                .matricula("mat123")
                .build();

        Turno turno = Turno.builder()
                .odontologo(odontologo)
                .paciente(paciente)
                .fecha(LocalDate.of(2024, 1, 12))
                .hora(LocalTime.of(10, 20))
                .build();

        // Act
        when(odontologoRepository.existsById(odontologo.getId())).thenReturn(true);
        when(pacienteRepository.existsById(paciente.getId())).thenReturn(true);
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);

        // Act
        Turno turnoGuardado = turnoService.guardar(turno);

        // Assert
        assertNotNull(turnoGuardado);
        assertEquals(paciente, turnoGuardado.getPaciente());
        assertEquals(odontologo, turnoGuardado.getOdontologo());
        assertEquals(LocalDate.of(2024, 1, 12), turnoGuardado.getFecha());
        assertEquals(LocalTime.of(10, 20), turnoGuardado.getHora());

        verify(odontologoRepository, times(1)).existsById(odontologo.getId());
        verify(pacienteRepository, times(1)).existsById(paciente.getId());
        verify(turnoRepository, times(1)).save(any(Turno.class));
    }

    @Test
    public void guardarTurnoOdontologoNoEncontradoTest() {
        // Arrange
        Paciente paciente = Paciente.builder().id(1L).nombre("Juan").build();
        Odontologo odontologo = Odontologo.builder().id(2L).nombre("Dr. Lopez").build();
        Turno turno = Turno.builder().odontologo(odontologo).paciente(paciente).build();

        // Mock
        when(odontologoRepository.existsById(odontologo.getId())).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> turnoService.guardar(turno)
        );

        assertEquals("Odontólogo no encontrado.", exception.getMessage());
        verify(odontologoRepository, times(1)).existsById(odontologo.getId());
        verify(pacienteRepository, never()).existsById(anyLong());
        verify(turnoRepository, never()).save(any(Turno.class));
    }

    @Test
    public void guardarTurnoPacienteNoEncontradoTest() {
        // Arrange
        Paciente paciente = Paciente.builder().id(1L).nombre("Juan").build();
        Odontologo odontologo = Odontologo.builder().id(1L).nombre("Dr. Lopez").build();
        Turno turno = Turno.builder().odontologo(odontologo).paciente(paciente).build();

        // Mock
        when(odontologoRepository.existsById(odontologo.getId())).thenReturn(true);
        when(pacienteRepository.existsById(paciente.getId())).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> turnoService.guardar(turno)
        );

        assertEquals("Paciente no encontrado.", exception.getMessage());
        verify(odontologoRepository, times(1)).existsById(odontologo.getId());
        verify(pacienteRepository, times(1)).existsById(paciente.getId());
        verify(turnoRepository, never()).save(any(Turno.class));
    }
}
