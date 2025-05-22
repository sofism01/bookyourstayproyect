package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter

public class Apartamento extends Alojamiento implements Serializable {
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
    public Apartamento(String id, Tipo tipo, String nombre, List<Oferta> oferta, Ciudad ciudad, String descripcion, float precioPorNoche,
                       int capacidadMax, String imagen, List<Servicio> servicio, double costoMantenimiento, List<Reserva> reservas) {
        super(id, tipo, nombre, oferta, ciudad, descripcion, precioPorNoche, capacidadMax, imagen, servicio, reservas);
        this.costoMantenimiento = costoMantenimiento;

    }

}
