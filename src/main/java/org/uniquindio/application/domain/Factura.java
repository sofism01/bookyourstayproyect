package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Factura {
    private String codigoFactura;
    private LocalDate fechaFactura;
    private double subtotal;
    private double total;

    // Constructor
    public Factura(double subtotal, double total) {
        this.codigoFactura = UUID.randomUUID().toString();
        this.fechaFactura = LocalDate.now();
        this.subtotal = subtotal;
        this.total = total;
}
}