package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Billetera;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.Cliente;

import java.net.URL;
import java.util.ResourceBundle;

public class AbrirBilleteraController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtSaldo;

    private BookYourStay bookYourStay;
    private Cliente clienteActual;
    private Billetera billetera;
    @FXML
    void consultarSaldo(ActionEvent event) {
        try {
            if (billetera == null) {
                Main.mostrarMensaje("Error: No hay billetera disponible", Alert.AlertType.ERROR);
                return;
            }
            float saldoTransaccionesBilletera = billetera.consultarSaldo();
            Main.mostrarMensaje("Su saldo es: " + saldoTransaccionesBilletera, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            Main.mostrarMensaje("Error al consultar saldo: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void recargarSaldo(ActionEvent event) {
        try {
            // Verificar que la billetera exista
            if (billetera == null) {
                Main.mostrarMensaje("Error: No hay billetera disponible", Alert.AlertType.ERROR);
                return;
            }
            
            // Obtener el monto a recargar
            String montoTexto = txtSaldo.getText().trim();
            if (montoTexto.isEmpty()) {
                Main.mostrarMensaje("Por favor ingrese un monto a recargar", Alert.AlertType.WARNING);
                return;
            }
            
            // Validar que sea un número válido
            float monto;
            try {
                monto = Float.parseFloat(montoTexto);
            } catch (NumberFormatException e) {
                Main.mostrarMensaje("El monto debe ser un número válido", Alert.AlertType.ERROR);
                return;
            }
            
            // Validar que sea un monto positivo
            if (monto <= 0) {
                Main.mostrarMensaje("El monto a recargar debe ser mayor a cero", Alert.AlertType.WARNING);
                return;
            }
            
            // Realizar la recarga
            billetera.depositar(monto);
            
            // Guardar los cambios
            bookYourStay.guardarDatosUsuario(bookYourStay.getPersonas());
            
            // Mostrar mensaje de éxito con el nuevo saldo
            float nuevoSaldo = billetera.consultarSaldo();
            
            // Crear alerta personalizada para mostrar el resultado
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Recarga Exitosa");
            alerta.setHeaderText("Recarga Completada");
            alerta.setContentText(String.format("¡Se han recargado $%.2f a tu billetera!\n\nSaldo anterior: $%.2f\nSaldo actual: $%.2f", 
                    monto, nuevoSaldo - monto, nuevoSaldo));
            alerta.showAndWait();
            
            // Limpiar el campo
            txtSaldo.clear();
            
        } catch (Exception e) {
            Main.mostrarMensaje("Error al recargar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void initialize() {
        try {
            bookYourStay = BookYourStay.getInstance();
            clienteActual = bookYourStay.getClienteActual();
            if (clienteActual == null) {
                Main.mostrarMensaje("Error: No hay cliente actual", Alert.AlertType.ERROR);
                return;
            }
            billetera = clienteActual.getBilletera();
            if (billetera == null) {
                Main.mostrarMensaje("Error: El cliente no tiene billetera", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            Main.mostrarMensaje("Error al inicializar la billetera: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
