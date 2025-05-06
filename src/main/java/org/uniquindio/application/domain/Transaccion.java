package org.uniquindio.application.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Transaccion {
    private String id;
    private float monto;
    private LocalDateTime fecha;
    private Billetera billeteraOrigen;
    private float comision;

}
