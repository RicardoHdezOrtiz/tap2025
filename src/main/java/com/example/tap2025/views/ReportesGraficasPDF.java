/*package com.example.tap2025.views;

import com.example.tap2025.modelos.Conexion;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReportesGraficasPDF {

    public void mostrar(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        PieChart graficaProductos = generarGraficaProductosVendidos();
        LineChart<String, Number> graficaVentasDia = generarGraficaVentasPorDia();
        BarChart<String, Number> graficaEmpleadosVentas = generarGraficaEmpleadosVentas();

        Button btnExportarPDF = new Button("Reporte Completo a PDF");
        btnExportarPDF.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 10;");

        btnExportarPDF.setOnAction(e -> {
            TextInputDialog dialogo = new TextInputDialog("reporte_completo");
            dialogo.setTitle("Nombre del Archivo");
            dialogo.setHeaderText("Ingrese el nombre del archivo PDF:");
            dialogo.setContentText("Nombre:");

            dialogo.showAndWait().ifPresent(nombre -> {
                nombre = nombre.trim().replaceAll("[\\\\/:*?\"<>|]", "_");
                if (!nombre.toLowerCase().endsWith(".pdf")) {
                    nombre += ".pdf";
                }

                String ruta = "C:/Users/100032624/Documents/Topicos Avanzados/REPORTE GRAFICAS PROD/" + nombre;

                generarReporteCompleto(ruta, graficaVentasDia, graficaEmpleadosVentas);

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("PDF Generado");
                alerta.setHeaderText(null);
                alerta.setContentText("¡El reporte PDF se generó correctamente en:\n" + ruta + "!");
                alerta.showAndWait();
            });
        });

        root.getChildren().addAll(graficaProductos, graficaVentasDia, graficaEmpleadosVentas, btnExportarPDF);

        ScrollPane scroll = new ScrollPane(root);
        Scene scene = new Scene(scroll, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Reportes y Gráficas");
        primaryStage.show();
    }

    public PieChart generarGraficaProductosVendidos() {
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();

        try {
            if (Conexion.connection == null || Conexion.connection.isClosed()) {
                Conexion.createConnection();
            }

            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT producto, SUM(cantidad) AS total FROM pedidos GROUP BY producto ORDER BY total DESC LIMIT 5");

            while (rs.next()) {
                datos.add(new PieChart.Data(rs.getString("producto"), rs.getInt("total")));
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PieChart pieChart = new PieChart(datos);
        pieChart.setTitle("Top 5 productos más vendidos");
        return pieChart;
    }

    public LineChart<String, Number> generarGraficaVentasPorDia() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Ventas por día");

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ventas");

        try {
            if (Conexion.connection == null || Conexion.connection.isClosed()) {
                Conexion.createConnection();
            }

            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DAYNAME(fecha) as dia, SUM(cantidad) as total FROM pedidos GROUP BY dia");

            while (rs.next()) {
                serie.getData().add(new XYChart.Data<>(rs.getString("dia"), rs.getInt("total")));
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        chart.getData().add(serie);
        return chart;
    }

    public BarChart<String, Number> generarGraficaEmpleadosVentas() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Ventas por empleado y día");

        Map<String, XYChart.Series<String, Number>> seriesMap = new HashMap<>();

        try {
            if (Conexion.connection == null || Conexion.connection.isClosed()) {
                Conexion.createConnection();
            }

            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DAYNAME(fecha) as dia, empleado, SUM(cantidad) as total FROM pedidos GROUP BY dia, empleado");

            while (rs.next()) {
                String dia = rs.getString("dia");
                String empleado = rs.getString("empleado");
                int total = rs.getInt("total");

                seriesMap.putIfAbsent(dia, new XYChart.Series<>());
                XYChart.Series<String, Number> serie = seriesMap.get(dia);
                serie.setName(dia);
                serie.getData().add(new XYChart.Data<>(empleado, total));
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        chart.getData().addAll(seriesMap.values());
        return chart;
    }

    public static void generarReporteCompleto(String rutaArchivo,
                                              LineChart<String, Number> graficaVentasDia,
                                              BarChart<String, Number> graficaEmpleadosVentas) {
        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            Paragraph titulo = new Paragraph("Reporte Completo de Ventas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24));
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            documento.add(titulo);

            Paragraph subTitulo1 = new Paragraph("Productos más vendidos por día de la semana", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            subTitulo1.setSpacingAfter(10);
            documento.add(subTitulo1);

            if (Conexion.connection == null || Conexion.connection.isClosed()) {
                Conexion.createConnection();
            }

            for (DayOfWeek day : DayOfWeek.values()) {
                String nombreDia = day.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                int numeroDia = day.getValue() % 7 + 1;

                Paragraph diaTitulo = new Paragraph(nombreDia.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
                documento.add(diaTitulo);

                PdfPTable tabla = new PdfPTable(2);
                tabla.setWidthPercentage(100);
                tabla.addCell(new PdfPCell(new Phrase("Producto")));
                tabla.addCell(new PdfPCell(new Phrase("Cantidad Vendida")));

                String sql = "SELECT producto, SUM(cantidad) AS total FROM pedidos WHERE YEARWEEK(fecha, 1) = YEARWEEK(NOW(), 1) AND DAYOFWEEK(fecha) = ? GROUP BY producto ORDER BY total DESC LIMIT 10";
                PreparedStatement pstmt = Conexion.connection.prepareStatement(sql);
                pstmt.setInt(1, numeroDia);
                ResultSet rs = pstmt.executeQuery();

                boolean tieneDatos = false;
                while (rs.next()) {
                    tabla.addCell(rs.getString("producto"));
                    tabla.addCell(String.valueOf(rs.getInt("total")));
                    tieneDatos = true;
                }

                if (!tieneDatos) {
                    PdfPCell celdaVacia = new PdfPCell(new Phrase("No hay ventas registradas"));
                    celdaVacia.setColspan(2);
                    tabla.addCell(celdaVacia);
                }

                documento.add(tabla);
                documento.add(new Paragraph(" "));

                rs.close();
                pstmt.close();
            }

            Paragraph subTitulo2 = new Paragraph("Ventas por día de la semana", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            documento.add(subTitulo2);

            WritableImage imagenVentasDia = graficaVentasDia.snapshot(null, null);
            Image imgVentasDia = Image.getInstance(SwingFXUtils.fromFXImage(imagenVentasDia, null), null);
            imgVentasDia.scaleToFit(500, 300);
            imgVentasDia.setAlignment(Element.ALIGN_CENTER);
            documento.add(imgVentasDia);
            documento.add(new Paragraph(" "));

            Paragraph subTitulo3 = new Paragraph("Empleados con más ventas por día", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            documento.add(subTitulo3);

            WritableImage imagenEmpleados = graficaEmpleadosVentas.snapshot(null, null);
            Image imgEmpleados = Image.getInstance(SwingFXUtils.fromFXImage(imagenEmpleados, null), null);
            imgEmpleados.scaleToFit(500, 300);
            imgEmpleados.setAlignment(Element.ALIGN_CENTER);
            documento.add(imgEmpleados);

            PdfPTable tablaEmpleados = new PdfPTable(3);
            tablaEmpleados.setWidthPercentage(100);
            tablaEmpleados.addCell("Día");
            tablaEmpleados.addCell("Empleado");
            tablaEmpleados.addCell("Ventas");

            for (XYChart.Series<String, Number> serie : graficaEmpleadosVentas.getData()) {
                String dia = serie.getName();
                for (XYChart.Data<String, Number> dato : serie.getData()) {
                    tablaEmpleados.addCell(dia);
                    tablaEmpleados.addCell(dato.getXValue());
                    tablaEmpleados.addCell(dato.getYValue().toString());
                }
            }

            documento.add(tablaEmpleados);
            documento.close();
            System.out.println("Reporte completo generado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/