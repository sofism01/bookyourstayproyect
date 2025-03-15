package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.uniquindio.application.Main;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;
import java.net.URL;

import java.util.Arrays;
import java.util.ResourceBundle;

public class HomeController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private ComboBox<String> combCiudad;

    @FXML
    private URL location;

    @FXML
    void iniciarSesionHome(ActionEvent event) throws IOException {
        Main.actualizarVista(Paths.INICIAR_SESION);

    }

    @FXML
    void iniciarRegistro(ActionEvent event) throws IOException {
        Main.actualizarVista(Paths.REGISTRARSE);

    }

    @FXML
    void seleccionarCiudad(ActionEvent event) {

    }

    @FXML
    void initialize() {
        this.combCiudad.getItems().addAll("ARMENIA", "PEREIRA", "MEDELLIN", "BOGOTA", "CARTAGENA");

    }
}
