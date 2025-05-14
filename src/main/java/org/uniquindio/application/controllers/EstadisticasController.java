package org.uniquindio.application.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import org.uniquindio.application.domain.BookYourStay;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EstadisticasController {

    @FXML
    private VBox panelEstadisticas;

    private final BookYourStay bookYourStay = BookYourStay.getInstance();

    @FXML
    public void initialize() {
        // Crear un gráfico de pastel
        crearGraficoPastelOcupacion();
    }

    private void crearGraficoPastelOcupacion() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Porcentaje de Ocupación por Tipo de Alojamiento");

        List<Double> porcentajes = bookYourStay.calcularOcupacionPorcentualPorTipo();

        pieChart.getData().add(new PieChart.Data("Casa", porcentajes.get(0)));
        pieChart.getData().add(new PieChart.Data("Apartamento", porcentajes.get(1)));
        pieChart.getData().add(new PieChart.Data("Hotel", porcentajes.get(2)));

        panelEstadisticas.getChildren().add(pieChart);
    }

}
