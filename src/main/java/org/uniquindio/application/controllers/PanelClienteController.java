package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
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



    public void crearReserva(ActionEvent actionEvent) throws IOException {
        Parent node = cargarPanel("/view/crearReserva.fxml");
        panelCliente.getChildren().setAll(node);
    }


    public void abrirBilletera(ActionEvent actionEvent) throws IOException {
        Parent node = cargarPanel("/view/abrirBilletera.fxml");
        panelCliente.getChildren().setAll(node);
    }


    private Parent cargarPanel(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent node = loader.load();
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cerrarVentanaActual() {
        Stage stage = (Stage) panelCliente.getScene().getWindow();
        stage.close();
    }



}
