package org.uniquindio.application.controllers;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.Habitacion;
import org.uniquindio.application.domain.Hotel;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
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

public class VistaAdminController implements Observable {

    public Label lblPrecioNoche;
    public Label lblCapacidad;
    @FXML
    private Button btnAgregarHabitacion;

    @FXML
    private Label lblCOstoAdicional;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuButton btnOpciones;

    private List<Habitacion> habitaciones;

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

    private Alojamiento alojamientoSeleccionado; //alojamiento seleccionado de la tabla

    @FXML
    void editarAlojamiento(ActionEvent event) {
        if (alojamientoSeleccionado!= null) {
            try {

                bookYourStay.editarAlojamiento(
                        alojamientoSeleccionado.getId(),
                         txtNombre.getText(),
                         Ciudad.valueOf(cmbCiudad.getValue().toUpperCase()),
                         txtDescripcion.getText(),
                         Float.parseFloat(txtPrecio.getText()),
                         Integer.parseInt(cmbCapacidad.getValue()),
                         imageView.getImage().getUrl()
                //cmbServicios.getCheckModel().getCheckedItems()
                        );

                limpiarCampos();
                actualizarAlojamiento();

                Main.mostrarMensaje("Alojamiento actualizado correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                Main.mostrarMensaje(ex.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            Main.mostrarMensaje("Debe seleccionar un alojamiento de la tabla", Alert.AlertType.WARNING);
        }
    }


    @FXML
    void irAAgregarHabitacion(ActionEvent event) throws IOException {
        FXMLLoader loader = Main.abrirVentana(Paths.AGREGARHABITACION);
        CrearHabitacionController controller = loader.getController();
        controller.setObservable(this);

        if(alojamientoSeleccionado!=null){
            if(alojamientoSeleccionado instanceof Hotel){
                controller.setHabitaciones(((Hotel) alojamientoSeleccionado).getHabitaciones());
            }
        }else{
            controller.setHabitaciones( habitaciones );
        }
    }

    @FXML
    void eliminarAlojamiento(ActionEvent event) {
        Alojamiento alojamiento = tablaAlojamientos.getSelectionModel().getSelectedItem();
        if (alojamiento != null) {
            try {
                bookYourStay.eliminarAlojamiento(alojamiento.getId());

                limpiarCampos();
                actualizarAlojamiento();
                Main.mostrarMensaje("Alojamiento eliminado correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception exception) {
                Main.mostrarMensaje(exception.getMessage(), Alert.AlertType.ERROR);
            }

        } else {
            Main.mostrarMensaje("Debe seleccionar un alojamiento de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void guardarAlojamiento(ActionEvent event) {
        try {
            Tipo tipo = Tipo.valueOf(cmbTipo.getValue().toUpperCase());
            String nombre = txtNombre.getText();
            Ciudad ciudad = Ciudad.valueOf(cmbCiudad.getValue().toUpperCase());
            String descripcion = txtDescripcion.getText();
            List<String> servicio = cmbServicios.getCheckModel().getCheckedItems();

            Image image = imageView.getImage();

            switch (tipo) {
                case Tipo.CASA -> {
                    float precioPorNoche = Float.parseFloat(txtPrecio.getText());
                    int capacidadMax = Integer.parseInt(cmbCapacidad.getValue());
                    double costoAdicional = Double.parseDouble(txtCostoAdicional.getText());
                    bookYourStay.agreagrCasa(tipo, nombre, ciudad, descripcion, precioPorNoche, capacidadMax, image.getUrl(), servicio, costoAdicional);
                }
                case Tipo.APARTAMENTO ->
                {
                    float precioPorNoche = Float.parseFloat(txtPrecio.getText());
                    int capacidadMax = Integer.parseInt(cmbCapacidad.getValue());
                    double costoAdicional = Double.parseDouble(txtCostoAdicional.getText());
                    bookYourStay.agreagrApartamento(tipo, nombre, ciudad, descripcion, precioPorNoche, capacidadMax, image.getUrl(), servicio, costoAdicional);
                }
                case Tipo.HOTEL ->
                {
                    System.out.println(habitaciones);
                    bookYourStay.agreagrHotel(tipo, nombre, ciudad, descripcion, image.getUrl(), servicio, habitaciones);
                }
            }


            limpiarCampos();
            actualizarAlojamiento();
            Main.mostrarMensaje("Alojamiento guardado correctamente", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            e.printStackTrace();
            Main.mostrarMensaje(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        cmbTipo.setValue(null);
        txtNombre.clear();
        cmbCiudad.setValue(null);
        txtDescripcion.clear();
        cmbServicios.getCheckModel().clearChecks();
        txtPrecio.clear();
        cmbCapacidad.setValue(null);
        imageView.setImage(null);
        txtCostoAdicional.clear();
        this.habitaciones = new ArrayList<>();
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
    void verOpciones() {

        MenuItem item1 = new MenuItem("Agregar ofertas");
        MenuItem item2 = new MenuItem("Cerrar sesion");
        MenuItem item3 = new MenuItem("Ver estadisticas");


        // Añadir acciones a cada ítem
        item1.setOnAction(e -> {
            try {
                agregarOfertas();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        item2.setOnAction(e -> {
            try {
                cerrarSesion();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        item3.setOnAction(e -> {
            try {
                verEstadisticas();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnOpciones.getItems().addAll(item1, item2, item3);

    }

    private void cerrarSesion() throws IOException {
        Main.actualizarVista(Paths.HOME);
    }

    private void verEstadisticas() throws IOException {
        Main.actualizarVista(Paths.ESTADISTICAS);
    }

    private void agregarOfertas() throws IOException {
        Main.actualizarVista(Paths.ADMINISTRAR_OFERTAS);
    }

    @FXML
    void initialize() {

        verOpciones();

        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad().name()));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colServicios.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServicio().toString()));
        colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecioPorNoche())));
        colCapacidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCapacidadMax())));

        //Inicializar lista observable y cargar alojamientos
        alojamientosObservable = FXCollections.observableArrayList();
        cargarAlojamientos();

        List<File> f = new ArrayList<>();
        f.add(new File("*.jpg"));
        f.add(new File("*.png"));
        //Cargar categorias en el ComboBox
        this.cmbTipo.getItems().addAll("Casa", "Apartamento", "Hotel");
        this.cmbCiudad.getItems().addAll("ARMENIA", "PEREIRA", "MEDELLIN", "BOGOTA", "CARTAGENA");
        this.cmbCapacidad.getItems().addAll("1", "2", "3", "4", "5");
        this.cmbServicios.getItems().addAll("PISCINA", "WIFI", "DESAYUNO", "GARAJE", "CALEFACCION", "TV", "GUARDAEQUIPAJE", "AGUA_CALIENTE", "AIRE_ACONDICIONADO" );

        this.habitaciones = new ArrayList<>();

        //Evento click en la tabla
        tablaAlojamientos.setOnMouseClicked(e -> {
            //Obtener alojamiento seleccionado
            alojamientoSeleccionado = tablaAlojamientos.getSelectionModel().getSelectedItem();

            if (alojamientoSeleccionado != null) {
                txtNombre.setText(alojamientoSeleccionado.getNombre());
                cmbCiudad.setValue(alojamientoSeleccionado.getCiudad().toString());
                txtDescripcion.setText(alojamientoSeleccionado.getDescripcion());
                 // combo servicios
                txtPrecio.setText(String.valueOf(alojamientoSeleccionado.getPrecioPorNoche()));
                cmbCapacidad.setValue(String.valueOf(alojamientoSeleccionado.getCapacidadMax()));
                imageView.setImage(new Image(alojamientoSeleccionado.getImagen()));
            }

        });
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
                    lblPrecioNoche.setVisible(true);
                    lblCapacidad.setVisible(true);
                    cmbCapacidad.setVisible(true);
                    txtPrecio.setVisible(true);
                    btnAgregarHabitacion.setVisible(false);
                }
                case Tipo.HOTEL -> {
                    txtCostoAdicional.setVisible(false);
                    lblCOstoAdicional.setVisible(false);
                    lblPrecioNoche.setVisible(false);
                    lblCapacidad.setVisible(false);
                    cmbCapacidad.setVisible(false);
                    txtPrecio.setVisible(false);
                    btnAgregarHabitacion.setVisible(true);
                }

            }
        }

    }

    @Override
    public void obtenerHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }
}


