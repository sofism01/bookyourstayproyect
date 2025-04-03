package org.uniquindio.application.domain;

import lombok.Builder;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Hotel extends Alojamiento {
    private ArrayList<Habitacion> habitaciones;

    @Builder
    public Hotel(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                 int capacidadMax, Image imagen, List<Servicio> servicio) {
        super(tipo, nombre, ciudad, descripcion, precioPorNoche, capacidadMax, imagen, servicio);
        this.habitaciones = new ArrayList<>();
    }

    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

}
