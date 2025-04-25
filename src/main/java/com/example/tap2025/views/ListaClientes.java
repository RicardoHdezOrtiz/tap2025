package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaClientes extends Stage {

    private ToolBar tlbMenu;
    private TableView<ClientesDAO> tbvClientes;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaClientes() {
        CrearUI();
        this.setTitle("Listado de Clientes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvClientes = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Cliente(tbvClientes, null));

        ImageView imv = new ImageView(getClass().getResource("/images/load.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar(btnAgregar);
        CreateTable();

        vBox = new VBox(tlbMenu, tbvClientes);
        escena = new Scene(vBox, 800, 600);
    }

    private void CreateTable() {
        ClientesDAO objC = new ClientesDAO();

        TableColumn<ClientesDAO, Integer> tbcIdCliente = new TableColumn<>("ID");
        tbcIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCte"));

        TableColumn<ClientesDAO, String> tbcNomCte = new TableColumn<>("Nombre");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nomCte"));

        TableColumn<ClientesDAO, String> tbcApellidoPaterno = new TableColumn<>("Apellido Paterno");
        tbcApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));

        TableColumn<ClientesDAO, String> tbcApellidoMaterno = new TableColumn<>("Apellido Materno");
        tbcApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));

        TableColumn<ClientesDAO, String> tbcDireccion = new TableColumn<>("Dirección");
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<ClientesDAO, String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("emailCte"));

        TableColumn<ClientesDAO, String> tbcTel = new TableColumn<>("Teléfono");
        tbcTel.setCellValueFactory(new PropertyValueFactory<>("telCte"));

        TableColumn<ClientesDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCell("Editar"));

        TableColumn<ClientesDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCell("Eliminar"));

        tbvClientes.getColumns().addAll(tbcIdCliente, tbcNomCte, tbcApellidoPaterno, tbcApellidoMaterno, tbcDireccion, tbcEmail, tbcTel, tbcEditar, tbcEliminar);
        tbvClientes.setItems(objC.SELECT());
    }
}