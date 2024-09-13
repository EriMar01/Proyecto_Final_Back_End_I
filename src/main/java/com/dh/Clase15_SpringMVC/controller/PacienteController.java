package com.dh.Clase15_SpringMVC.controller;

import com.dh.Clase15_SpringMVC.entity.Odontologo;
import com.dh.Clase15_SpringMVC.entity.Paciente;
import com.dh.Clase15_SpringMVC.service.IPacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private IPacienteServicio iPacienteServicio;

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> consultarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(iPacienteServicio.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(iPacienteServicio.guardar(paciente));
    }

    //listar todos
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {

        return ResponseEntity.ok(iPacienteServicio.listarTodos());
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Void> actualiza(@RequestBody Paciente paciente){
        iPacienteServicio.actualizar(paciente);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> eliminar( @PathVariable Long id){
        iPacienteServicio.eliminar(id);
        return ResponseEntity.noContent().build(); // Responde con 204 No Content si la eliminación fue exitosa
    }


}
