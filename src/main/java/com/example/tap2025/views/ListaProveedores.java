package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.ProveedoresDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaProveedores extends Stage {

    private ToolBar tlbMenu;
    private TableView<ProveedoresDAO> tbvProveedores;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaProveedores() {
        CrearUI();
        this.setTitle("Listado de Proveedores :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvProveedores = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Proveedores(tbvProveedores, null));
        ImageView imv = new ImageView(getClass().getResource("/images/load3.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);
        tlbMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tlbMenu, tbvProveedores);
        escena = new Scene(vBox, 800, 600);
    }

    private void CreateTable() {
        ProveedoresDAO objP = new ProveedoresDAO();

        TableColumn<ProveedoresDAO, Integer> tbcID = new TableColumn<>("ID");
        tbcID.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));

        TableColumn<ProveedoresDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<ProveedoresDAO, String> tbcTel = new TableColumn<>("Tel. Contacto");
        tbcTel.setCellValueFactory(new PropertyValueFactory<>("telContacto"));

        TableColumn<ProveedoresDAO, String> tbcDireccion = new TableColumn<>("Dirección");
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<ProveedoresDAO, String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<ProveedoresDAO, String> tbcNota = new TableColumn<>("Nota");
        tbcNota.setCellValueFactory(new PropertyValueFactory<>("nota"));

        TableColumn<ProveedoresDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<ProveedoresDAO, String>, TableCell<ProveedoresDAO, String>>() {
            @Override
            public TableCell<ProveedoresDAO, String> call(TableColumn<ProveedoresDAO, String> param) {
                return new ButtonCell("Editar");
            }
        });

        TableColumn<ProveedoresDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ProveedoresDAO, String>, TableCell<ProveedoresDAO, String>>() {
            @Override
            public TableCell<ProveedoresDAO, String> call(TableColumn<ProveedoresDAO, String> param) {
                return new ButtonCell("Eliminar");
            }
        });

        tbvProveedores.getColumns().addAll(
                tbcID, tbcNombre, tbcTel, tbcDireccion, tbcEmail, tbcNota, tbcEditar, tbcEliminar
        );

        tbvProveedores.setItems(objP.SELECT());
    }
}