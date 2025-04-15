package org.uniquindio.application.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Habitacion;
import org.uniquindio.application.utils.Paths;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CrearHabitacionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEditarHabitacion;

    @FXML
    private Button btnFotoHabitacion;

    @FXML
    private Button btnGuardarHabitacion;

    @FXML
    private ComboBox<String> cmbCapacidad;

    @FXML
    private TableColumn<Habitacion, String> colDescripcion;

    @FXML
    private TableColumn<Habitacion, String> colNumero;

    @FXML
    private TableColumn<Habitacion, String> colPrecio;

    @FXML
    private ImageView imagenHabitacion;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtNumHabitacion;

    @FXML
    private TextField txtPrecio;

    @FXML
    void fileChooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo", "*.jpg", "*.png"));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString()); // Cargar imagen desde la ruta seleccionada
            imagenHabitacion.setImage(image); // Mostrar imagen en el ImageView
        }
        this.cmbCapacidad.getItems().addAll("1", "2", "3", "4", "5");
    }

    @FXML
    void editarHabitacion(ActionEvent event) {

    }

    @FXML
    void guardarHabitacion(ActionEvent event) {
        String numero = txtNumHabitacion.getText();
        String precio = txtPrecio.getText();
        String capacidad = cmbCapacidad.getValue();
        String descripcion = txtPrecio.getText();
    }

    @FXML
    void regresarAVistaAdmin(ActionEvent event) throws IOException {
        Main.actualizarVista(Paths.AGREGARHABITACION);
    }

    @FXML
    void initialize(URL url, ResourceBundle resourceBundle) {
        List<File> f = new ArrayList<>();
        f.add(new File("*.jpg"));
        f.add(new File("*.png"));

        colNumero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumero()));
        colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));

    }
}
