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
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;

public class PanelClienteController {

    BookYourStay bookYourStay = BookYourStay.getInstance();

    @FXML
    private StackPane panelCliente;

    public void verAlojamientos(ActionEvent actionEvent){
        Parent node = cargarPanel("/view/verAlojamientos.fxml");
        if (node != null) {
            panelCliente.getChildren().setAll(node);
        } else {
            System.err.println("No se pudo cargar el panel: /view/verAlojamientos.fxml");
        }
    }



    /**
     * Muestra las reservas realizadas por el cliente
     */
    public void verReservas(ActionEvent actionEvent) {
        try {
            Parent node = cargarPanel(Paths.VER_RESERVAS);
            if (node != null) {
                panelCliente.getChildren().setAll(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Main.mostrarMensaje("Error al cargar las reservas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void crearReserva(ActionEvent actionEvent) throws IOException {
        Parent node = cargarPanel("/view/crearReserva.fxml");
        panelCliente.getChildren().setAll(node);
    }


    public void abrirBilletera(ActionEvent actionEvent) {
        try {
            Parent node = cargarPanel("/view/abrirBilletera.fxml");
            if (node != null) {
                panelCliente.getChildren().setAll(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Main.mostrarMensaje("Error al abrir la billetera: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void administrarCuenta(ActionEvent actionEvent) throws IOException {
        Parent node = cargarPanel("/view/administrarCuenta.fxml");
        panelCliente.getChildren().setAll(node);
    }


    private Parent cargarPanel(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            if (loader.getLocation() == null) {
                throw new IOException("No se pudo encontrar el archivo FXML: " + fxmlFile);
            }
            Parent node = loader.load();
            if (node == null) {
                throw new IOException("Error al cargar el archivo FXML: " + fxmlFile);
            }
            return node;
        } catch (Exception e) {
            e.printStackTrace();
            Main.mostrarMensaje("Error al cargar el panel: " + e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }





}
