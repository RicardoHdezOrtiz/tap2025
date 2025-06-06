/*package com.example.tap2025.views;

import com.example.tap2025.modelos.Conexion;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.image.WritableImage;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportesPDF {

    public static void generarReporteCompleto(String rutaArchivo,
                                              PieChart graficaProductos,
                                              LineChart<String, Number> graficaVentasDia,
                                              BarChart<String, Number> graficaEmpleadosVentas) {
        Document documento = new Document();

        try {
            // Initialize PDF
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // Title
            Paragraph titulo = new Paragraph("Reporte Completo de Ventas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24));
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            documento.add(titulo);

            // Ensure database connection
            if (Conexion.connection == null || Conexion.connection.isClosed()) {
                Conexion.createConnection();
            }

            // Section 1: Productos más vendidos
            Paragraph subTitulo1 = new Paragraph("Productos más vendidos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            subTitulo1.setSpacingAfter(10);
            documento.add(subTitulo1);

            PdfPTable tablaProductos = new PdfPTable(2);
            tablaProductos.setWidthPercentage(100);
            tablaProductos.addCell(new PdfPCell(new Phrase("Producto", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
            tablaProductos.addCell(new PdfPCell(new Phrase("Cantidad Vendida", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));

            // Adjusted SQL assuming an OrderDetails table (see notes below if schema differs)
            String sqlProductos = "SELECT p.nombreProducto, COUNT(od.idOrden) AS total " +
                    "FROM OrderDetails od " +
                    "JOIN Producto p ON od.idProducto = p.idProducto " +
                    "GROUP BY p.nombreProducto " +
                    "ORDER BY total DESC " +
                    "LIMIT 10";

            try (Statement stmt = Conexion.connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlProductos)) {
                boolean tieneDatos = false;
                while (rs.next()) {
                    tablaProductos.addCell(rs.getString("nombreProducto"));
                    tablaProductos.addCell(String.valueOf(rs.getInt("total")));
                    tieneDatos = true;
                }
                if (!tieneDatos) {
                    PdfPCell celdaVacia = new PdfPCell(new Phrase("No hay ventas registradas"));
                    celdaVacia.setColspan(2);
                    tablaProductos.addCell(celdaVacia);
                }
            } catch (SQLException e) {
                PdfPCell celdaError = new PdfPCell(new Phrase("Error al obtener datos: " + e.getMessage()));
                celdaError.setColspan(2);
                tablaProductos.addCell(celdaError);
                System.err.println("SQL Error in Productos: " + e.getMessage());
            }
            documento.add(tablaProductos);
            documento.add(new Paragraph(" "));

            // Embed PieChart image
            try {
                WritableImage imagenProductos = graficaProductos.snapshot(null, null);
                Image imgProductos = Image.getInstance(SwingFXUtils.fromFXImage(imagenProductos, null), null);
                imgProductos.scaleToFit(500, 300);
                imgProductos.setAlignment(Element.ALIGN_CENTER);
                documento.add(imgProductos);
            } catch (Exception e) {
                documento.add(new Paragraph("Error al incluir gráfica de productos: " + e.getMessage()));
                System.err.println("Chart Error in Productos: " + e.getMessage());
            }
            documento.add(new Paragraph(" "));

            // Section 2: Ventas por día
            Paragraph subTitulo2 = new Paragraph("Ventas por día", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            subTitulo2.setSpacingAfter(10);
            documento.add(subTitulo2);

            PdfPTable tablaVentasDia = new PdfPTable(2);
            tablaVentasDia.setWidthPercentage(100);
            tablaVentasDia.addCell(new PdfPCell(new Phrase("Día", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
            tablaVentasDia.addCell(new PdfPCell(new Phrase("Total Ventas", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));

            String sqlVentasDia = "SELECT DATE_FORMAT(fecha, '%Y-%m-%d') AS fecha, SUM(total) AS total_ventas " +
                    "FROM Orden " +
                    "WHERE fecha >= DATE_SUB(NOW(), INTERVAL 7 DAY) " +
                    "GROUP BY DATE(fecha) " +
                    "ORDER BY fecha DESC";

            try (Statement stmt = Conexion.connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlVentasDia)) {
                boolean tieneDatos = false;
                while (rs.next()) {
                    tablaVentasDia.addCell(rs.getString("fecha"));
                    tablaVentasDia.addCell(String.format("%.2f", rs.getFloat("total_ventas")));
                    tieneDatos = true;
                }
                if (!tieneDatos) {
                    PdfPCell celdaVacia = new PdfPCell(new Phrase("No hay ventas registradas"));
                    celdaVacia.setColspan(2);
                    tablaVentasDia.addCell(celdaVacia);
                }
            } catch (SQLException e) {
                PdfPCell celdaError = new PdfPCell(new Phrase("Error al obtener datos: " + e.getMessage()));
                celdaError.setColspan(2);
                tablaVentasDia.addCell(celdaError);
                System.err.println("SQL Error in VentasDia: " + e.getMessage());
            }
            documento.add(tablaVentasDia);
            documento.add(new Paragraph(" "));

            // Embed LineChart image
            try {
                WritableImage imagenVentasDia = graficaVentasDia.snapshot(null, null);
                Image imgVentasDia = Image.getInstance(SwingFXUtils.fromFXImage(imagenVentasDia, null), null);
                imgVentasDia.scaleToFit(500, 300);
                imgVentasDia.setAlignment(Element.ALIGN_CENTER);
                documento.add(imgVentasDia);
            } catch (Exception e) {
                documento.add(new Paragraph("Error al incluir gráfica de ventas por día: " + e.getMessage()));
                System.err.println("Chart Error in VentasDia: " + e.getMessage());
            }
            documento.add(new Paragraph(" "));

            // Section 3: Empleados con más ventas
            Paragraph subTitulo3 = new Paragraph("Empleados con más ventas por día", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            subTitulo3.setSpacingAfter(10);
            documento.add(subTitulo3);

            PdfPTable tablaEmpleados = new PdfPTable(3);
            tablaEmpleados.setWidthPercentage(100);
            tablaEmpleados.addCell(new PdfPCell(new Phrase("Día", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
            tablaEmpleados.addCell(new PdfPCell(new Phrase("Empleado", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
            tablaEmpleados.addCell(new PdfPCell(new Phrase("Ventas", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));

            String sqlEmpleados = "SELECT DAYNAME(o.fecha) AS dia_semana, " +
                    "CONCAT(e.nombres, ' ', e.apellido1) AS empleado, " +
                    "COUNT(o.idOrden) AS ventas " +
                    "FROM Orden o " +
                    "JOIN empleados e ON o.idEmpleado = e.idEmp " +
                    "WHERE o.fecha >= DATE_SUB(NOW(), INTERVAL 1 MONTH) " +
                    "GROUP BY DAYNAME(o.fecha), e.idEmp " +
                    "ORDER BY dia_semana, ventas DESC";

            try (Statement stmt = Conexion.connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlEmpleados)) {
                boolean tieneDatos = false;
                while (rs.next()) {
                    tablaEmpleados.addCell(rs.getString("dia_semana"));
                    tablaEmpleados.addCell(rs.getString("empleado"));
                    tablaEmpleados.addCell(String.valueOf(rs.getInt("ventas")));
                    tieneDatos = true;
                }
                if (!tieneDatos) {
                    PdfPCell celdaVacia = new PdfPCell(new Phrase("No hay ventas registradas"));
                    celdaVacia.setColspan(3);
                    tablaEmpleados.addCell(celdaVacia);
                }
            } catch (SQLException e) {
                PdfPCell celdaError = new PdfPCell(new Phrase("Error al obtener datos: " + e.getMessage()));
                celdaError.setColspan(3);
                tablaEmpleados.addCell(celdaError);
                System.err.println("SQL Error in Empleados: " + e.getMessage());
            }
            documento.add(tablaEmpleados);
            documento.add(new Paragraph(" "));

            // Embed BarChart image
            try {
                WritableImage imagenEmpleados = graficaEmpleadosVentas.snapshot(null, null);
                Image imgEmpleados = Image.getInstance(SwingFXUtils.fromFXImage(imagenEmpleados, null), null);
                imgEmpleados.scaleToFit(500, 300);
                imgEmpleados.setAlignment(Element.ALIGN_CENTER);
                documento.add(imgEmpleados);
            } catch (Exception e) {
                documento.add(new Paragraph("Error al incluir gráfica de empleados: " + e.getMessage()));
                System.err.println("Chart Error in Empleados: " + e.getMessage());
            }

            documento.close();
            System.out.println("Reporte completo generado exitosamente en: " + rutaArchivo);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al generar el reporte PDF: " + e.getMessage());
        }
    }
}*/