package org.uniquindio.application.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Ciudad {
    ARMENIA("ARMENIA"),
    PEREIRA("PEREIRA"),
    MEDELLIN("MEDELLIN"),
    BOGOTA("BOGOTA"),
    CARTAGENA("CARTAGENA");

    private final String nombre;

    @Override
    public String toString() {
        return nombre;
    }
}
