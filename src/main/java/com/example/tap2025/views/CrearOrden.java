package com.example.tap2025.views;

import com.example.tap2025.modelos.ClientesDAO;
import com.example.tap2025.modelos.EmpleadoDAO;
import com.example.tap2025.modelos.MesasDAO;
import com.example.tap2025.modelos.OrdenDAO;
import com.example.tap2025.modelos.ProductoDAO;
import com.example.tap2025.utils.ReporteGraficas;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrearOrden {

    private VBox vBox;
    private MesasDAO mesaSeleccionada;
    private ProductoDAO productoSeleccionado;
    private EmpleadoDAO meseroSeleccionado;
    private ClientesDAO clienteSeleccionado;
    private int pasoActual = 1;
    private Label lblInstruccion;

    public CrearOrden() {
        CrearUI();
    }

    private void CrearUI() {
        vBox = new VBox(10);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);

        lblInstruccion = new Label("Paso 1: Seleccionar Mesa");
        lblInstruccion.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        vBox.getChildren().add(lblInstruccion);
        mostrarPaso1();

        // Botón para abrir reportes gráficos
        Button btnAbrirReportes = new Button("Mostrar Reportes Gráficos");
        btnAbrirReportes.setOnAction(e -> {
            // Crear instancia de ReporteGraficas y mostrar ventana
            ReporteGraficas reporte = new ReporteGraficas();
            Stage stage = new Stage();  // Crear una nueva ventana (Stage)
            reporte.mostrar(stage);
        });

        vBox.getChildren().add(btnAbrirReportes);
    }

    // Paso 1: Seleccionar Mesa
    private void mostrarPaso1() {
        vBox.getChildren().clear();
        vBox.getChildren().add(lblInstruccion);
        lblInstruccion.setText("Paso 1: Seleccionar Mesa");

        HBox hBoxMesas = new HBox(10);
        hBoxMesas.setAlignment(Pos.CENTER);

        MesasDAO mesasDAO = new MesasDAO();
        for (MesasDAO mesa : mesasDAO.SELECT()) {
            Button btnMesa = new Button("Mesa " + mesa.getNoMesa());
            btnMesa.setOnAction(e -> {
                mesaSeleccionada = mesa;
                pasoActual = 2;
                mostrarPaso2();
            });
            hBoxMesas.getChildren().add(btnMesa);
        }

        vBox.getChildren().add(hBoxMesas);
    }

    private void mostrarPaso2() {
        vBox.getChildren().clear();
        vBox.getChildren().add(lblInstruccion);
        lblInstruccion.setText("Paso 2: Seleccionar Producto");

        HBox hBoxProductos = new HBox(10);
        hBoxProductos.setAlignment(Pos.CENTER);

        ProductoDAO productoDAO = new ProductoDAO();
        for (ProductoDAO producto : productoDAO.SELECT()) {
            VBox vBoxProducto = new VBox(5);
            vBoxProducto.setAlignment(Pos.CENTER);

            ImageView imageView = new ImageView();
            try {
                Image img = new Image(getClass().getResourceAsStream(producto.getImagen()), 80, 80, true, true);
                imageView.setImage(img);
            } catch (Exception e) {
                System.out.println("No se encontró imagen: " + producto.getImagen());
            }

            Button btnProducto = new Button(producto.getNombreProducto());
            btnProducto.setOnAction(e -> {
                productoSeleccionado = producto;
                pasoActual = 3;
                mostrarPaso3();
            });

            vBoxProducto.getChildren().addAll(imageView, btnProducto);
            hBoxProductos.getChildren().add(vBoxProducto);
        }

        Button btnAtras = new Button("Atrás");
        btnAtras.setOnAction(e -> {
            pasoActual = 1;
            mostrarPaso1();
        });

        vBox.getChildren().addAll(hBoxProductos, btnAtras);
    }

    private void mostrarPaso3() {
        vBox.getChildren().clear();
        vBox.getChildren().add(lblInstruccion);
        lblInstruccion.setText("Paso 3: Seleccionar Mesero");

        HBox hBoxMeseros = new HBox(10);
        hBoxMeseros.setAlignment(Pos.CENTER);

        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        for (EmpleadoDAO empleado : empleadoDAO.SELECT()) {
            Button btnMesero = new Button(empleado.getNombres() + " " + empleado.getApellido1());
            btnMesero.setOnAction(e -> {
                meseroSeleccionado = empleado;
                pasoActual = 4;
                mostrarPaso4();
            });
            hBoxMeseros.getChildren().add(btnMesero);
        }

        Button btnAtras = new Button("Atrás");
        btnAtras.setOnAction(e -> {
            pasoActual = 2;
            mostrarPaso2();
        });

        vBox.getChildren().addAll(hBoxMeseros, btnAtras);
    }

    private void mostrarPaso4() {
        vBox.getChildren().clear();
        vBox.getChildren().add(lblInstruccion);
        lblInstruccion.setText("Paso 4: Seleccionar Cliente");

        HBox hBoxClientes = new HBox(10);
        hBoxClientes.setAlignment(Pos.CENTER);

        ClientesDAO clientesDAO = new ClientesDAO();
        for (ClientesDAO cliente : clientesDAO.SELECT()) {
            Button btnCliente = new Button(cliente.getNomCte() + " " + cliente.getApellidoPaterno());
            btnCliente.setOnAction(e -> {
                clienteSeleccionado = cliente;
                pasoActual = 5;
                mostrarPaso5();
            });
            hBoxClientes.getChildren().add(btnCliente);
        }

        Button btnAtras = new Button("Atrás");
        btnAtras.setOnAction(e -> {
            pasoActual = 3;
            mostrarPaso3();
        });

        vBox.getChildren().addAll(hBoxClientes, btnAtras);
    }

    private void mostrarPaso5() {
        vBox.getChildren().clear();
        vBox.getChildren().add(lblInstruccion);
        lblInstruccion.setText("Paso 5: Confirmar Orden");

        VBox resumen = new VBox(10);
        resumen.setAlignment(Pos.CENTER_LEFT);
        resumen.getChildren().addAll(
                new Label("Mesa: " + mesaSeleccionada.getNoMesa()),
                new Label("Producto: " + productoSeleccionado.getNombreProducto()),
                new Label("Mesero: " + meseroSeleccionado.getNombres() + " " + meseroSeleccionado.getApellido1()),
                new Label("Cliente: " + clienteSeleccionado.getNomCte() + " " + clienteSeleccionado.getApellidoPaterno()),
                new Label("Total: $" + String.format("%.2f", productoSeleccionado.getPrecio()))
        );

        Button btnConfirmar = new Button("Confirmar y Guardar PDF");
        btnConfirmar.setOnAction(e -> guardarOrdenYGenerarTXT());

        Button btnAtras = new Button("Atrás");
        btnAtras.setOnAction(e -> {
            pasoActual = 4;
            mostrarPaso4();
        });

        vBox.getChildren().addAll(resumen, btnConfirmar, btnAtras);
    }

    private void guardarOrdenYGenerarTXT() {
        try {
            OrdenDAO orden = new OrdenDAO();
            orden.setIdCliente(clienteSeleccionado.getIdCte());
            orden.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            orden.setTotal((float) productoSeleccionado.getPrecio());
            orden.setNoMesa(mesaSeleccionada.getNoMesa());
            orden.setIdEmpleado(meseroSeleccionado.getIdEmp());
            orden.INSERT();

            String homeDir = System.getProperty("user.home");
            String filePath = Paths.get(homeDir, "Downloads", "Orden_" + orden.getIdOrden() + ".txt").toString();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath)))) {
                writer.write("Detalles de la Orden");
                writer.newLine();
                writer.write("Mesa: " + mesaSeleccionada.getNoMesa());
                writer.newLine();
                writer.write("Producto: " + productoSeleccionado.getNombreProducto());
                writer.newLine();
                writer.write("Mesero: " + meseroSeleccionado.getNombres() + " " + meseroSeleccionado.getApellido1());
                writer.newLine();
                writer.write("Cliente: " + clienteSeleccionado.getNomCte() + " " + clienteSeleccionado.getApellidoPaterno());
                writer.newLine();
                writer.write("Total: $" + String.format("%.2f", productoSeleccionado.getPrecio()));
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Orden Guardada");
            alert.setHeaderText(null);
            alert.setContentText("Orden guardada correctamente y archivo generado en Descargas.");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo guardar la orden");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Método para obtener el VBox para mostrarlo en el escenario principal
    public VBox getVista() {
        return vBox;
    }
}