package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import java.util.List;
@Getter
public class Casa extends Alojamiento{
    private double costoAseo;

    @Builder
    public Casa(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                int capacidadMax, Image imagen, List<Servicio> servicio, double costoAseo) {
        super(tipo, nombre, ciudad, descripcion, precioPorNoche, capacidadMax, imagen, servicio);
        this.costoAseo = costoAseo;
    }

    @Override
    public String toString() {
        return "Casa{" +
                "costoAseo=" + costoAseo +
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
