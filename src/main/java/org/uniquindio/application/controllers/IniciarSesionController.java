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
    private Label lblCodigoRecuperacion;

    @FXML
    private TextField txtCodigoRecuperacion;

    @FXML
    private Label lblContrasena;

    @FXML
    private Label lblContrasenaNueva;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRecuperarContrasena;

    @FXML
    private Button btnSolicitarCodigo;

    @FXML
    private Button btnValidar;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private PasswordField txtContrasenaNueva;

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
            lblCodigoRecuperacion.setVisible(false);
            txtCodigoRecuperacion.setVisible(false);
        }
    }

    public void mostrarCodigoNuevoUsuario(boolean mostrarCodigo) {
        this.mostrarCodigo = mostrarCodigo;
        labelCodigo.setVisible(mostrarCodigo);
        txtCodigo.setVisible(mostrarCodigo);
    }

    @FXML
    void recuperarContrasena(ActionEvent event) {

        lblContrasena.setVisible(false);
        txtContrasena.setVisible(false);
        btnSolicitarCodigo.setVisible(true);
        btnIniciarSesion.setVisible(false);

        String email = txtEmail.getText();
        if (email == null || email.isEmpty()) {
            Main.mostrarMensaje("Por favor ingrese su correo electrónico.", Alert.AlertType.WARNING);
            btnRecuperarContrasena.setVisible(false);

        }


}

    public void validarCodigoRecuperacion(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String codigoIngresado = txtCodigoRecuperacion.getText();

        Persona persona = bookYourStay.buscarPersona(email);
        if (persona instanceof Cliente) {
            Cliente cliente = (Cliente) persona;
            if (cliente.getCodigoRecuperacion() != null && cliente.getCodigoRecuperacion().equals(codigoIngresado)) {
                Main.mostrarMensaje("Código correcto. Ahora puede cambiar su contraseña.", Alert.AlertType.INFORMATION);
                txtContrasenaNueva.setVisible(true);
                lblContrasenaNueva.setVisible(true);
                btnValidar.setVisible(false);
                lblCodigoRecuperacion.setVisible(false);
                txtCodigoRecuperacion.setVisible(false);
                btnIniciarSesion.setVisible(true);
                cliente.setContrasena(txtContrasenaNueva.getText());
                bookYourStay.guardarDatosUsuario(bookYourStay.listarPersonas());
            } else {
                Main.mostrarMensaje("El código ingresado es incorrecto.", Alert.AlertType.ERROR);
            }
        } else if (persona instanceof Administrador) {
            Administrador admin = (Administrador) persona;
            if (admin.getCodigoRecuperacion() != null && admin.getCodigoRecuperacion().equals(codigoIngresado)) {
                Main.mostrarMensaje("Código correcto. Ahora puede cambiar su contraseña.", Alert.AlertType.INFORMATION);
                // Habilitar cambio de contraseña
            } else {
                Main.mostrarMensaje("El código ingresado es incorrecto.", Alert.AlertType.ERROR);
            }
        }
    }


        public void solicitarCodigo(ActionEvent actionEvent) {
            String email = txtEmail.getText();
            if (email == null || email.isEmpty()) {
                Main.mostrarMensaje("Por favor ingrese su correo electrónico.", Alert.AlertType.WARNING);
                return;
            }
            try {
                String codigo = bookYourStay.enviarCodigoRecuperacion(email);
                Persona persona = bookYourStay.buscarPersona(email);
                if (persona instanceof Cliente) {
                    ((Cliente) persona).setCodigoRecuperacion(codigo);
                    // Guarda los cambios en la lista de usuarios
                    bookYourStay.guardarDatosUsuario(bookYourStay.listarPersonas());
                }
                Main.mostrarMensaje("Se ha enviado un código de recuperación a su correo.", Alert.AlertType.INFORMATION);
                btnSolicitarCodigo.setVisible(false);
                btnValidar.setVisible(true);
                txtCodigoRecuperacion.setVisible(true);
                lblCodigoRecuperacion.setVisible(true);
            } catch (Exception e) {
                Main.mostrarMensaje(e.getMessage(), Alert.AlertType.ERROR);
            }
    }}
