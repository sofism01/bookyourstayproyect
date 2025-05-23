package org.uniquindio.application.enums;

public enum Tipo {
    CASA("CASA"),
    HOTEL("HOTEL"),
    APARTAMENTO("APARTAMENTO");


    private final String nombre;

    Tipo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
