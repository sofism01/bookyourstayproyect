package org.uniquindio.application.enums;

public enum Porcentaje {
    PORCENTAJE_10(10),
    PORCENTAJE_15(15),
    PORCENTAJE_20(20),
    PORCENTAJE_25(25),
    PORCENTAJE_30(30),
    PORCENTAJE_35(35),
    PORCENTAJE_40(40),
    PORCENTAJE_50(50);

    private final int porcentaje;

    Porcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    @Override
    public String toString() {
        return porcentaje + "%";
    }
}
