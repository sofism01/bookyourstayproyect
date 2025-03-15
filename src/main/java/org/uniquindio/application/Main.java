package org.uniquindio.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static Stage view;
    @Getter
    public static Main main;
    @Getter
    public static String usuarioActual;

    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        main = this;
        view = stage;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Paths.HOME)));
        Pane pane = (Pane) loader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    public static void actualizarVista(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader((Main.class.getResource(path)));
        Pane pane = (Pane) loader.load();
        Scene scene = new Scene(pane);
        view.setScene(scene);
        view.show();
    }
    public static void setUsuarioActual(String usuarioActual) {

        Main.usuarioActual = usuarioActual;
    }

    public static void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Book Your Stay");
        alert.setHeaderText("Informacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
