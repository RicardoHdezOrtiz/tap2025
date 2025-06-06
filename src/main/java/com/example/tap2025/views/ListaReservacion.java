package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.ReservacionDAO;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ListaReservacion {

    private ToolBar tlbMenu;
    private TableView<ReservacionDAO> tbvReservaciones;
    private VBox vBox;
    private Button btnAgregar;

    public ListaReservacion() {
        CrearUI();
    }

    private void CrearUI() {
        tbvReservaciones = new TableView<>();
        btnAgregar = new Button();

        ImageView imv = new ImageView(getClass().getResource("/images/load.png").toString());
        imv.setFitWidth(16);
        imv.setFitHeight(16);
        btnAgregar.setGraphic(imv);

        btnAgregar.setOnAction(event -> {
            new Reservacion(tbvReservaciones, null); // null porque es una nueva reservación
        });

        tlbMenu = new ToolBar(btnAgregar);

        CrearTabla();

        vBox = new VBox(tlbMenu, tbvReservaciones);
        vBox.setSpacing(5);
    }

    private void CrearTabla() {
        ReservacionDAO objR = new ReservacionDAO();

        TableColumn<ReservacionDAO, Integer> tbcIdReservacion = new TableColumn<>("ID Reservación");
        tbcIdReservacion.setCellValueFactory(new PropertyValueFactory<>("idReservacion"));

        TableColumn<ReservacionDAO, String> tbcNombreCliente = new TableColumn<>("Nombre Cliente");
        tbcNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));

        TableColumn<ReservacionDAO, String> tbcFechaHora = new TableColumn<>("Fecha y Hora");
        tbcFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));

        TableColumn<ReservacionDAO, Integer> tbcNumPersonas = new TableColumn<>("Núm. Personas");
        tbcNumPersonas.setCellValueFactory(new PropertyValueFactory<>("numPersonas"));

        TableColumn<ReservacionDAO, Integer> tbcNumMesa = new TableColumn<>("Núm. Mesa");
        tbcNumMesa.setCellValueFactory(new PropertyValueFactory<>("numMesa"));

        TableColumn<ReservacionDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCell<>("Editar"));

        TableColumn<ReservacionDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCell<>("Eliminar"));

        tbvReservaciones.getColumns().addAll(
                tbcIdReservacion,
                tbcNombreCliente,
                tbcFechaHora,
                tbcNumPersonas,
                tbcNumMesa,
                tbcEditar,
                tbcEliminar
        );

        tbvReservaciones.setItems(objR.SELECT());
    }

    public VBox getVista() {
        return vBox;
    }
}