package org.uniquindio.application.domain;

import lombok.Getter;
import lombok.Setter;
import org.uniquindio.application.domain.interfaces.Persona;

@Setter
@Getter
public class Administrador implements Persona {
    private String email;
    private String contrasena;
    private String codigoRecuperacion;

    @Override
    public String getEmail(){
        return email;
    }

    @Override
    public String getContrasena(){
        return contrasena;
    }

    @Override
    public String getCedula() {
        return "";
    }

}
