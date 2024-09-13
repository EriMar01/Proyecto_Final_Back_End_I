package com.dh.Clase15_SpringMVC.service.impl;

import com.dh.Clase15_SpringMVC.entity.Domicilio;
import com.dh.Clase15_SpringMVC.entity.Paciente;
import com.dh.Clase15_SpringMVC.entity.Turno;
import com.dh.Clase15_SpringMVC.exception.InvalidDataException;
import com.dh.Clase15_SpringMVC.exception.ResourceNotFoundException;
import com.dh.Clase15_SpringMVC.repository.IPacienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServicioImplTest {

    @InjectMocks
    private PacienteServicioImpl pacienteService;  // Clase que estamos probando

    @Mock
    private IPacienteRepository pacienteRepository; // Repositorio simulado

    @Test
    public void registrarPacienteTest() {
        // Arrange

        Paciente paciente = Paciente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .dni("fafa")
                .fechaAlta(LocalDate.of(2024, 1, 12))
                .domicilio(Domicilio.buildExample())
                .build();

        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        // Act
        Paciente resultado = pacienteService.guardar(paciente);

        // Assert
        assertNotNull(resultado);  // Verifica que el objeto retornado no sea nulo
        assertEquals("Juan", resultado.getNombre());  // Verifica que el nombre sea correcto
        assertEquals("Perez", resultado.getApellido());  // Verifica que el apellido sea correcto
        verify(pacienteRepository, times(1)).save(paciente);
    }

    @Test
    public void registrarPacienteConDatosInvalidosTest() {
        // Arrange
        Paciente paciente = Paciente.builder()
                .id(1L)
                .nombre(null)
                .apellido("Perez")
                .dni("fafa")
                .fechaAlta(LocalDate.of(2024, 1, 12))
                .domicilio(Domicilio.buildExample())
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(
                InvalidDataException.class,
                () -> pacienteService.guardar(paciente)
        );

        assertEquals("El paciente tiene datos inválidos.", exception.getMessage());
        verify(pacienteRepository, never()).save(paciente);
    }

    @Test
    public void buscarPorIdTest() {
        // Arrange
        Paciente paciente = Paciente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .dni("fafa")
                .fechaAlta(LocalDate.of(2024, 1, 12))
                .domicilio(Domicilio.buildExample())
                .build();

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        // Act
        Paciente pacienteEncontrado = pacienteService.buscarPorId(1L);

        // Assert
        assertNotNull(pacienteEncontrado);
        assertEquals("Juan", pacienteEncontrado.getNombre());
        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    public void buscarPorIdNoEncontradoTest() {
        // Arrange
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> pacienteService.buscarPorId(1L)
        );

        assertEquals("No se encontró el paciente con id 1", exception.getMessage());
        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    public void eliminarTest() {
        // Arrange
        Long id = 1L;
        when(pacienteRepository.existsById(id)).thenReturn(true);
        doNothing().when(pacienteRepository).deleteById(id);

        // Act
        pacienteService.eliminar(id);

        // Assert
        verify(pacienteRepository, times(1)).deleteById(id);
    }

    @Test
    public void eliminarNoEncontradoTest() {
        // Arrange
        Long id = 1L;
        when(pacienteRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> pacienteService.eliminar(id)
        );

        assertEquals("No se puede eliminar, no se encontró el paciente con id 1", exception.getMessage());
        verify(pacienteRepository, times(1)).existsById(id);
        verify(pacienteRepository, never()).deleteById(id);
    }

    @Test
    public void actualizarTestPacienteExistente() {
        // Arrange
        Paciente paciente = Paciente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .dni("fafa")
                .fechaAlta(LocalDate.of(2024, 1, 12))
                .domicilio(Domicilio.buildExample())
                .build();

        // Simula que el paciente existe en la base de datos
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        // Simula que el método save devuelve el paciente actualizado
        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        // Act
        pacienteService.actualizar(paciente);

        // Assert
        verify(pacienteRepository, times(1)).save(paciente);
    }

    @Test
    public void actualizarNoEncontradoTest() {
        // Arrange
        Paciente paciente = Paciente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .dni("fafa")
                .fechaAlta(LocalDate.of(2024, 1, 12))
                .domicilio(Domicilio.buildExample())
                .build();

        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> pacienteService.actualizar(paciente)
        );

        assertEquals("No se puede actualizar, no se encontró el paciente con id 1", exception.getMessage());
        verify(pacienteRepository, never()).save(paciente);
    }

    @Test
    public void listarTodosTest() {
        // Arrange
        Paciente paciente1 = Paciente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .dni("fafa")
                .fechaAlta(LocalDate.of(2024, 1, 12))
                .domicilio(Domicilio.buildExample())
                .build();

        Paciente paciente2 = Paciente.builder()
                .id(2L)
                .nombre("Ana")
                .apellido("Lopez")
                .dni("gaga")
                .fechaAlta(LocalDate.of(2024, 1, 13))
                .domicilio(Domicilio.buildExample())
                .build();

        when(pacienteRepository.findAll()).thenReturn(Arrays.asList(paciente1, paciente2));

        // Act
        List<Paciente> pacientes = pacienteService.listarTodos();

        // Assert
        assertNotNull(pacientes);
        assertEquals(2, pacientes.size());
        assertTrue(pacientes.contains(paciente1));
        assertTrue(pacientes.contains(paciente2));
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    public void listarTodosNoEncontradoTest() {
        // Arrange
        when(pacienteRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> pacienteService.listarTodos()
        );

        assertEquals("No se encontraron pacientes.", exception.getMessage());
        verify(pacienteRepository, times(1)).findAll();
    }
}
