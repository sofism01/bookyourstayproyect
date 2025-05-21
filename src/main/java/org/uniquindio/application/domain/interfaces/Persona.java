package org.uniquindio.application.domain.interfaces;

import lombok.AllArgsConstructor;

import java.io.Serializable;


public interface Persona extends Serializable {

    public String getEmail();
    public String getContrasena();
    public String getCedula();

    public void setContrasena(String nueva);

}

