package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Oferta implements Serializable {
    private String descuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String id;

    public Oferta(String descuento, LocalDate fechaInicio, LocalDate fechaFin) {
        this.descuento = descuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.id = UUID.randomUUID().toString();
    }
}
