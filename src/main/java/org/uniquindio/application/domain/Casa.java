package org.uniquindio.application.domain;

import lombok.Builder;
import lombok.Getter;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import javax.sound.midi.MidiMessage;
import java.util.List;
@Getter
public class Casa extends Alojamiento{
    private double costoAseo;

    @Builder
    public Casa(String id, Tipo tipo, String nombre,  List<Oferta> oferta, Ciudad ciudad, String descripcion, float precioPorNoche,
                int capacidadMax, String imagen, List<Servicio> servicio, double costoAseo, List<Reserva> reservas) {
        super(id, tipo, nombre, oferta, ciudad, descripcion, precioPorNoche, capacidadMax, imagen, servicio, reservas);
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
