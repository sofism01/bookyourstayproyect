package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Administrador;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.Cliente;
import org.uniquindio.application.domain.interfaces.Persona;
import org.uniquindio.application.utils.Paths;
import java.io.IOException;

public class RecuperarPassowordController {

    public Button btnCambiarPassoword;
    @FXML
    private Label lblCodigoRecuperacion;

    @FXML
    private TextField txtCodigoRecuperacion;

    @FXML
    private Label lblContrasenaNueva;

    @FXML
    private Button btnSolicitarCodigo;

    @FXML
    private Button btnValidar;

    @FXML
    private PasswordField txtContrasenaNueva;

    @FXML
    private TextField txtEmail;

    private BookYourStay bookYourStay;

    public RecuperarPassowordController(){
        this.bookYourStay = BookYourStay.getInstance();
    }

    @FXML
    void cambiarPassword(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        String contrasena = txtContrasenaNueva.getText();
        Persona persona = bookYourStay.cambiarContrasena(email, contrasena);

        if(persona != null) {

            Main.setUsuarioActual(email);
            Main.setUsuarioActual(contrasena);

            if (persona instanceof Administrador) {
                Main.actualizarVista(Paths.VISTA_ADMIN);
            } else if (persona instanceof Cliente) {
                // Establecer el cliente actual en BookYourStay
                bookYourStay.setClienteActual((Cliente) persona);
                Main.actualizarVistaMaximizada(Paths.VISTA_CLIENTE);
                cerrarVentana();
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
        lblCodigoRecuperacion.setVisible(false);
        txtCodigoRecuperacion.setVisible(false);
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
                btnCambiarPassoword.setVisible(true);
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
            Persona persona = bookYourStay.buscarPersona(email);

            if(persona==null){
                Main.mostrarMensaje("El correo  no existe.", Alert.AlertType.INFORMATION);
                return;
            }

            String codigo = bookYourStay.enviarCodigoRecuperacion(email);
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
    }

    public void cerrarVentana(){
        Stage stage = (Stage) btnCambiarPassoword.getScene().getWindow();
        stage.close();
    }

}
