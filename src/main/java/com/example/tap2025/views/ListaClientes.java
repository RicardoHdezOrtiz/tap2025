package com.example.tap2025.views;

import com.example.tap2025.modelos.ClientesDAO;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaClientes extends Stage {

    private ToolBar tlbMenu;
    private TableView<ClientesDAO> tbvClientes;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;
    public ListaClientes(){
        CrearUI();
        this.setTitle("Listado de Clientes :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvClientes = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Cliente(tbvClientes));
        ImageView imv = new ImageView(getClass().getResource("/images/person_add_icon.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);
        tlbMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tlbMenu,tbvClientes);
        escena = new Scene(vBox, 800, 600);
    }

    private void CreateTable() {
        ClientesDAO objC = new ClientesDAO();
        TableColumn<ClientesDAO,String> tbcNomCte = new TableColumn<>("Nombre");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nomCte"));
        TableColumn<ClientesDAO,String> tbcDireccion = new TableColumn<>("Dirección");
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        TableColumn<ClientesDAO,String> tbcTel = new TableColumn<>("Telefono");
        tbcTel.setCellValueFactory(new PropertyValueFactory<>("telCte"));
        TableColumn<ClientesDAO,String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("emailCte"));
        tbvClientes.getColumns().addAll(tbcNomCte,tbcDireccion,tbcTel,tbcEmail);
        tbvClientes.setItems(objC.SELECT());

        TableColumn<ClientesDAO,String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClientesDAO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ClientesDAO, String> clientesDAOStringCellDataFeatures) {
                return null;
            }
        });
        TableColumn<ClientesDAO,String> tbcEliminar = new TableColumn<>("Eliminar");

        tbvClientes.getColumns().addAll(tbcNomCte,tbcDireccion,tbcTel,tbcEmail);

    }
}