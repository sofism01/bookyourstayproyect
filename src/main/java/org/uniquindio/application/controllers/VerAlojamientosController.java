package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.BookYourStay;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VerAlojamientosController {

    public HBox contenedorAlojamientos;
    @FXML
    private ResourceBundle resources;



    @FXML
    private URL location;

    BookYourStay bookYourStay = BookYourStay.getInstance();

    @FXML
    void hacerReserva(ActionEvent event) {

    }

    public void dibujarAlojamientos(List<Alojamiento> alojamientos){
        contenedorAlojamientos.getChildren().clear();;
        try {
            for (Alojamiento alojamiento : alojamientos) {
                contenedorAlojamientos.getChildren().add(itemAlojamiento2(alojamiento));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Node itemAlojamiento2(Alojamiento alojamiento) throws Exception{
        FXMLLoader loader = new FXMLLoader((Main.class.getResource("/view/itemAlojamiento2.fxml")));
        Parent parent = loader.load();
        ItemAlojamientoController2 itemAlojamientoController2 = loader.getController();
        itemAlojamientoController2.setAlojamiento(alojamiento);

        return parent;
    }
    @FXML
    void initialize() {
        dibujarAlojamientos(bookYourStay.listarAlojamientos());


    }
}
