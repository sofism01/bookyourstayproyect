package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.simplejavamail.api.internal.clisupport.model.Cli;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Reserva {
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String idReserva;
    private int numeroPersonas;
    private Cliente cliente;
    private Alojamiento alojamiento;
    private Factura factura;

    public Reserva(LocalDate fechaIngreso, LocalDate fechaSalida, Cliente cliente, Alojamiento alojamiento, int numeroPersonas) {
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.idReserva = UUID.randomUUID().toString(); // Genera un ID único con UUID
        this.numeroPersonas = numeroPersonas;
        this.cliente = cliente;
        this.alojamiento = alojamiento;
        this.factura = generarFactura(); // Genera la factura con UUID
    }

    // Método para generar la factura con UUID
    private Factura generarFactura() {
        // Calcular el subtotal (precio por noche * noches)
        long noches = ChronoUnit.DAYS.between(fechaIngreso, fechaSalida);
        double subtotal = noches * alojamiento.getPrecioPorNoche();

        if (alojamiento instanceof Casa || alojamiento instanceof Apartamento) {
            subtotal += 50000; // Costo adicional fijo de aseo
        }

        // Crear la factura con el UUID como código único
        return new Factura(subtotal, subtotal); // El total inicialmente es igual al subtotal (puedes agregar impuestos o cargos adicionales si es necesario)
    }
}
