package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.uniquindio.application.Main;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;

public class RegistroController {
    @FXML
    void registroListo(ActionEvent event) throws IOException{
        //Guardar los datos
        //enviar el correo

        Main.navegarLogin(Paths.INICIAR_SESION, true);
    }

    @FXML
    void regresarHomeRegistro(ActionEvent event) throws IOException {
        Main.actualizarVista(Paths.HOME);

    }
}
