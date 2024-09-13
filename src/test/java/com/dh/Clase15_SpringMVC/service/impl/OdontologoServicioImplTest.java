package com.dh.Clase15_SpringMVC.service.impl;

import com.dh.Clase15_SpringMVC.entity.Odontologo;
import com.dh.Clase15_SpringMVC.entity.Turno;
import com.dh.Clase15_SpringMVC.exception.ResourceNotFoundException;
import com.dh.Clase15_SpringMVC.repository.IOdontologoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OdontologoServicioImplTest {

    @InjectMocks
    private OdontologoServicioImpl odontologoService;  // Clase que estamos probando

    @Mock
    private IOdontologoRepository odontologoRepository; // Repositorio simulado

    @Test
    public void registrarOdontologoTest() {
        // Arrange
        Odontologo odontologo = Odontologo.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .matricula("matricula")
                .build();

        when(odontologoRepository.save(odontologo)).thenReturn(odontologo);

        // Act
        Odontologo resultado = odontologoService.guardar(odontologo);

        // Assert
        assertNotNull(resultado);  // Verifica que el objeto retornado no sea nulo
        assertEquals("Juan", resultado.getNombre());  // Verifica que el nombre sea correcto
        assertEquals("Perez", resultado.getApellido());  // Verifica que el apellido sea correcto
        verify(odontologoRepository, times(1)).save(odontologo);
    }

    @Test
    public void buscarPorIdTest() {
        // Arrange
        Odontologo odontologo = Odontologo.builder()
                .id(1L)
                .nombre("Dr. Perez")
                .apellido("Perez")
                .matricula("mat123")
                .build();

        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));

        // Act
        Odontologo odontologoEncontrado = odontologoService.buscarPorId(1L);

        // Assert
        assertNotNull(odontologoEncontrado);
        assertEquals("Dr. Perez", odontologoEncontrado.getNombre());
        assertEquals("Perez", odontologoEncontrado.getApellido());
        verify(odontologoRepository, times(1)).findById(1L);
    }

    @Test
    public void buscarPorIdNoEncontradoTest() {
        // Arrange
        when(odontologoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> odontologoService.buscarPorId(1L)
        );

        assertEquals("No se encontró el odontólogo con id 1", exception.getMessage());
        verify(odontologoRepository, times(1)).findById(1L);
    }

    @Test
    public void eliminarTest() {
        // Arrange
        Long id = 1L;
        when(odontologoRepository.existsById(id)).thenReturn(true);
        doNothing().when(odontologoRepository).deleteById(id);

        // Act
        odontologoService.eliminar(id);

        // Assert
        verify(odontologoRepository, times(1)).deleteById(id);
    }

    @Test
    public void eliminarNoEncontradoTest() {
        // Arrange
        Long id = 1L;
        when(odontologoRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> odontologoService.eliminar(id)
        );

        assertEquals("No se puede eliminar, no se encontró el odontólogo con id 1", exception.getMessage());
        verify(odontologoRepository, times(1)).existsById(id);
    }

    @Test
    public void actualizarTest() {
        // Arrange
        Odontologo odontologo = Odontologo.builder()
                .id(1L)
                .nombre("Dr. Perez")
                .apellido("Perez")
                .matricula("mat123")
                .build();

        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));
        when(odontologoRepository.save(odontologo)).thenReturn(odontologo);

        // Act
        odontologoService.actualizar(odontologo);

        // Assert
        verify(odontologoRepository, times(1)).save(odontologo);
    }

    @Test
    public void actualizarNoEncontradoTest() {
        // Arrange
        Odontologo odontologo = Odontologo.builder()
                .id(1L)
                .nombre("Dr. Perez")
                .apellido("Perez")
                .matricula("mat123")
                .build();

        when(odontologoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> odontologoService.actualizar(odontologo)
        );

        assertEquals("No se puede actualizar, no se encontró el odontólogo con id 1", exception.getMessage());
        verify(odontologoRepository, times(1)).findById(1L);
    }

    @Test
    public void listarTodosTest() {
        // Arrange
        Odontologo odontologo1 = Odontologo.builder()
                .id(1L)
                .nombre("Dr. Perez")
                .apellido("Perez")
                .matricula("mat123")
                .build();

        Odontologo odontologo2 = Odontologo.builder()
                .id(2L)
                .nombre("Dr. Lopez")
                .apellido("Lopez")
                .matricula("mat456")
                .build();

        when(odontologoRepository.findAll()).thenReturn(Arrays.asList(odontologo1, odontologo2));

        // Act
        List<Odontologo> odontologos = odontologoService.listarTodos();

        // Assert
        assertNotNull(odontologos);
        assertEquals(2, odontologos.size());
        assertTrue(odontologos.contains(odontologo1));
        assertTrue(odontologos.contains(odontologo2));
        verify(odontologoRepository, times(1)).findAll();
    }

    @Test
    public void listarTodosNoEncontradosTest() {
        // Arrange
        when(odontologoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> odontologoService.listarTodos()
        );

        assertEquals("No se encontraron odontólogos.", exception.getMessage());
        verify(odontologoRepository, times(1)).findAll();
    }
}
