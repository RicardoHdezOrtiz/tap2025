package com.example.tap2025.utils;

import com.example.tap2025.modelos.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ReporteGraficas {

    public void mostrar(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        // Gráficas
        PieChart graficaProductos = generarGraficaProductosVendidos();
        LineChart<String, Number> graficaVentasDia = generarGraficaVentasPorDia();
        BarChart<String, Number> graficaEmpleadosVentas = generarGraficaEmpleadosVentas();

        // Botón para exportar a "PDF" (texto con extensión .pdf)
        Button btnExportarTxt = new Button("Exportar Reporte a PDF");
        btnExportarTxt.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 10;");

        btnExportarTxt.setOnAction(e -> {
            TextInputDialog dialogo = new TextInputDialog("reporte_ventas");
            dialogo.setTitle("Nombre del Archivo");
            dialogo.setHeaderText("Ingrese el nombre del archivo PDF:");
            dialogo.setContentText("Nombre:");

            dialogo.showAndWait().ifPresent(nombre -> {
                // Limpieza y validación del nombre
                nombre = nombre.trim().replaceAll("[\\\\/:*?\"<>|]", "_");
                if (!nombre.toLowerCase().endsWith(".pdf")) {
                    nombre += ".pdf";
                }

                String ruta = System.getProperty("user.home") + "/Downloads/" + nombre;

                // Generar archivo de texto con extensión .pdf
                ReportesPDF.generarReporteCompleto(ruta, graficaVentasDia, graficaEmpleadosVentas);

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Reporte Generado");
                alerta.setHeaderText(null);
                alerta.setContentText("¡El reporte se generó correctamente en:\n" + ruta + "!");
                alerta.showAndWait();
            });
        });

        root.getChildren().addAll(graficaProductos, graficaVentasDia, graficaEmpleadosVentas, btnExportarTxt);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene escena = new Scene(scrollPane, 1000, 600);
        Stage ventanaGraficas = new Stage();
        ventanaGraficas.setTitle("Reportes Gráficos");
        ventanaGraficas.setScene(escena);
        ventanaGraficas.show();
    }

    private PieChart generarGraficaProductosVendidos() {
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();

        String sql = "SELECT p.nombreProducto, COUNT(o.idOrden) AS total_ventas " +
                "FROM Orden o " +
                "JOIN Producto p ON o.idCliente = p.idProducto " + // Adjust based on actual relationship
                "GROUP BY p.nombreProducto " +
                "ORDER BY total_ventas DESC " +
                "LIMIT 10";

        try {
            if (Conexion.connection == null || Conexion.connection.isClosed()) {
                Conexion.createConnection();
            }

            try (Statement stmt = Conexion.connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    datos.add(new PieChart.Data(rs.getString("nombreProducto"), rs.getInt("total_ventas")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        PieChart pieChart = new PieChart(datos);
        pieChart.setTitle("Top 10 Productos Más Vendidos");
        pieChart.setLegendVisible(true);

        return pieChart;
    }

    private LineChart<String, Number> generarGraficaVentasPorDia() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        xAxis.setLabel("Fecha");
        yAxis.setLabel("Total de Ventas ($)");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ventas Diarias");

        String sql = "SELECT DATE(fecha) AS fecha, SUM(total) AS total_ventas " +
                "FROM Orden " +
                "GROUP BY DATE(fecha) " +
                "ORDER BY fecha DESC " +
                "LIMIT 7";

        try {
            if (Conexion.connection == null || Conexion.connection.isClosed()) {
                Conexion.createConnection();
            }

            try (Statement stmt = Conexion.connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    series.getData().add(new XYChart.Data<>(rs.getString("fecha"), rs.getDouble("total_ventas")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lineChart.getData().add(series);
        lineChart.setTitle("Ventas por Día (Últimos 7 Días)");

        return lineChart;
    }

    private BarChart<String, Number> generarGraficaEmpleadosVentas() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        xAxis.setLabel("Empleado");
        yAxis.setLabel("Ventas Realizadas");

        String sql = "SELECT DAYNAME(o.fecha) AS dia_semana, " +
                "CONCAT(e.nombres, ' ', e.apellido1) AS empleado, " +
                "COUNT(o.idOrden) AS ventas " +
                "FROM Orden o " +
                "JOIN empleados e ON o.idEmpleado = e.idEmp " +
                "WHERE o.fecha >= DATE_SUB(NOW(), INTERVAL 1 MONTH) " +
                "GROUP BY dia_semana, empleado " +
                "ORDER BY dia_semana, ventas DESC";

        try {
            if (Conexion.connection == null || Conexion.connection.isClosed()) {
                Conexion.createConnection();
            }

            try (Statement stmt = Conexion.connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                Map<String, XYChart.Series<String, Number>> seriesPorDia = new HashMap<>();

                while (rs.next()) {
                    String diaSemana = rs.getString("dia_semana");
                    String empleado = rs.getString("empleado");
                    int ventas = rs.getInt("ventas");

                    XYChart.Series<String, Number> series = seriesPorDia.computeIfAbsent(
                            diaSemana, k -> {
                                XYChart.Series<String, Number> s = new XYChart.Series<>();
                                s.setName(diaSemana);
                                return s;
                            }
                    );

                    if (series.getData().size() < 3) {
                        series.getData().add(new XYChart.Data<>(empleado, ventas));
                    }
                }

                String[] ordenDias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
                for (String dia : ordenDias) {
                    if (seriesPorDia.containsKey(dia)) {
                        barChart.getData().add(seriesPorDia.get(dia));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.setTitle("Empleados con Más Ventas por Día de la Semana");
        barChart.setLegendVisible(false);

        return barChart;
    }
}