package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Habitacion implements Serializable {

    private String numero;
    private float precio;
    private String capacidad;
    private String descripcion;
    List<Reserva> reservas;



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
