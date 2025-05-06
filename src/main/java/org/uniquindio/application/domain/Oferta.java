package org.uniquindio.application.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class Oferta {
    private float descuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
