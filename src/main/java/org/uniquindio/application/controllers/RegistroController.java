package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.uniquindio.application.Main;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;

public class RegistroController {
    @FXML
    void registroListo(ActionEvent event) {

    }

    @FXML
    void regresarHomeRegistro(ActionEvent event) throws IOException {
        Main.actualizarVista(Paths.HOME);

    }
}
