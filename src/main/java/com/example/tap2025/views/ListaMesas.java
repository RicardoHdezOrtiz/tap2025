package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.MesasDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaMesas extends Stage {

    private ToolBar tlbMenu;
    private TableView<MesasDAO> tbvMesas;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaMesas(){
        CrearUI();
        this.setTitle("Listado de Mesas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        tbvMesas = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Mesa(tbvMesas, null));
        ImageView imv = new ImageView(getClass().getResource("/images/load4.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);
        tlbMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tlbMenu, tbvMesas);
        escena = new Scene(vBox, 600, 400);
    }

    private void CreateTable(){
        MesasDAO objM = new MesasDAO();
        TableColumn<MesasDAO, String> tbcNumero = new TableColumn<>("No. Mesa");
        tbcNumero.setCellValueFactory(new PropertyValueFactory<>("numeroMesa"));

        TableColumn<MesasDAO, String> tbcCapacidad = new TableColumn<>("Capacidad");
        tbcCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));

        TableColumn<MesasDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<>() {
            @Override
            public TableCell<MesasDAO, String> call(TableColumn<MesasDAO, String> param) {
                return new ButtonCell<>("Editar");
            }
        });

        TableColumn<MesasDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<>() {
            @Override
            public TableCell<MesasDAO, String> call(TableColumn<MesasDAO, String> param) {
                return new ButtonCell<>("Eliminar");
            }
        });

        tbvMesas.getColumns().addAll(tbcNumero, tbcCapacidad, tbcEditar, tbcEliminar);
        tbvMesas.setItems(objM.SELECT());
    }
}