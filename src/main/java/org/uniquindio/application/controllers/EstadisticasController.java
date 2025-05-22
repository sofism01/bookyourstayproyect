package org.uniquindio.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.utils.Paths;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EstadisticasController {

    @FXML
    private HBox panelEstadisticas;

    private final BookYourStay bookYourStay = BookYourStay.getInstance();

    @FXML
    public void initialize() {
        // Crear un gráfico de pastel
        crearGraficoPastelOcupacion();
        // Crear gráfico de rentabilidad
        crearGraficoPastelRentabilidad();

    }

    private void crearGraficoPastelOcupacion() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Ocupación por Tipo de Alojamiento");

        List<Double> porcentajes = bookYourStay.calcularOcupacionPorcentualPorTipo();

        pieChart.getData().add(new PieChart.Data("Casa", porcentajes.get(0)));
        pieChart.getData().add(new PieChart.Data("Apartamento", porcentajes.get(1)));
        pieChart.getData().add(new PieChart.Data("Hotel", porcentajes.get(2)));

        panelEstadisticas.getChildren().add(pieChart);
    }

    private void crearGraficoPastelRentabilidad() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Tipos de Alojamiento Más Rentables");

        Map<String, Double> gananciasPorTipo = bookYourStay.listarTiposAlojamientoMasRentables();

        for (Map.Entry<String, Double> entry : gananciasPorTipo.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        panelEstadisticas.getChildren().add(pieChart);
    }

    public void Volver(ActionEvent actionEvent) throws IOException {
        Main.actualizarVista(Paths.VISTA_ADMIN);
    }
}
