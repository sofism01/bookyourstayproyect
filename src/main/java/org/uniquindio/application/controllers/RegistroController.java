package org.uniquindio.application.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.interfaces.Persona;
import org.uniquindio.application.utils.Paths;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;


public class RegistroController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtCedula;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtTelefono;

BookYourStay bookYourStay = BookYourStay.getInstance();

    private ObservableList<Persona> personasObservable;

    @FXML
    void initialize() {

    }

    @FXML
    void registroListo(ActionEvent event) throws IOException{
        try {
            bookYourStay.registrarCliente(
                    txtCedula.getText(),
                    txtNombres.getText(),
                    txtApellidos.getText(),
                    txtTelefono.getText(),
                    txtEmail.getText(),
                    txtContrasena.getText()
                    );
            limpiarCampos();
            actualizarPersonas();
            mostrarAlerta("Usuario guardado correctamente", Alert.AlertType.INFORMATION);

            Main.navegarLogin(Paths.INICIAR_SESION, true);

        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();

    }

    private void actualizarPersonas() {
        personasObservable.setAll(bookYourStay.listarPersonas());
    }

    private void limpiarCampos() {
        txtCedula.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtContrasena.clear();
    }

    @FXML
    void regresarHomeRegistro(ActionEvent event) throws IOException {
        Main.actualizarVista(Paths.HOME);

    }
}
