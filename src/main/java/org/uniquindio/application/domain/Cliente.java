package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.simplejavamail.api.email.Email;
import org.uniquindio.application.domain.interfaces.Persona;

import java.io.Serializable;
import java.util.ArrayList;
@Getter
@Setter
public class Cliente implements Persona, Serializable {
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String contrasena;
    private ArrayList<Reserva> reservas;
    private boolean activo;
    private String codigoActivacion;
    private String codigoRecuperacion;
    private Billetera billetera;
    @Builder
    public Cliente(String cedula, String nombre, String apellido, String telefono, String email, String contrasena,
                   ArrayList<Reserva> reservas, boolean activo, String codigoActivacion, String codigoRecuperacion, Billetera billetera) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.contrasena = contrasena;
        this.reservas = new ArrayList<>();
        this.activo = activo;
        this.codigoActivacion = codigoActivacion;
        this.codigoRecuperacion = codigoRecuperacion;
        this.billetera = new Billetera(this);
    }
}
