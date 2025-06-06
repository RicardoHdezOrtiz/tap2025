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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CrearOrden {

    private VBox vBox;
    private MesasDAO mesaSeleccionada;
    private List<ProductoDAO> productosSeleccionados = new ArrayList<>();
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

        Button btnAbrirReportes = new Button("Mostrar Reportes Gráficos");
        btnAbrirReportes.setOnAction(e -> {
            ReporteGraficas reporte = new ReporteGraficas();
            Stage stage = new Stage();
            reporte.mostrar(stage);
        });

        vBox.getChildren().add(btnAbrirReportes);
    }

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
        lblInstruccion.setText("Paso 2: Seleccionar Productos");

        ProductoDAO productoDAO = new ProductoDAO();
        List<ProductoDAO> productos = productoDAO.SELECT();

        // Filtrar productos por categoría (ajusta los ids según tu modelo)
        List<ProductoDAO> alimentos = productos.stream()
                .filter(p -> p.getIdCategoria() == 1)
                .collect(Collectors.toList());
        List<ProductoDAO> bebidas = productos.stream()
                .filter(p -> p.getIdCategoria() == 2)
                .collect(Collectors.toList());
        List<ProductoDAO> postres = productos.stream()
                .filter(p -> p.getIdCategoria() == 3)
                .collect(Collectors.toList());

        VBox contenedorCategorias = new VBox(15);
        contenedorCategorias.setAlignment(Pos.CENTER);

        java.util.function.BiFunction<String, List<ProductoDAO>, VBox> crearCategoriaBox = (titulo, listaProductos) -> {
            VBox categoriaBox = new VBox(10);
            Label lblTitulo = new Label(titulo);
            lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            categoriaBox.getChildren().add(lblTitulo);

            HBox hBoxProductos = new HBox(10);
            hBoxProductos.setAlignment(Pos.CENTER);

            for (ProductoDAO producto : listaProductos) {
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
                    productosSeleccionados.add(producto);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Producto agregado");
                    alert.setHeaderText(null);
                    alert.setContentText("Producto agregado: " + producto.getNombreProducto());
                    alert.showAndWait();
                });

                vBoxProducto.getChildren().addAll(imageView, btnProducto);
                hBoxProductos.getChildren().add(vBoxProducto);
            }

            categoriaBox.getChildren().add(hBoxProductos);
            return categoriaBox;
        };

        contenedorCategorias.getChildren().addAll(
                crearCategoriaBox.apply("Alimentos_________________________________________________________________________________________", alimentos),
                crearCategoriaBox.apply("Bebidas__________________________________________________________________________________________", bebidas),
                crearCategoriaBox.apply("Postres____________________________________________________________________________________________", postres)
        );

        Button btnContinuar = new Button("Continuar");
        btnContinuar.setOnAction(e -> {
            if (productosSeleccionados.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Debe seleccionar al menos un producto.");
                alert.showAndWait();
            } else {
                pasoActual = 3;
                mostrarPaso3();
            }
        });

        Button btnAtras = new Button("Atrás");
        btnAtras.setOnAction(e -> {
            pasoActual = 1;
            mostrarPaso1();
        });

        vBox.getChildren().addAll(contenedorCategorias, btnContinuar, btnAtras);
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

        resumen.getChildren().add(new Label("Mesa: " + mesaSeleccionada.getNoMesa()));

        resumen.getChildren().add(new Label("Productos seleccionados:"));
        double total = 0.0;
        for (ProductoDAO producto : productosSeleccionados) {
            resumen.getChildren().add(new Label("- " + producto.getNombreProducto() + " ($" + String.format("%.2f", producto.getPrecio()) + ")"));
            total += producto.getPrecio();
        }

        resumen.getChildren().add(new Label("Mesero: " + meseroSeleccionado.getNombres() + " " + meseroSeleccionado.getApellido1()));
        resumen.getChildren().add(new Label("Cliente: " + clienteSeleccionado.getNomCte() + " " + clienteSeleccionado.getApellidoPaterno()));
        resumen.getChildren().add(new Label("Total: $" + String.format("%.2f", total)));

        Button btnConfirmar = new Button("Confirmar y Guardar PDF");
        double finalTotal = total;
        btnConfirmar.setOnAction(e -> guardarOrdenYGenerarTXT(finalTotal));

        Button btnAtras = new Button("Atrás");
        btnAtras.setOnAction(e -> {
            pasoActual = 4;
            mostrarPaso4();
        });

        vBox.getChildren().addAll(resumen, btnConfirmar, btnAtras);
    }

    private void guardarOrdenYGenerarTXT(double total) {
        try {
            OrdenDAO orden = new OrdenDAO();
            orden.setIdCliente(clienteSeleccionado.getIdCte());
            orden.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            orden.setTotal((float) total);
            orden.setNoMesa(mesaSeleccionada.getNoMesa());
            orden.setIdEmpleado(meseroSeleccionado.getIdEmp());
            orden.INSERT();

            String homeDir = System.getProperty("user.home");
            String filePath = Paths.get(homeDir, "Downloads", "Orden" + ".txt").toString();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write("Detalles de la Orden");
                writer.newLine();
                writer.write("Mesa: " + mesaSeleccionada.getNoMesa());
                writer.newLine();
                writer.write("Productos:");
                writer.newLine();
                for (ProductoDAO producto : productosSeleccionados) {
                    writer.write("- " + producto.getNombreProducto() + " ($" + String.format("%.2f", producto.getPrecio()) + ")");
                    writer.newLine();
                }
                writer.write("Mesero: " + meseroSeleccionado.getNombres() + " " + meseroSeleccionado.getApellido1());
                writer.newLine();
                writer.write("Cliente: " + clienteSeleccionado.getNomCte() + " " + clienteSeleccionado.getApellidoPaterno());
                writer.newLine();
                writer.write("Total: $" + String.format("%.2f", total));
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Orden Guardada");
            alert.setHeaderText(null);
            alert.setContentText("Orden guardada correctamente y archivo generado en Descargas.");
            alert.showAndWait();

            productosSeleccionados.clear();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo guardar la orden");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public VBox getVista() {
        return vBox;
    }
}