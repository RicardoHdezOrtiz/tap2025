package com.example.tap2025.utils;

import com.example.tap2025.modelos.Conexion;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class ReportesPDF {
    public static void generarReporteCompleto(String rutaArchivo,
                                              LineChart<String, Number> graficaVentasDia,
                                              BarChart<String, Number> graficaEmpleadosVentas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            // Título del reporte
            writer.write("Reporte Completo de Ventas\n");
            writer.write("==================================\n\n");

            // Sección 1: Productos más vendidos por día
            writer.write("Productos más vendidos por día de la semana\n");
            writer.write("------------------------------------------\n");

            if (Conexion.connection == null || Conexion.connection.isClosed()) {
                Conexion.createConnection();
            }

            for (DayOfWeek day : DayOfWeek.values()) {
                String nombreDia = day.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                int numeroDia = day.getValue() % 7 + 1;

                writer.write(nombreDia.toUpperCase() + "\n");
                writer.write(String.format("%-30s %-10s\n", "Producto", "Cantidad Vendida"));
                writer.write("----------------------------------------\n");

                String sql = "SELECT p.nombreProducto, COUNT(o.idOrden) AS total " +
                        "FROM Orden o " +
                        "JOIN Producto p ON o.idCliente = p.idProducto " + // Adjust join as needed
                        "WHERE YEARWEEK(o.fecha, 1) = YEARWEEK(NOW(), 1) " +
                        "AND DAYOFWEEK(o.fecha) = ? " +
                        "GROUP BY p.nombreProducto " +
                        "ORDER BY total DESC " +
                        "LIMIT 10";

                PreparedStatement pstmt = Conexion.connection.prepareStatement(sql);
                pstmt.setInt(1, numeroDia);
                ResultSet rs = pstmt.executeQuery();

                boolean tieneDatos = false;
                while (rs.next()) {
                    writer.write(String.format("%-30s %-10d\n", rs.getString("nombreProducto"), rs.getInt("total")));
                    tieneDatos = true;
                }

                if (!tieneDatos) {
                    writer.write("No hay ventas registradas\n");
                }

                writer.write("\n");
                rs.close();
                pstmt.close();
            }

            // Sección 2: Ventas por día
            writer.write("Ventas por día de la semana\n");
            writer.write("------------------------------------------\n");
            writer.write(String.format("%-15s %-10s\n", "Fecha", "Total Ventas ($)"));
            writer.write("----------------------------------------\n");

            for (XYChart.Series<String, Number> serie : graficaVentasDia.getData()) {
                for (XYChart.Data<String, Number> dato : serie.getData()) {
                    writer.write(String.format("%-15s %-10.2f\n", dato.getXValue(), dato.getYValue().doubleValue()));
                }
            }

            writer.write("\n");

            // Sección 3: Empleados con más ventas
            writer.write("Empleados con más ventas por día de la semana\n");
            writer.write("------------------------------------------\n");
            writer.write(String.format("%-15s %-30s %-10s\n", "Día", "Empleado", "Ventas"));
            writer.write("----------------------------------------\n");

            for (XYChart.Series<String, Number> serie : graficaEmpleadosVentas.getData()) {
                String dia = serie.getName();
                for (XYChart.Data<String, Number> dato : serie.getData()) {
                    writer.write(String.format("%-15s %-30s %-10d\n", dia, dato.getXValue(), dato.getYValue().intValue()));
                }
            }

            System.out.println("Reporte completo generado exitosamente en: " + rutaArchivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}