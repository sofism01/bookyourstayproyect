package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public abstract class Alojamiento implements Serializable {

    private String id;
    public Tipo tipo;
    protected String nombre;
    protected List<Oferta> oferta;
    protected Ciudad ciudad;
    protected String descripcion;
    protected float precioPorNoche;
    protected int capacidadMax;
    public String imagen;
    protected List<Servicio> servicio;
    protected List<Reserva> reservas = new ArrayList<>();

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
