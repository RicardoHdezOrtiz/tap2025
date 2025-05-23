package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.ProveedoresDAO;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ListaProveedores {

    private ToolBar tlbMenu;
    private TableView<ProveedoresDAO> tbvProveedores;
    private VBox vBox;
    private Button btnAgregar;

    public ListaProveedores() {
        CrearUI();
    }

    private void CrearUI() {
        tbvProveedores = new TableView<>();
        btnAgregar = new Button();

        ImageView imv = new ImageView(getClass().getResource("/images/load3.png").toString());
        imv.setFitWidth(16);
        imv.setFitHeight(16);
        btnAgregar.setGraphic(imv);

        btnAgregar.setOnAction(event -> {
            new Proveedores(tbvProveedores, null);
        });

        tlbMenu = new ToolBar(btnAgregar);

        CrearTabla();

        vBox = new VBox(tlbMenu, tbvProveedores);
        vBox.setSpacing(5);
    }

    private void CrearTabla() {
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
        tbcEditar.setCellFactory(param -> new ButtonCell<>("Editar"));

        TableColumn<ProveedoresDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCell<>("Eliminar"));

        tbvProveedores.getColumns().addAll(
                tbcID, tbcNombre, tbcTel, tbcDireccion, tbcEmail, tbcNota, tbcEditar, tbcEliminar
        );

        tbvProveedores.setItems(objP.SELECT());
    }

    // Método público para devolver el contenedor con toda la interfaz
    public VBox getVista() {
        return vBox;
    }
}