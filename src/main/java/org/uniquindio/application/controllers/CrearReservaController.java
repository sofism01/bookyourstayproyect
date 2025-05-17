package org.uniquindio.application.controllers;

import com.github.aytchell.qrgen.QrConfigurationException;
import com.github.aytchell.qrgen.QrGenerator;
import com.github.aytchell.qrgen.config.ErrorCorrectionLevel;
import com.github.aytchell.qrgen.config.ImageFileType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.Cliente;
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
    @FXML
    private ImageView qrGenerado;

    @FXML
    private Label lblNombreAlojamiento;
    
    @FXML
    private Label lblPrecioNoche;
    
    @FXML
    private Label lblCapacidad;
    
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
    
    private BookYourStay bookYourStay = BookYourStay.getInstance();
    private Alojamiento alojamientoSeleccionado;
    
    @FXML
    public void initialize() {
        // Obtener el alojamiento seleccionado
        alojamientoSeleccionado = ItemAlojamientoController2.getAlojamientoSeleccionado();
        
        if (alojamientoSeleccionado != null) {
            lblNombreAlojamiento.setText("Nombre: " + alojamientoSeleccionado.getNombre());
            lblPrecioNoche.setText("Precio por noche: $" + alojamientoSeleccionado.getPrecioPorNoche());
            lblCapacidad.setText("Capacidad máxima: " + alojamientoSeleccionado.getCapacidadMax() + " personas");
        } else {
            mostrarAlerta("Error", "No se ha seleccionado ningún alojamiento", Alert.AlertType.ERROR);
        }
        
        // Configurar los datepickers para que solo permitan fechas futuras
        fechaIngreso.setValue(LocalDate.now().plusDays(1));
        fechaSalida.setValue(LocalDate.now().plusDays(2));
        
        // Inicializar el spinner de personas
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 
                        alojamientoSeleccionado != null ? alojamientoSeleccionado.getCapacidadMax() : 10, 1);
        spinnerPersonas.setValueFactory(valueFactory);
        
        // Agregar listeners para actualizar el resumen cuando cambien las fechas
        fechaIngreso.valueProperty().addListener((obs, oldVal, newVal) -> actualizarResumen());
        fechaSalida.valueProperty().addListener((obs, oldVal, newVal) -> actualizarResumen());
        spinnerPersonas.valueProperty().addListener((obs, oldVal, newVal) -> actualizarResumen());
        
        // Inicializar el resumen
        actualizarResumen();
    }
    
    private void actualizarResumen() {
        if (fechaIngreso.getValue() != null && fechaSalida.getValue() != null && alojamientoSeleccionado != null) {
            LocalDate ingreso = fechaIngreso.getValue();
            LocalDate salida = fechaSalida.getValue();
            
            // Validar que la fecha de salida sea posterior a la de ingreso
            if (salida.isAfter(ingreso)) {
                long noches = ChronoUnit.DAYS.between(ingreso, salida);
                float total = noches * alojamientoSeleccionado.getPrecioPorNoche();
                
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
        if (numeroPersonas > alojamientoSeleccionado.getCapacidadMax()) {
            mostrarAlerta("Error", "El número de personas excede la capacidad del alojamiento", Alert.AlertType.ERROR);
            return;
        }
        
        try {
            // Realizar la reserva
            String resultado = bookYourStay.realizarReserva(clienteActual, alojamientoSeleccionado, ingreso, salida, numeroPersonas);
            mostrarAlerta("Reserva Exitosa", resultado, Alert.AlertType.INFORMATION);
            
            // Regresar a la vista de cliente
            Main.actualizarVistaMaximizada(Paths.VISTA_CLIENTE);
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    void cancelarReserva(ActionEvent event) {
        try {
            Main.actualizarVistaMaximizada(Paths.VISTA_CLIENTE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
