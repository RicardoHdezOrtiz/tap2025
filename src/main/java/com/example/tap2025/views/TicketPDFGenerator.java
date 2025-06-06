/*package com.example.tap2025.views;

import com.example.tap2025.modelos.ClientesDAO;
import com.example.tap2025.modelos.Conexion;
import com.example.tap2025.modelos.EmpleadoDAO;
import com.example.tap2025.modelos.OrdenDAO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketPDFGenerator {
    public static void generateTicketPDF(OrdenDAO orden) {
        if (Conexion.connection == null) {
            System.err.println("Error: No hay conexión a la base de datos.");
            return;
        }

        try {
            // Create output file
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = "ticket_orden_" + orden.getIdOrden() + "_" + timestamp + ".pdf";
            File file = new File(filePath);
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add restaurant header
            document.add(new Paragraph("Fondita Doña Lupe")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Ticket de Compra")
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n"));

            // Order details
            document.add(new Paragraph("Orden #" + orden.getIdOrden())
                    .setFontSize(12));
            document.add(new Paragraph("Fecha: " + orden.getFecha())
                    .setFontSize(12));

            // Fetch client details
            String clienteNombre = "Desconocido";
            try {
                PreparedStatement stmt = Conexion.connection.prepareStatement(
                        "SELECT nomCliente, apellidoPaterno, apellidoMaterno FROM clientes WHERE idCte = ?");
                stmt.setInt(1, orden.getIdCliente());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    clienteNombre = rs.getString("nomCliente") + " " +
                            rs.getString("apellidoPaterno") + " " +
                            rs.getString("apellidoMaterno");
                }
                rs.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            document.add(new Paragraph("Cliente: " + clienteNombre)
                    .setFontSize(12));

            // Fetch employee details
            String empleadoNombre = "Desconocido";
            try {
                PreparedStatement stmt = Conexion.connection.prepareStatement(
                        "SELECT nombres, apellido1, apellido2 FROM empleados WHERE idEmp = ?");
                stmt.setInt(1, orden.getIdEmpleado());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    empleadoNombre = rs.getString("nombres") + " " +
                            rs.getString("apellido1") + " " +
                            rs.getString("apellido2");
                }
                rs.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            document.add(new Paragraph("Empleado: " + empleadoNombre)
                    .setFontSize(12));

            // Table details
            document.add(new Paragraph("Mesa: " + orden.getNoMesa())
                    .setFontSize(12));

            // Products table
            document.add(new Paragraph("\nDetalles de la Compra")
                    .setFontSize(12)
                    .setBold());
            Table table = new Table(new float[]{3, 1, 1, 1});
            table.addHeaderCell(new Cell().add(new Paragraph("Producto")));
            table.addHeaderCell(new Cell().add(new Paragraph("Cantidad")));
            table.addHeaderCell(new Cell().add(new Paragraph("Precio Unit.")));
            table.addHeaderCell(new Cell().add(new Paragraph("Subtotal")));

            try {
                PreparedStatement stmt = Conexion.connection.prepareStatement(
                        "SELECT p.nombreProducto, do.cantidad, p.precio " +
                                "FROM detalle_orden do " +
                                "JOIN Producto p ON do.idProducto = p.idProducto " +
                                "WHERE do.idOrden = ?");
                stmt.setInt(1, orden.getIdOrden());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String producto = rs.getString("nombreProducto");
                    int cantidad = rs.getInt("cantidad");
                    double precio = rs.getDouble("precio");
                    double subtotal = cantidad * precio;
                    table.addCell(new Cell().add(new Paragraph(producto)));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(cantidad))));
                    table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", precio))));
                    table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", subtotal))));
                }
                rs.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
                table.addCell(new Cell().add(new Paragraph("No se encontraron productos")));
                table.addCell(new Cell().add(new Paragraph("")));
                table.addCell(new Cell().add(new Paragraph("")));
                table.addCell(new Cell().add(new Paragraph("")));
            }
            document.add(table);

            // Total
            document.add(new Paragraph("\nTotal: $" + String.format("%.2f", orden.getTotal()))
                    .setFontSize(12)
                    .setBold());

            // Footer
            document.add(new Paragraph("\nGracias por su compra!")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();
            System.out.println("PDF generado: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/