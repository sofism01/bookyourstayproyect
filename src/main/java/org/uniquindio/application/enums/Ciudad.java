package org.uniquindio.application.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Ciudad {
    ARMENIA("Armenia"),
    PEREIRA("Pereira"),
    MEDELLIN("Medellín"),
    BOGOTA("Bogotá"),
    CARTAGENA("Cartagena");

    private final String nombre;

    @Override
    public String toString() {
        return nombre;
    }
}
