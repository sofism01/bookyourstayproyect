package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public abstract class Alojamiento {

    private String id;
    public Tipo tipo;
    protected String nombre;
    protected List<Oferta> oferta;
    protected Reserva reserva;
    protected Ciudad ciudad;
    protected String descripcion;
    protected double precioPorNoche;
    protected int capacidadMax;
    public String imagen;
    protected List<Servicio> servicio;

    @Override
    public String toString() {
        return "Alojamiento{" +
                "tipo=" + tipo +
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
