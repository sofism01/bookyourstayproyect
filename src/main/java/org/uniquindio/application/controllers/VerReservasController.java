package org.uniquindio.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.Cliente;
import org.uniquindio.application.domain.Reserva;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VerReservasController {

    @FXML
    private TableView<Reserva> tablaReservas;
    
    @FXML
    private TableColumn<Reserva, String> colAlojamiento;
    
    @FXML
    private TableColumn<Reserva, LocalDate> colFechaIngreso;
    
    @FXML
    private TableColumn<Reserva, LocalDate> colFechaSalida;
    
    @FXML
    private TableColumn<Reserva, Integer> colPersonas;
    
    @FXML
    private TableColumn<Reserva, String> colPrecio;
    
    @FXML
    private Button btnDetalles;
    
    @FXML
    private Button btnVolver;
    
    private BookYourStay bookYourStay;
    private Cliente clienteActual;
    private ObservableList<Reserva> listaReservas;
    
    @FXML
    public void initialize() {
        bookYourStay = BookYourStay.getInstance();
        clienteActual = bookYourStay.getClienteActual();
        
        if (clienteActual == null) {
            mostrarAlerta("Error", "No hay cliente logueado", Alert.AlertType.ERROR);
            return;
        }
        
        // Configurar columnas de la tabla
        colAlojamiento.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue().getAlojamiento();
            String nombreAlojamiento = alojamiento != null ? alojamiento.getNombre() : "No disponible";
            return javafx.beans.binding.Bindings.createStringBinding(() -> nombreAlojamiento);
        });
        
        colFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
        colFechaIngreso.setCellFactory(col -> new TableCell<Reserva, LocalDate>() {
            @Override
            protected void updateItem(LocalDate fecha, boolean empty) {
                super.updateItem(fecha, empty);
                if (empty || fecha == null) {
                    setText(null);
                } else {
                    setText(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });
        
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        colFechaSalida.setCellFactory(col -> new TableCell<Reserva, LocalDate>() {
            @Override
            protected void updateItem(LocalDate fecha, boolean empty) {
                super.updateItem(fecha, empty);
                if (empty || fecha == null) {
                    setText(null);
                } else {
                    setText(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });
        
        colPersonas.setCellValueFactory(new PropertyValueFactory<>("numeroPersonas"));
        
        colPrecio.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue().getAlojamiento();
            float precio = alojamiento != null ? alojamiento.getPrecioPorNoche() : 0;
            long dias = cellData.getValue().getTotalDias();
            return javafx.beans.binding.Bindings.createStringBinding(() -> 
                    String.format("$%.2f", precio * dias));
        });
        
        cargarReservas();
        
        // Habilitar el botón de detalles solo cuando se seleccione una reserva
        tablaReservas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnDetalles.setDisable(newSelection == null);
        });
    }
    
    private void cargarReservas() {
        if (clienteActual.getReservas() == null) {
            clienteActual.setReservas(new ArrayList<>());
        }
        
        listaReservas = FXCollections.observableArrayList(clienteActual.getReservas());
        tablaReservas.setItems(listaReservas);
        
        if (listaReservas.isEmpty()) {
            mostrarAlerta("Información", "No tienes reservas realizadas", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    void verDetallesReserva(ActionEvent event) {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            Alojamiento alojamiento = reservaSeleccionada.getAlojamiento();
            LocalDate fechaIngreso = reservaSeleccionada.getFechaIngreso();
            LocalDate fechaSalida = reservaSeleccionada.getFechaSalida();
            int personas = reservaSeleccionada.getNumeroPersonas();
            
            long dias = reservaSeleccionada.getTotalDias();
            float precio = alojamiento.getPrecioPorNoche();
            float total = precio * dias;
            
            String mensaje = String.format(
                    "Detalles de la Reserva\n\n" +
                    "Alojamiento: %s\n" +
                    "Ubicación: %s\n" +
                    "Fecha de ingreso: %s\n" +
                    "Fecha de salida: %s\n" +
                    "Número de noches: %d\n" +
                    "Personas: %d\n" +
                    "Precio por noche: $%.2f\n" +
                    "Total pagado: $%.2f",
                    alojamiento.getNombre(),
                    alojamiento.getCiudad(),
                    fechaIngreso.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    fechaSalida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    dias,
                    personas,
                    precio,
                    total
            );
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Detalles de la Reserva");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }
    }
    
    @FXML
    void volver(ActionEvent event) {
        try {
            Main.actualizarVistaMaximizada(Paths.VISTA_CLIENTE);
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al volver: " + e.getMessage(), Alert.AlertType.ERROR);
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
