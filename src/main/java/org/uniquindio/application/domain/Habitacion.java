package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Habitacion {

    private String numero;
    private float precio;
    private String capacidad;
    private String descripcion;


    @Override
    public String toString() {
        return "Habitacion{" +
                "numero=" + numero +
                ", precio=" + precio +
                ", capacidad=" + capacidad +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

}
