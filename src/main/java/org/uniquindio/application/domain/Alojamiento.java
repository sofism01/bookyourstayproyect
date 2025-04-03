package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public abstract class Alojamiento {

    public Tipo tipo;
    protected String nombre;
    protected Ciudad ciudad;
    protected String descripcion;
    protected double precioPorNoche;
    protected int capacidadMax;
    public Image imagen;
    protected List<Servicio> servicio;


}
