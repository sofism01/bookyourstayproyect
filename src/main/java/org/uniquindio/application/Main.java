package org.uniquindio.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import org.uniquindio.application.controllers.IniciarSesionController;
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
    public static void actualizarVistaMaximizada(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader((Main.class.getResource(path)));
        Pane pane = (Pane) loader.load();
        Scene scene = new Scene(pane);
        view.setScene(scene);
        view.setMaximized(true);
        view.show();
    }
    public static void actualizarVista(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader((Main.class.getResource(path)));
        Pane pane = (Pane) loader.load();
        Scene scene = new Scene(pane);
        view.setScene(scene);
        view.show();
    }
    public static FXMLLoader abrirVentana(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader((Main.class.getResource(path)));
        Pane pane = (Pane) loader.load();
        Scene scene = new Scene(pane);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        return loader;

    }
    public static void navegarLogin(String path, boolean nuevo) throws IOException {
        FXMLLoader loader = new FXMLLoader((Main.class.getResource(path)));
        Pane pane = (Pane) loader.load();
        IniciarSesionController controller = loader.getController();
        controller.mostrarCodigoNuevoUsuario(nuevo);
        Scene scene = new Scene(pane);
        view.setScene(scene);
        view.show();
    }

    public static void setUsuarioActual(String usuarioActual) {

        Main.usuarioActual = usuarioActual;
    }

    public static void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Book Your Stay");
        alert.setHeaderText("Informacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana) throws IOException {


        // Cargar la vista
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
        Parent root = loader.load();

        // Crear la escena
        Scene scene = new Scene(root);

        // Crear un nuevo escenario (ventana)
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(tituloVentana);

        // Mostrar la nueva ventana
        stage.show();
        return loader;

    }

}
