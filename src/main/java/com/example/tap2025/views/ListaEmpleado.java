package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.EmpleadoDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaEmpleado extends Stage {

    private ToolBar tlbMenu;
    private TableView<EmpleadoDAO> tbvEmpleados;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaEmpleado() {
        CrearUI();
        this.setTitle("Listado de Empleados :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvEmpleados = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Empleado(tbvEmpleados, null));
        ImageView imv = new ImageView(getClass().getResource("/images/load2.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);
        tlbMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tlbMenu, tbvEmpleados);
        escena = new Scene(vBox, 800, 600);
    }

    private void CreateTable() {
        EmpleadoDAO objE = new EmpleadoDAO();

        TableColumn<EmpleadoDAO, Integer> tbcIdEmp = new TableColumn<>("ID");
        tbcIdEmp.setCellValueFactory(new PropertyValueFactory<>("idEmp"));

        TableColumn<EmpleadoDAO, String> tbcNombres = new TableColumn<>("Nombres");
        tbcNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));

        TableColumn<EmpleadoDAO, String> tbcApellido1 = new TableColumn<>("Apellido 1");
        tbcApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));

        TableColumn<EmpleadoDAO, String> tbcApellido2 = new TableColumn<>("Apellido 2");
        tbcApellido2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));

        TableColumn<EmpleadoDAO, String> tbcCURP = new TableColumn<>("CURP");
        tbcCURP.setCellValueFactory(new PropertyValueFactory<>("curp"));

        TableColumn<EmpleadoDAO, String> tbcRFC = new TableColumn<>("RFC");
        tbcRFC.setCellValueFactory(new PropertyValueFactory<>("rfc"));

        TableColumn<EmpleadoDAO, Float> tbcSueldo = new TableColumn<>("Sueldo");
        tbcSueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));

        TableColumn<EmpleadoDAO, String> tbcHorario = new TableColumn<>("Horario");
        tbcHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        TableColumn<EmpleadoDAO, String> tbcNSS = new TableColumn<>("NSS");
        tbcNSS.setCellValueFactory(new PropertyValueFactory<>("nssEmp"));

        TableColumn<EmpleadoDAO, String> tbcCelular = new TableColumn<>("Celular");
        tbcCelular.setCellValueFactory(new PropertyValueFactory<>("celEmp"));

        TableColumn<EmpleadoDAO, String> tbcFechaIngreso = new TableColumn<>("Fecha Ingreso");
        tbcFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));

        TableColumn<EmpleadoDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
            @Override
            public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> param) {
                return new ButtonCell<>("Editar");
            }
        });

        TableColumn<EmpleadoDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
            @Override
            public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> param) {
                return new ButtonCell<>("Eliminar");
            }
        });

        tbvEmpleados.getColumns().addAll(tbcIdEmp, tbcNombres, tbcApellido1, tbcApellido2, tbcCURP,
                tbcRFC, tbcSueldo, tbcHorario, tbcNSS, tbcCelular, tbcFechaIngreso, tbcEditar, tbcEliminar);
        tbvEmpleados.setItems(objE.SELECT());
    }
}