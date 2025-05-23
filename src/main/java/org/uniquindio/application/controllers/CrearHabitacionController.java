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
import lombok.Setter;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.Habitacion;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Tipo;
import org.uniquindio.application.observer.Observable;
import org.uniquindio.application.utils.Paths;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.uniquindio.application.domain.BookYourStay.bookYourStay;

public class CrearHabitacionController {

    public TableColumn<Habitacion, String> colCapacidad;
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
    private TableView<Habitacion> tablaHabitaciones;

    @FXML
    private ImageView imagenHabitacion;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtNumHabitacion;

    @FXML
    private TextField txtPrecio;

    private List<Habitacion> habitaciones;

    private ObservableList<Habitacion> observableHabitaciones;
    private Habitacion habitacionSeleccionada; //habitacion seleccionado de la tabla

    @Setter
    private Observable observable;

    @FXML
    void fileChooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo", "*.jpg", "*.png"));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString()); // Cargar imagen desde la ruta seleccionada
            imagenHabitacion.setImage(image); // Mostrar imagen en el ImageView
        }
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
        actualizarAlojamiento();
    }

    @FXML
    void editarHabitacion(ActionEvent event) {
        if (habitacionSeleccionada!= null) {
            try {

                bookYourStay.editarHabitacion(
                       habitacionSeleccionada.getNumero(),
                        Float.parseFloat(txtPrecio.getText()),
                        cmbCapacidad.getValue(),
                        txtDescripcion.getText()

                );

                limpiarCampos();
                actualizarHabitacion();

                Main.mostrarMensaje("Habitacion actualizada correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                Main.mostrarMensaje(ex.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            Main.mostrarMensaje("Debe seleccionar una habitacion de la tabla", Alert.AlertType.WARNING);
        }
    }

    public void actualizarHabitacion() {
        observableHabitaciones.setAll(bookYourStay.listarHabitaciones());
    }

    private void limpiarCampos() {
        txtNumHabitacion.clear();
        txtPrecio.clear();
        cmbCapacidad.getSelectionModel().clearSelection();
        txtDescripcion.clear();
        imagenHabitacion.setImage(null);
    }

    @FXML
    void guardarHabitacion(ActionEvent event) {
        String numero = txtNumHabitacion.getText();
        String precio = txtPrecio.getText();
        String capacidad = cmbCapacidad.getValue();
        String descripcion = txtDescripcion.getText();

        try {
            Habitacion habitacion = new Habitacion(numero, Float.parseFloat(precio), capacidad, descripcion, new ArrayList<>());
            if (habitaciones == null) {
                habitaciones = new ArrayList<>();
            }
            habitaciones.add(habitacion);
            actualizarAlojamiento();
            Main.mostrarMensaje("Habitación creada corectamente", Alert.AlertType.INFORMATION);
        }catch (Exception e){
            Main.mostrarMensaje(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void regresarAVistaAdmin(ActionEvent event) throws IOException {
        observable.obtenerHabitaciones(habitaciones);
        //Cerrar la ventana
        btnEditarHabitacion.getScene().getWindow().hide();
    }

    public void actualizarAlojamiento() {
        observableHabitaciones.setAll(habitaciones);
    }

    private void cargarHabitaciones() {
        observableHabitaciones.setAll(bookYourStay.listarHabitaciones());
        tablaHabitaciones.setItems(observableHabitaciones);
    }


    @FXML
    void initialize() {

        observableHabitaciones = FXCollections.observableArrayList();
       cargarHabitaciones();



        this.cmbCapacidad.getItems().addAll("1", "2", "3", "4", "5");

        List<File> f = new ArrayList<>();
        f.add(new File("*.jpg"));
        f.add(new File("*.png"));

        colNumero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumero()));
        colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colCapacidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCapacidad()));

        // Habilitar los botones solo cuando se seleccione una reserva
        tablaHabitaciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnEditarHabitacion.setDisable(newSelection == null);
//Evento click en la tabla
            tablaHabitaciones.setOnMouseClicked(e -> {
                //Obtener habitacion seleccionado
                habitacionSeleccionada = tablaHabitaciones.getSelectionModel().getSelectedItem();
                if (habitacionSeleccionada != null) {
                    txtDescripcion.setText(habitacionSeleccionada.getDescripcion());
                    txtPrecio.setText(String.valueOf(habitacionSeleccionada.getPrecio()));
                    cmbCapacidad.setValue(habitacionSeleccionada.getCapacidad());
                    txtNumHabitacion.setText(habitacionSeleccionada.getNumero());
                        }

    });
        });
    }


}
