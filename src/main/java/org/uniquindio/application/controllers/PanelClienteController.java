package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.uniquindio.application.Main;
import org.uniquindio.application.controllers.singleton.PanelClienteSingleton;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;

public class PanelClienteController {

    BookYourStay bookYourStay = BookYourStay.getInstance();
    PanelClienteSingleton panelClienteSingleton;

    @FXML
    private StackPane panelCliente;

    @FXML
    public void initialize(){
        panelClienteSingleton = PanelClienteSingleton.getInstance();
        panelClienteSingleton.setPanelCliente(panelCliente);
    }

    public void verAlojamientos(ActionEvent actionEvent){
        panelClienteSingleton.cargarPanel(Paths.VER_ALOJAMIENTOS);
    }


    /**
     * Muestra las reservas realizadas por el cliente
     */
    public void verReservas(ActionEvent actionEvent) {
        panelClienteSingleton.cargarPanel(Paths.VER_RESERVAS);
    }

    public void crearReserva(ActionEvent actionEvent){
        panelClienteSingleton.cargarPanel(Paths.CREAR_RESERVA);
    }


    public void abrirBilletera(ActionEvent actionEvent) {
        panelClienteSingleton.cargarPanel(Paths.ABRIR_BILLETERA);
    }

    public void administrarCuenta(ActionEvent actionEvent) {
        panelClienteSingleton.cargarPanel("/view/administrarCuenta.fxml");
    }


}
