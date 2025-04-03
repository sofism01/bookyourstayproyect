package org.uniquindio.application.controllers;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;
import org.uniquindio.application.utils.Paths;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.uniquindio.application.domain.BookYourStay.bookYourStay;

public class VistaAdminController {

    @FXML
    private Button btnAgregarHabitacion;

    @FXML
    private Label lblCOstoAdicional;

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
    private CheckComboBox<String> cmbServicios;

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private ImageView imageView;

    @FXML
    private TableView<Alojamiento> tablaAlojamientos;

    @FXML
    private TableColumn<Alojamiento, String> colCapacidad;

    @FXML
    private TableColumn<Alojamiento, String> colCiudad;

    @FXML
    private TableColumn<Alojamiento, String> colDescripcion;

    @FXML
    private TableColumn<Alojamiento, String> colNombre;

    @FXML
    private TableColumn<Alojamiento, String> colPrecio;

    @FXML
    private TableColumn<Alojamiento, String> colServicios;

    @FXML
    private TextField txtCostoAdicional;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;


    private ObservableList<Alojamiento> alojamientosObservable;

    private Alojamiento alojamientoSeleccionado; //contacto seleccionado de la tabla

    @FXML
    void editarAlojamiento(ActionEvent event) {

    }

    @FXML
    void eliminarAlojamiento(ActionEvent event) {

    }

    @FXML
    void guardarAlojamiento(ActionEvent event) {
        try {
            Tipo tipo = Tipo.valueOf(cmbTipo.getValue().toUpperCase());
            String nombre = txtNombre.getText();
            Ciudad ciudad = Ciudad.valueOf(cmbCiudad.getValue());
            String descripcion = txtDescripcion.getText();
            double precioPorNoche = Double.parseDouble(txtPrecio.getText());
            int capacidadMax = Integer.parseInt(cmbCapacidad.getValue());
            List<String> servicio = cmbServicios.getCheckModel().getCheckedItems();
            System.out.println(servicio);
            double costoAdicional = Double.parseDouble(txtCostoAdicional.getText());

            Image image = imageView.getImage();

            switch (tipo) {
                case Tipo.CASA ->
                        bookYourStay.agreagrCasa(tipo, nombre, ciudad, descripcion, precioPorNoche, capacidadMax, image, servicio, costoAdicional);
                case Tipo.APARTAMENTO ->
                        bookYourStay.agreagrApartamento(tipo, nombre, ciudad, descripcion, precioPorNoche, capacidadMax, image, servicio, costoAdicional);
                case Tipo.HOTEL ->
                        bookYourStay.agreagrHotel(tipo, nombre, ciudad, descripcion, precioPorNoche, capacidadMax, image, servicio);
            }


            limpiarCampos();
            actualizarAlojamiento();
            mostrarAlerta("Alojamiento guardado correctamente", Alert.AlertType.INFORMATION);

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

    private void limpiarCampos() {
        cmbTipo.setValue(null);
        txtNombre.clear();
        txtDescripcion.clear();
        cmbServicios.getCheckModel().clearChecks();
        txtPrecio.clear();
        cmbCapacidad.setValue(null);
        imageView.setImage(null);
        txtCostoAdicional.clear();
    }

    public void actualizarAlojamiento() {
        alojamientosObservable.setAll(bookYourStay.listarAlojamientos());
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

        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colCiudad.setCellValueFactory(cellData -> new SimpleStringProperty());
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colServicios.setCellValueFactory(cellData -> new SimpleStringProperty());
        colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty());
        colCapacidad.setCellValueFactory(cellData -> new SimpleStringProperty());

        //Inicializar lista observable y cargar contactos
        alojamientosObservable = FXCollections.observableArrayList();
        cargarAlojamientos();

        List<File> f = new ArrayList<>();
        f.add(new File("*.jpg"));
        f.add(new File("*.png"));
        //Cargar categorias en el ComboBox
        this.cmbTipo.getItems().addAll("Casa", "Apartamento", "Hotel");
        this.cmbCiudad.getItems().addAll("ARMENIA", "PEREIRA", "MEDELLIN", "BOGOTA", "CARTAGENA");
        this.cmbCapacidad.getItems().addAll("1", "2", "3", "4", "5");
        this.cmbServicios.getItems().addAll("PISCINA", "WIFI", "DESAYUNO", "GARAJE");
        }

    private void cargarAlojamientos() {
        alojamientosObservable.setAll(bookYourStay.listarAlojamientos());
        tablaAlojamientos.setItems(alojamientosObservable);
    }

    public void elegirTipo(ActionEvent e){
        String seleccion = cmbTipo.getValue();

        if(seleccion !=null) {
            Tipo tipo = Tipo.valueOf(seleccion.toUpperCase());

            switch (tipo) {

                case Tipo.CASA, Tipo.APARTAMENTO -> {
                    txtCostoAdicional.setVisible(true);
                    lblCOstoAdicional.setVisible(true);
                    btnAgregarHabitacion.setVisible(false);
                }
                case Tipo.HOTEL -> {
                    txtCostoAdicional.setVisible(false);
                    lblCOstoAdicional.setVisible(false);
                    btnAgregarHabitacion.setVisible(true);
                }

            }
        }

    }

}


