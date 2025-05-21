package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;

public class ItemAlojamientoController2 {
    public ImageView imagenAlojamiento;
    public Label nombreAlojamiento;
    public Label precioAlojamiento;
    public Label capacidadAlojamiento;
    public Label descripcionAlojamiento;
    public Label serviciosAlojamiento;
    public Label ofertaAlojamiento;
    public Button btnReservar;
    
    private Alojamiento alojamiento;
    private Alojamiento alojamientoSeleccionado;

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
        imagenAlojamiento.setImage(new Image(alojamiento.getImagen()));
        nombreAlojamiento.setText(alojamiento.getNombre());
        precioAlojamiento.setText("El precio por noche es: " + alojamiento.getPrecioPorNoche());
        capacidadAlojamiento.setText("Capacidad maxima: " + alojamiento.getCapacidadMax());
        descripcionAlojamiento.setText("Descripcion: " + alojamiento.getDescripcion());
        serviciosAlojamiento.setText("Servicios: " + alojamiento.getServicio());
    }
    
    @FXML
    public void seleccionarAlojamiento(ActionEvent event) {
        alojamientoSeleccionado = this.alojamiento;
        try {
            FXMLLoader loader = Main.actualizarVista(Paths.CREAR_RESERVA);
            ((CrearReservaController) loader.getController()).setAlojamiento(alojamientoSeleccionado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verResenas(ActionEvent actionEvent) {
        alojamientoSeleccionado = this.alojamiento;
        try {
            FXMLLoader loader = Main.abrirVentana(Paths.RESENAS);
            ((ResenasController) loader.getController()).setAlojamiento(alojamientoSeleccionado);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}





