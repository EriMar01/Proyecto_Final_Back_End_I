package com.dh.Clase15_SpringMVC.entity;

import jakarta.persistence.*;
        import lombok.*;

        import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Builder
@Entity
@Table(name = "turnos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    // @JoinColumn(name = "odontologo_id", nullable = false) // columna en la tabla turnos que hará referencia al odontólogo
    private Odontologo odontologo;
    @ManyToOne
    //@JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    private LocalDate fecha;
    private LocalTime hora;

    public static Turno buildExample() {
        return Turno.builder()
                .id(1L)
                .odontologo(Odontologo.buildExample())
                .paciente(Paciente.buildExample())
                .fecha(LocalDate.of(2024, 1, 12))
                .hora(LocalTime.of(10, 20))
                .build();
    }
}
