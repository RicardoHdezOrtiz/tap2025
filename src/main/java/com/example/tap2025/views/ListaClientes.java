package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.ClientesDAO;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ListaClientes {

    private ToolBar tlbMenu;
    private TableView<ClientesDAO> tbvClientes;
    private VBox vBox;
    private Button btnAgregar;

    public ListaClientes() {
        CrearUI();
    }

    private void CrearUI() {
        tbvClientes = new TableView<>();
        btnAgregar = new Button();

        ImageView imv = new ImageView(getClass().getResource("/images/load.png").toString());
        imv.setFitWidth(16);  // Tamaño más pequeño
        imv.setFitHeight(16);
        btnAgregar.setGraphic(imv);

        btnAgregar.setOnAction(event -> {
            new Cliente(tbvClientes, null);  // null porque es un nuevo cliente
        });

        tlbMenu = new ToolBar(btnAgregar);

        CrearTabla();

        vBox = new VBox(tlbMenu, tbvClientes);
        vBox.setSpacing(5);
    }

    private void CrearTabla() {
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
        tbcEditar.setCellFactory(param -> new ButtonCell<>("Editar"));

        TableColumn<ClientesDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCell<>("Eliminar"));

        tbvClientes.getColumns().addAll(
                tbcIdCliente,
                tbcNomCte,
                tbcApellidoPaterno,
                tbcApellidoMaterno,
                tbcDireccion,
                tbcEmail,
                tbcTel,
                tbcEditar,
                tbcEliminar
        );
        tbvClientes.setItems(objC.SELECT());
    }

    // Para que puedas obtener la vista y agregarla a tu layout principal
    public VBox getVista() {
        return vBox;
    }
}