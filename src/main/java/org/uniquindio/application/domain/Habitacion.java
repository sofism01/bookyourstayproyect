package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor

public class Habitacion {
    private int numero;
    @Getter
    private double precio;
    private int capacidad;
    private String descripcion;


    @Override
    public String toString() {
        return "Habitacion{" +
                "numero=" + numero +
                ", precio=" + precio +
                ", capacidad=" + capacidad +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
