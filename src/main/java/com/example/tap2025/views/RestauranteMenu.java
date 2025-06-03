package com.example.tap2025.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class RestauranteMenu extends Stage {

    private final StackPane panelContenido = new StackPane(); // Panel central que cambia dinámicamente

    public RestauranteMenu() {
        this.setTitle("Menú Principal - Restaurante");

        // Barra superior de navegación
        HBox barraSuperior = new HBox(10);
        barraSuperior.setPadding(new Insets(10));
        barraSuperior.setAlignment(Pos.CENTER);
        barraSuperior.setStyle("-fx-background-color: #f0f0f0;");

        // Botones del menú con íconos
        Button btnOrdenes = crearBotonBarra("Órdenes", "/images/load7.png");
        Button btnMesas = crearBotonBarra("Mesas", "/images/load4.png");
        Button btnCategorias = crearBotonBarra("Categorías", "/images/load6.png");
        Button btnClientes = crearBotonBarra("Clientes", "/images/load.png");
        Button btnEmpleados = crearBotonBarra("Empleados", "/images/load2.png");
        Button btnProveedores = crearBotonBarra("Proveedores", "/images/load3.png");
        Button btnCrearOrden = crearBotonBarra("Crear Orden", "/images/load10.png");
        Button btnSalir = new Button("Salir");

        // Acciones de los botones
        btnOrdenes.setOnAction(e -> cargarContenido(new ListaOrden().getVista()));
        btnMesas.setOnAction(e -> cargarContenido(new ListaMesas().getVista()));
        btnCategorias.setOnAction(e -> cargarContenido(new ListaProducto().getVista()));
        btnClientes.setOnAction(e -> cargarContenido(new ListaClientes().getVista()));
        btnEmpleados.setOnAction(e -> cargarContenido(new ListaEmpleado().getVista()));
        btnProveedores.setOnAction(e -> cargarContenido(new ListaProveedores().getVista()));
        btnCrearOrden.setOnAction(e -> {
            // Aquí va la acción para Crear Orden
            System.out.println("Botón Crear Orden clickeado");
            // Por ejemplo: cargarContenido(new CrearOrden().getVista());
        });
        btnSalir.setOnAction(e -> this.close());

        // Agregar todos los botones a la barra
        barraSuperior.getChildren().addAll(
                btnOrdenes, btnMesas, btnCategorias, btnClientes, btnEmpleados,
                btnProveedores, btnCrearOrden, btnSalir
        );

        // Panel principal con logo al inicio
        panelContenido.setPadding(new Insets(20));
        Image logo = new Image(getClass().getResourceAsStream("/images/logo.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(400);
        panelContenido.getChildren().add(logoView);

        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setTop(barraSuperior);
        layoutPrincipal.setCenter(panelContenido);

        Scene escena = new Scene(layoutPrincipal, 1000, 700);
        this.setScene(escena);
        this.show();
    }

    private Button crearBotonBarra(String texto, String rutaImagen) {
        Image img = new Image(getClass().getResourceAsStream(rutaImagen));
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(32);
        imgView.setFitHeight(32);

        Button btn = new Button(texto, imgView);
        btn.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        btn.setPrefSize(100, 80);
        btn.setTextAlignment(TextAlignment.CENTER);
        return btn;
    }

    private void cargarContenido(Pane nuevoContenido) {
        panelContenido.getChildren().clear();
        panelContenido.getChildren().add(nuevoContenido);
    }
}