package com.example.tap2025.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestauranteMenu extends Stage {

    public RestauranteMenu() {
        this.setTitle("Menú Principal - Restaurante");

        // Crear botones
        Button btnClientes = new Button("Clientes");
        Button btnEmpleados = new Button("Empleados");
        Button btnProveedores = new Button("Proveedores");
        Button btnMesas = new Button("Mesas");
        Button btnBebidas = new Button("Bebidas");
        Button btnCategorias = new Button("Categorías");
        Button btnOrdenes = new Button("Órdenes");

        // Acciones
        btnClientes.setOnAction(e -> new ListaClientes());
        btnEmpleados.setOnAction(e -> new ListaEmpleado());
        btnProveedores.setOnAction(e -> new ListaProveedores());
        btnMesas.setOnAction(e -> new ListaMesas());
        btnBebidas.setOnAction(e -> new ListaBebidas());
        btnCategorias.setOnAction(e -> new ListaCategoria());
        btnOrdenes.setOnAction(e -> new ListaOrden());

        // Crear VBox y alinear
        VBox vbox = new VBox(15, btnClientes, btnEmpleados, btnProveedores, btnMesas, btnBebidas, btnCategorias, btnOrdenes);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.CENTER);

        // Tamaño y escena
        Scene scene = new Scene(vbox, 300, 400);
        this.setScene(scene);
        this.show();
    }
}