package org.uniquindio.application.controllers;

import javafx.collections.FXCollections;
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

    @FXML
    void initialize() {

        this.cmbOferta.getItems().addAll("10%", "15%", "20%", "25%", "30%", "35%", "40%", "50%");
    }

    }
