package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Administrador;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.Cliente;
import org.uniquindio.application.domain.interfaces.Persona;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IniciarSesionController {

    public TextField txtCodigo;

    public Label labelCodigo;

    @FXML
    private Label lblContrasena;

    @FXML
    private Button btnRecuperarContrasena;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private TextField txtEmail;

    private BookYourStay bookYourStay;

    private boolean mostrarCodigo;

    public IniciarSesionController(){
        this.bookYourStay = BookYourStay.getInstance();
    }

    @FXML
    void iniciarSesion(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        String contrasena = txtContrasena.getText();
        String codigo = txtCodigo.getText();
        Persona persona = null;

        if(mostrarCodigo){
            persona = bookYourStay.activarCuenta(email, contrasena, codigo);
        }else{
            persona = bookYourStay.iniciarSesion(email, contrasena);
        }

        if(persona != null) {

            Main.setUsuarioActual(email);
            Main.setUsuarioActual(contrasena);

            if (persona instanceof Administrador) {
                Main.actualizarVista(Paths.VISTA_ADMIN);
            } else if (persona instanceof Cliente) {
                // Establecer el cliente actual en BookYourStay
                bookYourStay.setClienteActual((Cliente) persona);
                Main.actualizarVistaMaximizada(Paths.VISTA_CLIENTE);
            }
        }else{
            Main.mostrarMensaje("Sus datos son incorrectos", Alert.AlertType.ERROR);
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

    @FXML
    void recuperarContrasena(ActionEvent event) throws Exception{
        Main.abrirVentana(Paths.VISTA_RECUPERAR_CONTRASENA);
    }

}
