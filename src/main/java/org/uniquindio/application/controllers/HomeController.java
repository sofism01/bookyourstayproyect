package org.uniquindio.application.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController {

    @FXML
    private HBox alojamientosInicio;

    @FXML
    private ResourceBundle resources;

    @FXML
    private ComboBox<String> cmbFiltro;

    @FXML
    private URL location;

    @FXML
    private TextField txtBusqueda;


    private ObservableList<Alojamiento> alojamientosObservable;

    @FXML
    private ScrollPane scrollAlojamientos;

    private BookYourStay bookYourStay;

    public HomeController(){
        this.bookYourStay = BookYourStay.getInstance();
    }

    @FXML
    void iniciarSesionHome(ActionEvent event) throws IOException {
        Main.navegarLogin(Paths.INICIAR_SESION, false);

    }

    @FXML
    void iniciarRegistro(ActionEvent event) throws IOException {
        Main.actualizarVista(Paths.REGISTRARSE);

    }


    @FXML
    void seleccionarFiltro(ActionEvent event) {

    }

    @FXML
    void initialize() {
        this.cmbFiltro.getItems().addAll("CIUDAD", "TIPO");
        dibujarAlojamientos(bookYourStay.listarAlojamientos());
    }

    public void dibujarAlojamientos(List<Alojamiento> alojamientos){
        alojamientosInicio.getChildren().clear();;
        try {
            for (Alojamiento alojamiento : alojamientos) {
                alojamientosInicio.getChildren().add(itemAlojamiento(alojamiento));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Node itemAlojamiento(Alojamiento alojamiento) throws Exception{
        FXMLLoader loader = new FXMLLoader((Main.class.getResource("/view/itemAlojamiento.fxml")));
        Parent parent = loader.load();
        ItemAlojamientoController itemAlojamientoController = loader.getController();
        itemAlojamientoController.setAlojamiento(alojamiento);

        return parent;
    }

    public void filtrar(ActionEvent actionEvent) {

        String ciudad = txtBusqueda.getText();
        List<Alojamiento> alojamientos = bookYourStay.buscarPorCiudad(Ciudad.valueOf(ciudad));
        if(!alojamientos.isEmpty()){
            dibujarAlojamientos(alojamientos);
        }else{
            Main.mostrarMensaje("No hay alojamientos", Alert.AlertType.INFORMATION);
        }

    }
}
