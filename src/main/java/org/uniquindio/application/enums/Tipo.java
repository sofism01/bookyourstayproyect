package org.uniquindio.application.enums;

public enum Tipo {
    CASA("Casa"),
    HOTEL("Hotel"),
    APARTAMENTO("Apartamento");


    private final String nombre;

    Tipo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
