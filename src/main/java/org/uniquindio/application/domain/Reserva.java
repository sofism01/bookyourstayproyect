package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.simplejavamail.api.internal.clisupport.model.Cli;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Reserva implements Serializable {
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String idReserva;
    private int numeroPersonas;
    private Cliente cliente;
    private Alojamiento alojamiento;
    private Factura factura;
    private float costoTotal;
    private Habitacion habitacionSeleccionada;

    public Habitacion getHabitacionSeleccionada() {
        return habitacionSeleccionada;
    }

    public void setHabitacionSeleccionada(Habitacion habitacionSeleccionada) {
        this.habitacionSeleccionada = habitacionSeleccionada;
    }

    @Builder
    public Reserva(LocalDate fechaIngreso, LocalDate fechaSalida, Cliente cliente, Alojamiento alojamiento, int numeroPersonas, float costoTotal) {
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.idReserva = UUID.randomUUID().toString(); // Genera un ID único con UUID
        this.numeroPersonas = numeroPersonas;
        this.cliente = cliente;
        this.alojamiento = alojamiento;
        this.factura = generarFactura(); // Genera la factura con UUID
        this.costoTotal = costoTotal;
    }

    // Método para generar la factura con UUID
    public Factura generarFactura() {
        float subtotal = getTotalDias() * alojamiento.getPrecioPorNoche();
        if (alojamiento instanceof Casa) {
            subtotal += ((Casa) alojamiento).getCostoAseo();
        } else if (alojamiento instanceof Apartamento) {
            subtotal += ((Apartamento) alojamiento).getCostoMantenimiento();
        }
        return new Factura(subtotal, subtotal);
    }
    // Método para calcular el total de días entre fechaIngreso y fechaSalida
    public long getTotalDias() {
        return fechaIngreso != null && fechaSalida != null ? 
               ChronoUnit.DAYS.between(fechaIngreso, fechaSalida) : 0;
    }
}
