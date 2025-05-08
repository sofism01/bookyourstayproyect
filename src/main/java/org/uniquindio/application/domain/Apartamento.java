package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import java.util.List;
@Getter
@Setter

public class Apartamento extends Alojamiento {
    private double costoMantenimiento;

    @Override
    public String toString() {
        return "Apartamento{" +
                "costoMantenimiento=" + costoMantenimiento +
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

    @Builder
    public Apartamento(String id, Tipo tipo, String nombre, List<Oferta> oferta, Reserva reserva, Ciudad ciudad, String descripcion, double precioPorNoche,
                       int capacidadMax, String imagen, List<Servicio> servicio, double costoMantenimiento) {
        super(id, tipo, nombre, oferta, reserva, ciudad, descripcion, precioPorNoche, capacidadMax, imagen, servicio);
        this.costoMantenimiento = costoMantenimiento;

    }

}
