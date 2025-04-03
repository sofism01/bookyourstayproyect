package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import lombok.Builder;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import java.util.List;

public class Apartamento extends Alojamiento {
    private double costoMantenimiento;

    @Builder
    public Apartamento(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                       int capacidadMax, Image imagen, List<Servicio> servicio, double costoMantenimiento) {
        super(tipo, nombre, ciudad, descripcion, precioPorNoche, capacidadMax, imagen, servicio);
        this.costoMantenimiento = costoMantenimiento;

    }

}
