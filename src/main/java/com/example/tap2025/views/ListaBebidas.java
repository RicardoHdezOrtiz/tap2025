package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.BebidasDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaBebidas extends Stage {

    private TableView<BebidasDAO> tbvBebidas;
    private ToolBar tlbMenu;
    private Button btnAgregar;
    private Scene escena;
    private VBox vBox;

    public ListaBebidas(){
        CrearUI();
        this.setTitle("Listado de Bebidas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        tbvBebidas = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(e -> new Bebidas(tbvBebidas, null));

        ImageView imv = new ImageView(getClass().getResource("/images/load5.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar(btnAgregar);
        CrearTabla();
        vBox = new VBox(tlbMenu, tbvBebidas);
        escena = new Scene(vBox, 800, 600);
    }

    private void CrearTabla(){
        BebidasDAO objB = new BebidasDAO();

        TableColumn<BebidasDAO, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("idBebida"));

        TableColumn<BebidasDAO, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreBebida"));

        TableColumn<BebidasDAO, Float> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<BebidasDAO, Float> colCosto = new TableColumn<>("Costo");
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        TableColumn<BebidasDAO, Integer> colCategoria = new TableColumn<>("ID Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        TableColumn<BebidasDAO, String> colEditar = new TableColumn<>("Editar");
        colEditar.setCellFactory(param -> new ButtonCell<>("Editar"));

        TableColumn<BebidasDAO, String> colEliminar = new TableColumn<>("Eliminar");
        colEliminar.setCellFactory(param -> new ButtonCell<>("Eliminar"));

        tbvBebidas.getColumns().addAll(colID, colNombre, colPrecio, colCosto, colCategoria, colEditar, colEliminar);
        tbvBebidas.setItems(objB.SELECT());
    }
}