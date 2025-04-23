package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.CategoriaDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaCategoria extends Stage {

    private TableView<CategoriaDAO> tbvCategoria;
    private ToolBar tlbMenu;
    private Button btnAgregar;
    private Scene escena;
    private VBox vBox;

    public ListaCategoria() {
        CrearUI();
        this.setTitle("Listado de Categorías");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvCategoria = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(e -> new Categoria(tbvCategoria, null));

        ImageView imv = new ImageView(getClass().getResource("/images/load6.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar(btnAgregar);
        CrearTabla();
        vBox = new VBox(tlbMenu, tbvCategoria);
        escena = new Scene(vBox, 800, 600);
    }

    private void CrearTabla() {
        CategoriaDAO obj = new CategoriaDAO();

        TableColumn<CategoriaDAO, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nomCategoria"));

        TableColumn<CategoriaDAO, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionCategoria"));

        TableColumn<CategoriaDAO, String> colEditar = new TableColumn<>("Editar");
        colEditar.setCellFactory(param -> new ButtonCell<>("Editar"));

        TableColumn<CategoriaDAO, String> colEliminar = new TableColumn<>("Eliminar");
        colEliminar.setCellFactory(param -> new ButtonCell<>("Eliminar"));

        tbvCategoria.getColumns().addAll(colNombre, colDescripcion, colEditar, colEliminar);
        tbvCategoria.setItems(obj.SELECT());
    }
}