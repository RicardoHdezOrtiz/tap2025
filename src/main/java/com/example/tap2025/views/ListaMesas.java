package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.MesasDAO;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ListaMesas {

    private ToolBar tlbMenu;
    private TableView<MesasDAO> tbvMesas;
    private VBox vBox;
    private Button btnAgregar;

    public ListaMesas() {
        CrearUI();
    }

    private void CrearUI() {
        tbvMesas = new TableView<>();
        btnAgregar = new Button();

        ImageView imv = new ImageView(getClass().getResource("/images/load4.png").toString());
        imv.setFitWidth(16);  // Más pequeño que antes
        imv.setFitHeight(16);
        btnAgregar.setGraphic(imv);

        // Aquí debes cambiar la acción del botón para mostrar el formulario en la parte inferior
        btnAgregar.setOnAction(event -> {
            new Mesa(tbvMesas, null); // null porque estás creando una nueva mesa
        });

        tlbMenu = new ToolBar(btnAgregar);

        CrearTabla();

        vBox = new VBox(tlbMenu, tbvMesas);
        vBox.setSpacing(5);
    }

    private void CrearTabla() {
        MesasDAO objM = new MesasDAO();

        TableColumn<MesasDAO, Integer> tbcNumero = new TableColumn<>("No. Mesa");
        tbcNumero.setCellValueFactory(new PropertyValueFactory<>("noMesa"));

        TableColumn<MesasDAO, Integer> tbcCapacidad = new TableColumn<>("Capacidad");
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

    // Método para que tu menú principal obtenga la vista y la muestre
    public VBox getVista() {
        return vBox;
    }
}