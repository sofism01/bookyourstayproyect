package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;

@AllArgsConstructor


public abstract class Alojamiento {
    protected String nombre;
    protected String ubicacion;
    protected Ciudad ciudad;
    protected String descripcion;
    protected double precioPorNoche;
    protected int capacidadMax;
    protected Servicio servicio;



    //método con el que cada tipo de alojamiento calcula el precio de forma diferente
    public abstract double calcularCostoTotal();

    @Override
    public String toString() {
        return "Alojamiento{" +
                "nombre='" + nombre + '\'' +
                ", ciudad=" + ciudad +
                ", descripcion='" + descripcion + '\'' +
                ", precioPorNoche=" + precioPorNoche +
                ", capacidadMax=" + capacidadMax +
                ", servicio=" + servicio +
                '}';
    }
}
