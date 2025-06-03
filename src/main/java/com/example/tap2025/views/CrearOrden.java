/*package com.example.tap2025.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import com.example.tap2025.models.Mesa;
import com.example.tap2025.models.Producto;
import com.example.tap2025.models.Empleado;
import com.example.tap2025.models.Cliente;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CrearOrden {

    private VBox vista;

    public CrearOrden() {
        vista = new VBox(20);
        vista.setPadding(new Insets(20));
        vista.setAlignment(Pos.CENTER);
        mostrarPaso1();
    }

    public Pane getVista() {
        return vista;
    }

    // Paso 1: Seleccionar mesa
    private void mostrarPaso1() {
        vista.getChildren().clear();
        Label titulo = new Label("Paso 1: Escoge una mesa");

        List<Mesa> mesas = obtenerMesasDesdeBD();
        for (Mesa mesa : mesas) {
            Button btnMesa = new Button("Mesa " + mesa.getNumero());
            btnMesa.setOnAction(e -> mostrarPaso2(mesa));
            vista.getChildren().add(btnMesa);
        }

        vista.getChildren().add(0, titulo);
    }

    // Paso 2: Seleccionar producto
    private void mostrarPaso2(Mesa mesaSeleccionada) {
        vista.getChildren().clear();
        Label titulo = new Label("Paso 2: Escoge un producto");

        List<Producto> productos = obtenerProductosDesdeBD();
        for (Producto producto : productos) {
            Button btnProducto = new Button(producto.getNombre());
            btnProducto.setOnAction(e -> mostrarPaso3(mesaSeleccionada, producto));
            vista.getChildren().add(btnProducto);
        }

        vista.getChildren().add(0, titulo);
    }

    // Paso 3: Seleccionar mesero
    private void mostrarPaso3(Mesa mesa, Producto producto) {
        vista.getChildren().clear();
        Label titulo = new Label("Paso 3: Escoge un mesero");

        List<Empleado> meseros = obtenerMeserosDesdeBD();
        for (Empleado mesero : meseros) {
            Button btnMesero = new Button(mesero.getNombre());
            btnMesero.setOnAction(e -> mostrarPaso4(mesa, producto, mesero));
            vista.getChildren().add(btnMesero);
        }

        vista.getChildren().add(0, titulo);
    }

    // Paso 4: Seleccionar cliente
    private void mostrarPaso4(Mesa mesa, Producto producto, Empleado mesero) {
        vista.getChildren().clear();
        Label titulo = new Label("Paso 4: Escoge un cliente");

        List<Cliente> clientes = obtenerClientesDesdeBD();
        for (Cliente cliente : clientes) {
            Button btnCliente = new Button(cliente.getNombre());
            btnCliente.setOnAction(e -> mostrarPaso5(mesa, producto, mesero, cliente));
            vista.getChildren().add(btnCliente);
        }

        vista.getChildren().add(0, titulo);
    }

    // Paso 5: Confirmar y guardar orden
    private void mostrarPaso5(Mesa mesa, Producto producto, Empleado mesero, Cliente cliente) {
        vista.getChildren().clear();
        Label titulo = new Label("Paso 5: Confirmar orden");

        Label resumen = new Label("Resumen del ticket:\n" +
                "Mesa: " + mesa.getNumero() + "\n" +
                "Producto: " + producto.getNombre() + "\n" +
                "Mesero: " + mesero.getNombre() + "\n" +
                "Cliente: " + cliente.getNombre());

        Button btnGuardarPDF = new Button("Guardar ticket como PDF");
        btnGuardarPDF.setOnAction(e -> {
            boolean exito = guardarOrdenPDF(mesa, producto, mesero, cliente);
            vista.getChildren().clear();
            if (exito) {
                vista.getChildren().add(new Label("¡Orden registrada y PDF guardado en la carpeta Descargas!"));
            } else {
                vista.getChildren().add(new Label("Error al guardar el PDF. Intenta de nuevo."));
            }
        });

        vista.getChildren().addAll(titulo, resumen, btnGuardarPDF);
    }

    // Método que genera y guarda el PDF
    private boolean guardarOrdenPDF(Mesa mesa, Producto producto, Empleado mesero, Cliente cliente) {
        Document documento = new Document();
        try {
            String rutaDescargas = Paths.get(System.getProperty("user.home"), "Downloads", "ticket_orden.pdf").toString();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaDescargas));
            documento.open();

            documento.add(new Paragraph("***** TICKET DE ORDEN *****\n\n"));
            documento.add(new Paragraph("Mesa: " + mesa.getNumero()));
            documento.add(new Paragraph("Producto: " + producto.getNombre()));
            documento.add(new Paragraph("Mesero: " + mesero.getNombre()));
            documento.add(new Paragraph("Cliente: " + cliente.getNombre()));
            documento.add(new Paragraph("\nGracias por su preferencia."));

            return true;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (documento.isOpen()) {
                documento.close();
            }
        }
    }

    // Simulaciones para pruebas
    private List<Mesa> obtenerMesasDesdeBD() {
        return List.of(new Mesa(1), new Mesa(2), new Mesa(3));
    }

    private List<Producto> obtenerProductosDesdeBD() {
        return List.of(new Producto("Taco"), new Producto("Burrito"));
    }

    private List<Empleado> obtenerMeserosDesdeBD() {
        return List.of(new Empleado("Pedro"), new Empleado("María"));
    }

    private List<Cliente> obtenerClientesDesdeBD() {
        return List.of(new Cliente("Juan"), new Cliente("Laura"));
    }
}*/