package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IniciarSesionController {

    public TextField txtCodigo;
    public Label labelCodigo;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtContrasena;

    @FXML
    private TextField txtEmail;


    private boolean mostrarCodigo;


    @FXML
    void iniciarSesion(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        String contrasena = txtContrasena.getText();
        String rol = BookYourStay.iniciarSesion(email, contrasena);
        System.out.print(rol);

        if (rol.equals("ADMINISTRADOR")) {
            Main.setUsuarioActual(email);
            Main.setUsuarioActual(contrasena);
            Main.actualizarVista(Paths.VISTA_ADMIN);
        } else {
            Main.mostrarMensaje("Email o contraseña incorrecto", Alert.AlertType.ERROR);
        }
    }

    @FXML

    void regresarHomeInicioSesion (ActionEvent event) throws IOException {
        Main.actualizarVista(Paths.HOME);
    }

    @FXML
    void initialize() {

        if(!mostrarCodigo){
            labelCodigo.setVisible(false);
            txtCodigo.setVisible(false);
        }
    }

    public void mostrarCodigoNuevoUsuario(boolean mostrarCodigo) {
        this.mostrarCodigo = mostrarCodigo;
        labelCodigo.setVisible(mostrarCodigo);
        txtCodigo.setVisible(mostrarCodigo);
    }

}
