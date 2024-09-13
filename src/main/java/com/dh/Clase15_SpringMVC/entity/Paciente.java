package com.dh.Clase15_SpringMVC.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Builder
@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaAlta;

    //estoy mapeando la relación para que 1 paciente tenga 1 solo domicilio
    @OneToOne(cascade = CascadeType.ALL)
    private Domicilio domicilio;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();

    public static Paciente buildExample(){
        return Paciente.builder().id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .dni("fafa")
                .fechaAlta(LocalDate.of(2024,1,12))
                .domicilio(Domicilio.buildExample())
                .build();

    }

}
