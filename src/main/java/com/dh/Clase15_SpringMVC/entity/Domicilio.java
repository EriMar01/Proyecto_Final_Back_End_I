package com.dh.Clase15_SpringMVC.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Builder
@Entity
@Table(name = "domicilios")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String calle;
    private Integer numero;
    private String localidad;
    private String provincia;

    public static Domicilio buildExample(){
        return Domicilio.builder()
                .id(1L)
                .calle("Bolivar")
                .numero(12)
                .localidad("Uribe")
                .provincia("Sefani")
                .build();
    }
}
