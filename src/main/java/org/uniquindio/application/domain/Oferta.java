package org.uniquindio.application.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class Oferta implements Serializable {
    private float descuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
