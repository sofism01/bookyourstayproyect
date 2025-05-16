package org.uniquindio.application.controllers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.uniquindio.application.domain.Alojamiento;

public class ItemAlojamientoController2 {
    public ImageView imagenAlojamiento;
    public Label nombreAlojamiento;
    public Label precioAlojamiento;
    public Label capacidadAlojamiento;
    public Label descripcionAlojamiento;
    public Label serviciosAlojamiento;
    public Label ofertaAlojamiento;


    public void setAlojamiento(Alojamiento alojamiento) {
        imagenAlojamiento.setImage( new Image(alojamiento.getImagen()));
        nombreAlojamiento.setText(alojamiento.getNombre());
        precioAlojamiento.setText("El precio por noche es: "+alojamiento.getPrecioPorNoche());
        capacidadAlojamiento.setText("Capacidad maxima: "+alojamiento.getCapacidadMax());
        descripcionAlojamiento.setText("Descripcion: "+alojamiento.getDescripcion());
        serviciosAlojamiento.setText("Servicios: "+alojamiento.getServicio());
        if(alojamiento.getOferta() != null){
            ofertaAlojamiento.setText("Oferta: "+alojamiento.getOferta().get(0).getDescuento()+"% desde el "+alojamiento.getOferta().get(0).getFechaInicio()+" hasta el "+alojamiento.getOferta().get(0).getFechaFin());
    }}

        }



