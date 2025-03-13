package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IniciarSesionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtContrasena;

    @FXML
    private TextField txtEmail;

    @FXML
    void iniciarSesion(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        String contrasena = txtContrasena.getText();
        String rol = BookYourStay.iniciarSesion(email, contrasena);

        switch (rol) {
            case "CLIENTE":
                Main.setUsuarioActual(email);
                Main.setUsuarioActual(contrasena);
                Main.actualizarVista(Paths.VISTA_CLIENTE);
                break;
            default:
                Main.mostrarMensaje("Email o contraseña incorrecto");
        }

    }

    @FXML
    void initialize() {

    }

}
