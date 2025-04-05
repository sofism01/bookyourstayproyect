package org.uniquindio.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.simplejavamail.api.email.Email;
import org.uniquindio.application.domain.interfaces.Persona;

import java.io.Serializable;
import java.util.ArrayList;
@Getter
@Setter
@AllArgsConstructor


public class Cliente implements Persona {
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String contrasena;
    private ArrayList<Reserva> reservas;
    private boolean activo;


    public Cliente() {
        reservas = new ArrayList<>();
    }
}
