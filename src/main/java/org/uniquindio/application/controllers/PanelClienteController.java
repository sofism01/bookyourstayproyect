package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public class PanelClienteController {


    @FXML
    private StackPane panelClienteController;

    public void verAlojamientos(ActionEvent actionEvent) {
        Parent node = cargarPanel("/verAlojamientos.fxml");


        // Se reemplaza el contenido del panel principal
        panelClienteController.getChildren().setAll(node);
    }


    public void crearReserva(ActionEvent actionEvent) {
        Parent node = cargarPanel("/crearReserva.fxml");


        // Se reemplaza el contenido del panel principal
        panelClienteController.getChildren().setAll(node);
    }


    public void abrirBilletera(ActionEvent actionEvent) {
        Parent node = cargarPanel("/abrirBilletera.fxml");


        // Se reemplaza el contenido del panel principal
        panelClienteController.getChildren().setAll(node);
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
