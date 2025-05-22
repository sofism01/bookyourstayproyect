package org.uniquindio.application.controllers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.Hotel;

public class ItemAlojamientoController {

    public ImageView imagenAlojamiento;
    public Label nombreAlojamiento;
    public Label precioAlojamiento;

    public void setAlojamiento(Alojamiento alojamiento) {
        imagenAlojamiento.setImage( new Image(alojamiento.getImagen()));
        nombreAlojamiento.setText(alojamiento.getNombre());
        if(alojamiento instanceof Hotel){
            precioAlojamiento.setText(((Hotel) alojamiento).getPrecioRango());
        }else{
            precioAlojamiento.setText("El precio por noche es: "+alojamiento.getPrecioPorNoche());
        }
    }

}
