package org.uniquindio.application.controllers;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.utils.Paths;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.uniquindio.application.domain.BookYourStay.bookYourStay;

public class VistaAdminController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Button btnElegirFoto;

    @FXML
    private ComboBox<String> cmbCapacidad;

    @FXML
    private ComboBox<String> cmbCiudad;

    @FXML
    private ComboBox<String> cmbNumHabitacion;

    @FXML
    private ComboBox<String> cmbServicios;

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private ImageView imageView;

    @FXML
    private TableView<Alojamiento> tablaAlojamientos;


    @FXML
    void editarAlojamiento(ActionEvent event) {

    }

    @FXML
    void eliminarAlojamiento(ActionEvent event) {

    }

    @FXML
    void guardarAlojamiento(ActionEvent event) {

    }

    @FXML
    void fileChooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo", "*.jpg", "*.png"));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString()); // Cargar imagen desde la ruta seleccionada
            imageView.setImage(image); // Mostrar imagen en el ImageView
        }
    }

    @FXML
    void initialize() {
        List<File> f = new ArrayList<>();
        f.add(new File("*.jpg"));
        f.add(new File("*.png"));
        //Cargar categorias en el ComboBox
        this.cmbTipo.getItems().addAll("Casa", "Apartamento", "Hotel");
        this.cmbCiudad.getItems().addAll("ARMENIA", "PEREIRA", "MEDELLIN", "BOGOTA", "CARTAGENA");
        this.cmbNumHabitacion.getItems().addAll("1", "2", "3", "4", "5");
        this.cmbCapacidad.getItems().addAll("1", "2", "3", "4", "5");
        this.cmbServicios.getItems().addAll("PISCINA", "WIFI", "DESAYUNO", "GARAJE");
        }

}


