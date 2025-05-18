package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.Cliente;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;

public class AdministrarCuentaController {
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;

    private final BookYourStay bookYourStay = BookYourStay.getInstance();

    @FXML
    public void initialize() {
        Cliente cliente = BookYourStay.getInstance().getClienteActual();
        if (cliente != null) {
            txtCedula.setText(cliente.getCedula());
            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtTelefono.setText(cliente.getTelefono());
            txtEmail.setText(cliente.getEmail());
        }
    }


    @FXML
    private void confirmarEdicion(ActionEvent event) {
        Cliente cliente = bookYourStay.getClienteActual();
        if (cliente != null) {
            cliente.setCedula(txtCedula.getText());
            cliente.setNombre(txtNombre.getText());
            cliente.setApellido(txtApellido.getText());
            cliente.setTelefono(txtTelefono.getText());
            cliente.setEmail(txtEmail.getText());

            // Guardar cambios en la serialización
            bookYourStay.guardarDatosUsuario(bookYourStay.listarPersonas());

            Main.mostrarMensaje("Datos actualizados correctamente", Alert.AlertType.INFORMATION);
        }
    }

    public void cerrarSesion(ActionEvent actionEvent) throws IOException {
        Main.actualizarVista(Paths.HOME);
    }
}
