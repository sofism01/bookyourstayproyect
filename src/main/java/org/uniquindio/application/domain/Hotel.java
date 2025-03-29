package org.uniquindio.application.domain;

import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;

import java.util.ArrayList;

public class Hotel extends Alojamiento {
    private ArrayList<Habitacion> habitaciones;

    public Hotel(String nombre, String ubicacion, Ciudad ciudad, String descripcion, double precioPorNoche,
                 int capacidadMax, Servicio servicio) {
        super(nombre, ubicacion, ciudad, descripcion, precioPorNoche, capacidadMax, servicio);
        this.habitaciones = new ArrayList<>();
    }

    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    @Override
    public double calcularCostoTotal() {
        return habitaciones.stream().mapToDouble(Habitacion::getPrecio).sum();
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo : Hotel, Habitaciones: " + habitaciones.size();
    }
}
