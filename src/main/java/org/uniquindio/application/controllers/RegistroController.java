package org.uniquindio.application.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.Cliente;
import org.uniquindio.application.domain.interfaces.Persona;
import org.uniquindio.application.utils.Paths;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import static org.simplejavamail.api.mailer.config.TransportStrategy.SMTP_TLS;


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


    private ObservableList<Persona> personasObservable;

    @FXML
    void initialize() {

    }

    BookYourStay bookYourStay = BookYourStay.getInstance();
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

            //enviar el correo
            enviarCorreoBienvenida(txtNombres.getText(), txtEmail.getText());

        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }

        Main.navegarLogin(Paths.INICIAR_SESION, true);
    }

    private void enviarCorreoBienvenida(String nombre, String emailUser) {
        Email email = EmailBuilder.startingBlank()
                .from("bookYourStay", "bookyourstay7@gmail.com")
                .to("melisa", "melisayramona123@gmail.com")
                .withSubject("¡Bienvenido/a a Book Your Stay!")
                .withPlainText("Hola " + nombre + ",\n\nGracias por registrarte en Book Your Stay.\n¡Esperamos que tengas una excelente experiencia!")
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "bookyourstay7@gmail.com", "ramona3000")
                .withTransportStrategy(SMTP_TLS)
                .buildMailer();

        mailer.sendMail(email);
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
