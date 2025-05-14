package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import org.uniquindio.application.domain.BookYourStay;

public class PanelClienteController {

BookYourStay bookYourStay = BookYourStay.getInstance();

    @FXML
    private StackPane panelCliente;

    public void verAlojamientos(ActionEvent actionEvent) {
        Parent node = cargarPanel("/view/verAlojamientos.fxml");


        // Se reemplaza el contenido del panel principal
        panelCliente.getChildren().setAll(node);
    }


    public void crearReserva(ActionEvent actionEvent) {
        Parent node = cargarPanel("/view/crearReserva.fxml");


        // Se reemplaza el contenido del panel principal
        panelCliente.getChildren().setAll(node);
    }


    public void abrirBilletera(ActionEvent actionEvent) {
        Parent node = cargarPanel("/view/abrirBilletera.fxml");


        // Se reemplaza el contenido del panel principal
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



}
