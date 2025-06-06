package com.example.tap2025.views;

import com.example.tap2025.utils.ReporteGraficas;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class RestauranteMenu extends Stage {

    private final StackPane panelContenido = new StackPane(); // Panel central para cambiar contenido dinámicamente

    public RestauranteMenu(String tipoUsuario) {
        this.setTitle("Menú Principal - Restaurante");

        // Barra superior con botones
        HBox barraSuperior = new HBox(10);
        barraSuperior.setPadding(new Insets(10));
        barraSuperior.setAlignment(Pos.CENTER);
        barraSuperior.setStyle("-fx-background-color: #f0f0f0;");

        Button btnCrearOrden = crearBotonBarra("Crear Orden", "/images/load10.png");
        Button btnSalir = new Button("Salir");

        // Acción para cargar la vista de crear orden
        btnCrearOrden.setOnAction(e -> cargarContenido(new CrearOrden().getVista()));

        // Acción para cerrar ventana
        btnSalir.setOnAction(e -> this.close());

        // Si el usuario es admin, mostramos más opciones
        if (tipoUsuario != null && tipoUsuario.equalsIgnoreCase("admin")) {
            Button btnOrdenes = crearBotonBarra("Órdenes", "/images/load7.png");
            Button btnMesas = crearBotonBarra("Mesas", "/images/load4.png");
            Button btnCategorias = crearBotonBarra("Categorías", "/images/load6.png");
            Button btnClientes = crearBotonBarra("Clientes", "/images/load.png");
            Button btnEmpleados = crearBotonBarra("Empleados", "/images/load2.png");
            Button btnProveedores = crearBotonBarra("Proveedores", "/images/load3.png");
            Button btnReportes = crearBotonBarra("Reportes", "/images/load11.png");
            Button btnReservaciones = crearBotonBarra("Reservaciones", "/images/load13.png");

            // Asignar eventos a botones admin
            btnOrdenes.setOnAction(e -> cargarContenido(new ListaOrden().getVista()));
            btnMesas.setOnAction(e -> cargarContenido(new ListaMesas().getVista()));
            btnCategorias.setOnAction(e -> cargarContenido(new ListaProducto().getVista()));
            btnClientes.setOnAction(e -> cargarContenido(new ListaClientes().getVista()));
            btnEmpleados.setOnAction(e -> cargarContenido(new ListaEmpleado().getVista()));
            btnProveedores.setOnAction(e -> cargarContenido(new ListaProveedores().getVista()));
            btnReportes.setOnAction(e -> new ReporteGraficas().mostrar(this));
            btnReservaciones.setOnAction(e -> cargarContenido(new ListaReservacion().getVista()));

            barraSuperior.getChildren().addAll(
                    btnOrdenes, btnMesas, btnCategorias, btnClientes,
                    btnEmpleados, btnProveedores, btnReportes, btnReservaciones,
                    btnCrearOrden, btnSalir
            );
        } else {
            // Para otros tipos de usuarios solo mostramos crear orden y salir
            barraSuperior.getChildren().addAll(btnCrearOrden, btnSalir);
        }

        // Etiqueta de bienvenida
        Label lblMensaje = new Label("Bienvenido, tu rol es: " + tipoUsuario);
        lblMensaje.setPadding(new Insets(5));
        lblMensaje.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Contenedor vertical para barra y mensaje
        VBox contenedorSuperior = new VBox();
        contenedorSuperior.getChildren().addAll(barraSuperior, lblMensaje);

        // Panel central con logo por defecto
        panelContenido.setPadding(new Insets(20));
        Image logo = null;
        try {
            logo = new Image(getClass().getResourceAsStream("/images/logo.png"));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar la imagen del logo.");
        }
        if (logo != null) {
            ImageView logoView = new ImageView(logo);
            logoView.setPreserveRatio(true);
            logoView.setFitWidth(400);
            panelContenido.getChildren().add(logoView);
        }

        // Layout principal usando BorderPane
        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setTop(contenedorSuperior);
        layoutPrincipal.setCenter(panelContenido);

        Scene escena = new Scene(layoutPrincipal, 1000, 700);
        this.setScene(escena);
        this.show();
    }

    // Método para crear botones con imagen y texto
    private Button crearBotonBarra(String texto, String rutaImagen) {
        Image img = null;
        try {
            img = new Image(getClass().getResourceAsStream(rutaImagen));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + rutaImagen);
        }

        ImageView imgView = null;
        if (img != null) {
            imgView = new ImageView(img);
            imgView.setFitWidth(32);
            imgView.setFitHeight(32);
        }

        Button btn;
        if (imgView != null) {
            btn = new Button(texto, imgView);
            btn.setContentDisplay(ContentDisplay.TOP);
        } else {
            btn = new Button(texto);
        }

        btn.setPrefSize(100, 80);
        btn.setTextAlignment(TextAlignment.CENTER);

        return btn;
    }

    // Método para cambiar el contenido del panel central
    private void cargarContenido(Pane nuevoContenido) {
        panelContenido.getChildren().clear();
        panelContenido.getChildren().add(nuevoContenido);
    }
}