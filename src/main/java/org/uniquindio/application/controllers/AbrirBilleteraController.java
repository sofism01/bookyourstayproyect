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

    BookYourStay bookYourStay = BookYourStay.getInstance();
    Cliente clienteActual = bookYourStay.getClienteActual();
    Billetera billetera = clienteActual.getBilletera();
    @FXML
    void consultarSaldo(ActionEvent event) {
        float SaldoTransaccionesBilletera = billetera.consultarSaldo();
        Main.mostrarMensaje("Su saldo es:" + SaldoTransaccionesBilletera, Alert.AlertType.INFORMATION);    }

    @FXML
    void recargarSaldo(ActionEvent event) {

    }

    @FXML
    void initialize() {


    }

}
