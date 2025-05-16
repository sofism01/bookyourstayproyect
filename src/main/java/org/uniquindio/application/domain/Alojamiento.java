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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public abstract class Alojamiento implements Serializable {

    private String id;
    public Tipo tipo;
    protected String nombre;
    protected List<Oferta> ofertas;
    protected Ciudad ciudad;
    protected String descripcion;
    protected float precioPorNoche;
    protected int capacidadMax;
    public String imagen;
    protected List<Servicio> servicio;
    protected List<Reserva> reservas;

    public Alojamiento() {
        this.reservas = new ArrayList<>();
    }

    public void guardarOferta(float porcentaje, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        if (porcentaje <= 0 || porcentaje > 100) {
            throw new Exception("El porcentaje debe estar entre 1 y 100.");

        }

        if (fechaInicio.isBefore(LocalDate.now()) || fechaFin.isBefore(LocalDate.now())) {
            throw new Exception("Las fechas no pueden estar en el pasado.");


        }
        if (fechaInicio == null || fechaFin == null) {
            throw new Exception("Las fechas no pueden ser nulas.");

        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new Exception("La fecha de fin no puede ser anterior a la fecha de inicio.");

        }

        Oferta nuevaOferta = new Oferta(porcentaje, fechaInicio, fechaFin);
        ofertas.add(nuevaOferta);

        throw new Exception("¡Oferta guardada con éxito!");
    }

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
