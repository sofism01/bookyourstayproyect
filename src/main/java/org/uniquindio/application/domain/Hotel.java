package org.uniquindio.application.domain;

import lombok.Builder;
import lombok.Getter;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Hotel extends Alojamiento {
    private List<Habitacion> habitaciones;

    @Builder
    public Hotel(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                 int capacidadMax, Image imagen, List<Servicio> servicio, List<Habitacion> habitaciones) {
        super(tipo, nombre, ciudad, descripcion, precioPorNoche, capacidadMax, imagen, servicio);
        this.habitaciones = habitaciones;
    }

    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "habitaciones=" + habitaciones +
                ", tipo=" + tipo +
                ", nombre='" + nombre + '\'' +
                ", ciudad=" + ciudad +
                ", descripcion='" + descripcion + '\'' +
                ", precioPorNoche=" + precioPorNoche +
                ", capacidadMax=" + capacidadMax +
                ", imagen=" + imagen +
                ", servicio=" + servicio +
                '}';
    }
}
