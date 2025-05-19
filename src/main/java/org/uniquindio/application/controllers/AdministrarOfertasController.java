package org.uniquindio.application.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.Oferta;
import org.uniquindio.application.domain.Reserva;
import org.uniquindio.application.enums.Porcentaje;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.uniquindio.application.domain.BookYourStay.bookYourStay;

public class AdministrarOfertasController {
    @FXML
    private Button btnRegresar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<Oferta, String> colDescuento;

    @FXML
    private TableColumn<Oferta, String> colFechaFin;

    @FXML
    private TableColumn<Oferta, String> colFechaInicio;

    @FXML
    private ComboBox<String> cmbOferta;

    @FXML
    private TableView<Oferta> tblOfertas;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicio;

    private Oferta ofertaSeleccionada; //oferta seleccionado de la tabla

    BookYourStay bookYourStay = BookYourStay.getInstance();

    private ObservableList<Oferta> listaOfertas;

    @FXML
    void agregarOferta(ActionEvent event) throws Exception {
        try {
            bookYourStay.agregarOferta(
                    cmbOferta.getValue(),
                    dpFechaInicio.getValue(),
                    dpFechaFin.getValue()
            );
            Main.mostrarMensaje("Oferta agregada", Alert.AlertType.INFORMATION);
            actualizarOferta();
        } catch (Exception e) {
            Main.mostrarMensaje(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void actualizarOferta() {
        listaOfertas.setAll(bookYourStay.listarOfertas());
    }

    private void limpiarCampos(){
        cmbOferta.setValue(null);
        dpFechaInicio.setValue(null);
        dpFechaFin.setValue(null);
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        Main.actualizarVista(Paths.VISTA_ADMIN);
    }

    @FXML
    void initialize() {

        for (Porcentaje porcentaje : Porcentaje.values()) {
            this.cmbOferta.getItems().add(porcentaje.toString());
        }

        tblOfertas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    btnEliminar.setDisable(newSelection == null);
                    btnEditar.setDisable(newSelection == null);
                }
        );
        colDescuento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescuento()));
        colFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaInicio().toString()));
        colFechaFin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaFin().toString()));
        //Inicializar lista observable y cargar alojamientos
        listaOfertas = FXCollections.observableArrayList();
        cargarOfertas();

        //Evento click en la tabla
        tblOfertas.setOnMouseClicked(e -> {
            //Obtener oferta seleccionado
            ofertaSeleccionada = tblOfertas.getSelectionModel().getSelectedItem();
            //Deshabilitar boton eliminar si no hay oferta seleccionada
            btnEliminar.setDisable(ofertaSeleccionada == null);
            btnEditar.setDisable(ofertaSeleccionada == null);
            //Mostrar datos de la oferta seleccionada
            if (ofertaSeleccionada != null) {
                cmbOferta.setValue(ofertaSeleccionada.getDescuento());
                dpFechaInicio.setValue(ofertaSeleccionada.getFechaInicio());
                dpFechaFin.setValue(ofertaSeleccionada.getFechaFin());
            }

        });

    }


    private void cargarOfertas() {
        listaOfertas.setAll(bookYourStay.listarOfertas());
        tblOfertas.setItems(listaOfertas);
    }


    @FXML
    void editarOferta(ActionEvent event) {
        Oferta ofertaSeleccionada = tblOfertas.getSelectionModel().getSelectedItem();
        if (ofertaSeleccionada != null) {
            try {
                bookYourStay.editarOferta(
                        ofertaSeleccionada.getDescuento(),
                        dpFechaInicio.getValue(),
                        dpFechaFin.getValue(),
                        cmbOferta.getValue()
                );
                actualizarOferta();
                limpiarCampos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        actualizarTablaOfertas();
    }

    @FXML
    void eliminarOferta(ActionEvent event) {
        Oferta ofertaSeleccionada = tblOfertas.getSelectionModel().getSelectedItem();
        if (ofertaSeleccionada != null) {
            try {
                bookYourStay.eliminarOferta(ofertaSeleccionada.getId());
                listaOfertas.remove(ofertaSeleccionada);
                tblOfertas.refresh();
                limpiarCampos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
actualizarOferta();
    }

    private void actualizarTablaOfertas() {
        List<Oferta> ofertasActualizadas = BookYourStay.getInstance().listarOfertas();
        listaOfertas.setAll(ofertasActualizadas);
        tblOfertas.refresh();
    }

    }
