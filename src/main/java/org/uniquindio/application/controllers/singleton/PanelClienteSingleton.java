package org.uniquindio.application.controllers.singleton;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import lombok.Setter;
import org.uniquindio.application.Main;

import java.io.IOException;

public class PanelClienteSingleton {

    @Setter
    private StackPane panelCliente;
    private static PanelClienteSingleton instancia;

    private PanelClienteSingleton(){

    }

    public static PanelClienteSingleton getInstance(){
        if(instancia == null){
            instancia = new PanelClienteSingleton();
        }

        return instancia;
    }

    public FXMLLoader cargarPanel(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            if (loader.getLocation() == null) {
                throw new IOException("No se pudo encontrar el archivo FXML: " + fxmlFile);
            }
            Parent node = loader.load();
            if (node == null) {
                throw new IOException("Error al cargar el archivo FXML: " + fxmlFile);
            }

            panelCliente.getChildren().setAll(node);

            return loader;
        } catch (Exception e) {
            e.printStackTrace();
            Main.mostrarMensaje("Error al cargar el panel: " + e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }

}
