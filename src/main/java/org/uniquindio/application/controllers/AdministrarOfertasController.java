package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.uniquindio.application.Main;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;

public class AdministrarOfertasController {
    @FXML
    private Button btnRegresar;

    @FXML
    private ComboBox<String> cmbOferta;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    void agregarOferta(ActionEvent event) {

    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        Main.actualizarVista(Paths.VISTA_ADMIN);
    }

}
