package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
public class Reserva {
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String idReserva;
    private int numeroPersonas;
}
