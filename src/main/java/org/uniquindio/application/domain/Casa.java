package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;

import java.util.ArrayList;

public class Casa extends Alojamiento{
    private double CostoAseo;

    public Casa(String nombre, String ubicacion, Ciudad ciudad, String descripcion, double precioPorNoche,
                 int capacidadMax, Servicio servicio, double costoAseo) {
        super(nombre, ubicacion, ciudad, descripcion, precioPorNoche, capacidadMax, servicio);
        this.CostoAseo = costoAseo;
    }

    @Override
    public double calcularCostoTotal() {
        return CostoAseo; //cobrando solo el costo de aseo
    }

    @Override
    public String toString() {
        return super.toString()+", Tipo: Casa, CostoAseo : $" + CostoAseo + '}';
    }
}
