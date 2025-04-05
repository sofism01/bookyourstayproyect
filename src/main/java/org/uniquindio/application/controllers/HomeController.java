package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.uniquindio.application.Main;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;
import java.net.URL;

import java.util.Arrays;
import java.util.ResourceBundle;

public class HomeController {

    @FXML
    private HBox alojamientosInicio;

    @FXML
    private ResourceBundle resources;

    @FXML
    private ComboBox<String> combCiudad;

    @FXML
    private URL location;

    @FXML
    void iniciarSesionHome(ActionEvent event) throws IOException {
        Main.navegarLogin(Paths.INICIAR_SESION, false);

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

        try {

            for (int i = 0; i < 1; i++) {
                alojamientosInicio.getChildren().add(
                        itemAlojamiento()
                );
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Node itemAlojamiento() throws Exception{
        FXMLLoader loader = new FXMLLoader((Main.class.getResource("/view/itemAlojamiento.fxml")));
        return loader.load();
    }
}
