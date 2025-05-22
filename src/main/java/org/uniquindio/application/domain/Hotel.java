package org.uniquindio.application.domain;

import lombok.Builder;
import lombok.Getter;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Getter
public class Hotel extends Alojamiento implements Serializable {
    private List<Habitacion> habitaciones;

    @Builder
    public Hotel(String id, Tipo tipo, String nombre,  List<Oferta> oferta, Ciudad ciudad, String descripcion, float precioPorNoche,
                 int capacidadMax, String imagen, List<Servicio> servicio, List<Habitacion> habitaciones, List<Reserva> reservas) {
        super(id, tipo, nombre, oferta,ciudad, descripcion, precioPorNoche, capacidadMax, imagen, servicio, reservas);
        this.habitaciones = habitaciones;
    }

    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    public String getPrecioRango() {

        float minimo = Float.MAX_VALUE;
        float maximo = 0;

        for(Habitacion habitacion: habitaciones){
            if(habitacion.getPrecio() < minimo){
                minimo = habitacion.getPrecio();
            }
            if(habitacion.getPrecio() > maximo){
                maximo = habitacion.getPrecio();
            }
        }

        return "Entre "+minimo+" y "+maximo;
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
