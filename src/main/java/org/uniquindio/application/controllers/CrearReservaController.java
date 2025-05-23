package org.uniquindio.application.controllers;

import com.github.aytchell.qrgen.QrConfigurationException;
import com.github.aytchell.qrgen.QrGenerator;
import com.github.aytchell.qrgen.config.ErrorCorrectionLevel;
import com.github.aytchell.qrgen.config.ImageFileType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import org.uniquindio.application.Main;
import org.uniquindio.application.controllers.singleton.PanelClienteSingleton;
import org.uniquindio.application.domain.*;
import org.uniquindio.application.utils.Paths;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CrearReservaController {

    public TableView<Habitacion> habitaciones;
    public TableColumn<Habitacion, String> colDescripcion;
    public TableColumn<Habitacion, String> colCapacidad;
    public TableColumn<Habitacion, String> colPrecio;
    @FXML
    private ImageView qrGenerado;

    @FXML
    private Label lblNombreAlojamiento;
    
    @FXML
    private Label lblPrecioNoche;
    
    @FXML
    private Label lblCapacidad;

    @FXML
    private Label lblCostoAdicional;
    
    @FXML
    private DatePicker fechaIngreso;
    
    @FXML
    private DatePicker fechaSalida;
    
    @FXML
    private Spinner<Integer> spinnerPersonas;
    
    @FXML
    private Label lblResumenNoches;
    
    @FXML
    private Label lblResumenTotal;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnConfirmar;

    private Habitacion habitacionSeleccionada; //habitacion seleccionado de la tabla
    private BookYourStay bookYourStay = BookYourStay.getInstance();
    private Alojamiento alojamientoSeleccionado;
    private static PanelClienteSingleton panelClienteSingleton = PanelClienteSingleton.getInstance();
    
    @FXML
    public void initialize() {
        
        // Configurar los datepickers para que solo permitan fechas futuras
        fechaIngreso.setValue(LocalDate.now().plusDays(1));
        fechaSalida.setValue(LocalDate.now().plusDays(2));
        
        // Agregar listeners para actualizar el resumen cuando cambien las fechas
        fechaIngreso.valueProperty().addListener((obs, oldVal, newVal) -> actualizarResumen());
        fechaSalida.valueProperty().addListener((obs, oldVal, newVal) -> actualizarResumen());
        spinnerPersonas.valueProperty().addListener((obs, oldVal, newVal) -> actualizarResumen());
        
        // Inicializar el resumen
        actualizarResumen();

        //Evento click en la tabla
        habitaciones.setOnMouseClicked(e -> {
            //Obtener habitacion seleccionado
            habitacionSeleccionada = habitaciones.getSelectionModel().getSelectedItem();});
    }
    
    private void actualizarResumen() {
        if (fechaIngreso.getValue() != null && fechaSalida.getValue() != null && alojamientoSeleccionado != null) {
            LocalDate ingreso = fechaIngreso.getValue();
            LocalDate salida = fechaSalida.getValue();
            
            // Validar que la fecha de salida sea posterior a la de ingreso
            if (salida.isAfter(ingreso)) {
                long noches = ChronoUnit.DAYS.between(ingreso, salida);
                float total = noches * alojamientoSeleccionado.getPrecioPorNoche();
                if (alojamientoSeleccionado instanceof org.uniquindio.application.domain.Hotel && habitacionSeleccionada != null) {
                    total = noches * habitacionSeleccionada.getPrecio();
                } else {
                    total = noches * alojamientoSeleccionado.getPrecioPorNoche();
                    if (alojamientoSeleccionado instanceof org.uniquindio.application.domain.Casa) {
                        total += (float) ((org.uniquindio.application.domain.Casa) alojamientoSeleccionado).getCostoAseo();
                    } else if (alojamientoSeleccionado instanceof org.uniquindio.application.domain.Apartamento) {
                        total += (float) ((org.uniquindio.application.domain.Apartamento) alojamientoSeleccionado).getCostoMantenimiento();
                    }
                }

                lblResumenNoches.setText("Noches: " + noches);
                lblResumenTotal.setText("Total: $" + total);
            } else {
                lblResumenNoches.setText("Fechas inválidas");
                lblResumenTotal.setText("Total: $0");
            }
        }
    }

    public void generarQR(String idFactura) throws Exception {
        QrGenerator generator = new QrGenerator()
                .withSize(300, 300)
                .withMargin(3)
                .as(ImageFileType.PNG)
                .withErrorCorrection(ErrorCorrectionLevel.Q);

        Path tmpImg = generator.writeToTmpFile(idFactura);

        Path dirDestino = Path.of("src/main/resources/qr");
        if (!Files.exists(dirDestino)) {
            Files.createDirectories(dirDestino);
        }
        Path archivoDestino = dirDestino.resolve("qr_" + idFactura + ".png");

        Files.copy(tmpImg, archivoDestino, StandardCopyOption.REPLACE_EXISTING);

        qrGenerado.setImage(new Image(archivoDestino.toUri().toString()));
    }

    public ImageView getQrGenerado() {
        return qrGenerado;
    }

    @FXML
    void confirmarReserva(ActionEvent event) {
        if (alojamientoSeleccionado == null) {
            mostrarAlerta("Error", "No se ha seleccionado ningún alojamiento", Alert.AlertType.ERROR);
            return;
        }

        // Verificar que el cliente esté logueado
        Cliente clienteActual = bookYourStay.getClienteActual();
        if (clienteActual == null) {
            mostrarAlerta("Error", "Debe iniciar sesión para hacer una reserva", Alert.AlertType.ERROR);
            return;
        }

        // Obtener datos del formulario
        LocalDate ingreso = fechaIngreso.getValue();
        LocalDate salida = fechaSalida.getValue();
        int numeroPersonas = spinnerPersonas.getValue();

        // Validar fechas
        if (ingreso == null || salida == null || !salida.isAfter(ingreso)) {
            mostrarAlerta("Error", "Las fechas seleccionadas no son válidas", Alert.AlertType.ERROR);
            return;
        }

        // Validar capacidad
        if (alojamientoSeleccionado instanceof Hotel) {

            if (habitacionSeleccionada == null) {
                mostrarAlerta("Error", "Debe seleccionar una habitación", Alert.AlertType.ERROR);
                return;
            }
            int capacidadHabitacion = Integer.parseInt(habitacionSeleccionada.getCapacidad());
            int capacidad = spinnerPersonas.getValue();
            if (capacidad > capacidadHabitacion) {
                mostrarAlerta("Error", "El número de personas excede la capacidad de la habitación seleccionada", Alert.AlertType.ERROR);
                return;
            }
        } else {
            if (numeroPersonas > alojamientoSeleccionado.getCapacidadMax()) {
                mostrarAlerta("Error", "El número de personas excede la capacidad del alojamiento", Alert.AlertType.ERROR);
                return;
            }
        }

        try {
            // Realizar la reserva
            String resultado = bookYourStay.realizarReserva(clienteActual, alojamientoSeleccionado, ingreso, salida, numeroPersonas);
            mostrarAlerta("Reserva Exitosa", resultado, Alert.AlertType.INFORMATION);

            // Regresar a la vista de cliente
            panelClienteSingleton.cargarPanel(Paths.VER_ALOJAMIENTOS);
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    
    @FXML
    void cancelarReserva(ActionEvent event) {
        panelClienteSingleton.cargarPanel(Paths.VER_ALOJAMIENTOS);
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setAlojamiento(Alojamiento alojamientoSeleccionado) {
        this.alojamientoSeleccionado = alojamientoSeleccionado;

        if (alojamientoSeleccionado != null) {
            lblNombreAlojamiento.setText("Nombre: " + alojamientoSeleccionado.getNombre());
            lblPrecioNoche.setText("Precio por noche: $" + alojamientoSeleccionado.getPrecioPorNoche());
            lblCapacidad.setText("Capacidad máxima: " + alojamientoSeleccionado.getCapacidadMax() + " personas");
            if(alojamientoSeleccionado instanceof Casa){
                lblCostoAdicional.setText("Costo adicional: $" + ((Casa) alojamientoSeleccionado).getCostoAseo());
            } else if (alojamientoSeleccionado instanceof Apartamento) {
                lblCostoAdicional.setText("Costo adicional: $" + ((Apartamento) alojamientoSeleccionado).getCostoMantenimiento());
            } else
            lblCostoAdicional.setText("Costo adicional: $0");
            if (alojamientoSeleccionado instanceof Hotel) {
                habitaciones.setVisible(true);
                colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
                colCapacidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCapacidad()));
                colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));

                habitaciones.setItems(FXCollections.observableArrayList(((Hotel) alojamientoSeleccionado).getHabitaciones()));

                habitaciones.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        lblCapacidad.setText("Capacidad máxima: " + newVal.getCapacidad() + " personas");
                        lblPrecioNoche.setText("Precio por noche: $" + newVal.getPrecio());

                        // Actualizar el spinner de personas según la capacidad de la habitación seleccionada
                        int capacidadMax = 1;
                        try {
                            capacidadMax = Integer.parseInt(newVal.getCapacidad());
                        } catch (NumberFormatException e) {
                            capacidadMax = 1;
                        }
                        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, capacidadMax);
                        spinnerPersonas.setValueFactory(valueFactory);

                        // Validar si el número de personas es mayor a la capacidad de la habitación
                        int numPersonas = spinnerPersonas.getValue();
                        if (numPersonas > capacidadMax) {
                            mostrarAlerta("Advertencia", "El número de personas excede la capacidad de la habitación seleccionada", Alert.AlertType.WARNING);
                        }
                    }
                });
            } else {
                // Inicializar el spinner de personas
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, alojamientoSeleccionado.getCapacidadMax());
                spinnerPersonas.setValueFactory(valueFactory);
            }

        } else {
            mostrarAlerta("Error", "No se ha seleccionado ningún alojamiento", Alert.AlertType.ERROR);
        }
    }
}
