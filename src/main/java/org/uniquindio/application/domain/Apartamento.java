package org.uniquindio.application.domain;

import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;

public class Apartamento extends Alojamiento {
    private double costoMantenimiento;

    public Apartamento(String nombre, String ubicacion, Ciudad ciudad, String descripcion, double precioPorNoche,
                       int capacidadMax, Servicio servicio, double costoMantenimiento) {
        super(nombre, ubicacion, ciudad, descripcion, precioPorNoche, capacidadMax, servicio);
        this.costoMantenimiento = costoMantenimiento;

    }

    @Override
    public double calcularCostoTotal() {
        return costoMantenimiento; //cobrando solo el cosot de mantenimiento
    }

    @Override
    public String toString() {
        return super.toString()+ ", Tipo: Apartamento, Costo Mantenimiento: $" + costoMantenimiento + "\n";
    }
}
