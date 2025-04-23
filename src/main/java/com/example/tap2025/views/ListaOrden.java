package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.OrdenDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaOrden extends Stage {

    private TableView<OrdenDAO> tbvOrdenes;
    private ToolBar tlbMenu;
    private Button btnAgregar;
    private VBox vBox;
    private Scene escena;

    public ListaOrden() {
        CrearUI();
        this.setTitle("Listado de Órdenes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvOrdenes = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(e -> new Orden(tbvOrdenes, null));

        ImageView imv = new ImageView(getClass().getResource("/images/load7.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar(btnAgregar);
        CrearTabla();
        vBox = new VBox(tlbMenu, tbvOrdenes);
        escena = new Scene(vBox, 800, 600);
    }

    private void CrearTabla() {
        OrdenDAO obj = new OrdenDAO();

        TableColumn<OrdenDAO, Integer> colIdCliente = new TableColumn<>("ID Cliente");
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

        TableColumn<OrdenDAO, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<OrdenDAO, Float> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<OrdenDAO, Integer> colIdMesa = new TableColumn<>("ID Mesa");
        colIdMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));

        TableColumn<OrdenDAO, Integer> colIdEmpleado = new TableColumn<>("ID Empleado");
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));

        TableColumn<OrdenDAO, String> colEditar = new TableColumn<>("Editar");
        colEditar.setCellFactory(param -> new ButtonCell<>("Editar"));

        TableColumn<OrdenDAO, String> colEliminar = new TableColumn<>("Eliminar");
        colEliminar.setCellFactory(param -> new ButtonCell<>("Eliminar"));

        tbvOrdenes.getColumns().addAll(colIdCliente, colFecha, colTotal, colIdMesa, colIdEmpleado, colEditar, colEliminar);
        tbvOrdenes.setItems(obj.SELECT());
    }
}