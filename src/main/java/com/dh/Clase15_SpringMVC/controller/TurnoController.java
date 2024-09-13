package com.dh.Clase15_SpringMVC.controller;

import com.dh.Clase15_SpringMVC.entity.Turno;
import com.dh.Clase15_SpringMVC.service.IOdontologoServicio;
import com.dh.Clase15_SpringMVC.service.IPacienteServicio;
import com.dh.Clase15_SpringMVC.service.ITurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private ITurnoServicio iTurnoServicio;
    @Autowired private IOdontologoServicio iOdontologoServicio;
    @Autowired private IPacienteServicio iPacienteServicio;

    @PostMapping
    public ResponseEntity<Turno> guardar(@RequestBody Turno turno) {
        return ResponseEntity.ok(iTurnoServicio.guardar(turno));

    }


    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarPorId(@PathVariable Long id) {

        //TODO que pasa si el id del paciente o del odontologo
        //que recibe el turno no existen
        return ResponseEntity.ok(iTurnoServicio.buscarPorId(id));
    }

    //listar todos
    @GetMapping
    public ResponseEntity<List<Turno>> listarTodos() {

        return ResponseEntity.ok(iTurnoServicio.listarTodos());
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Void> actualiza(@RequestBody Turno turno){
        iTurnoServicio.actualizar(turno);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> eliminar( @PathVariable Long id){
        iTurnoServicio.eliminar(id);
        return ResponseEntity.noContent().build(); // Responde con 204 No Content si la eliminación fue exitosa
    }
}
