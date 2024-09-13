package com.dh.Clase15_SpringMVC.controller;

import com.dh.Clase15_SpringMVC.entity.Odontologo;
import com.dh.Clase15_SpringMVC.service.IOdontologoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private IOdontologoServicio odontologoServicio;


    //RequestParam url ? parametro = X & parametro = X
    //PathVariable url/pathVariable
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(odontologoServicio.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoServicio.guardar(odontologo));
    }

    //listar todos
    @GetMapping
    public ResponseEntity<List<Odontologo>> listarTodos() {
        return ResponseEntity.ok(odontologoServicio.listarTodos());
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Void>  actualiza(@RequestBody Odontologo odontologo){
         odontologoServicio.actualizar(odontologo);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> eliminar( @PathVariable Long id){
        odontologoServicio.eliminar(id);
        return ResponseEntity.noContent().build(); // Responde con 204 No Content si la eliminación fue exitosa
    }

    @GetMapping("matricula/{matricula}")
    public ResponseEntity<Odontologo> buscarPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(odontologoServicio.buscarPorMatricula(matricula));
    }

}
