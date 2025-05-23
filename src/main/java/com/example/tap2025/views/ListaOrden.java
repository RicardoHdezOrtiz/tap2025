package com.example.tap2025.views;

import com.example.tap2025.Components.ButtonCell;
import com.example.tap2025.modelos.OrdenDAO;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class ListaOrden {

    private TableView<OrdenDAO> tbvOrdenes;
    private ToolBar tlbMenu;
    private Button btnAgregar;
    private VBox vBox;           // contenedor principal que muestra tabla o formulario
    private VBox formulario;     // contenedor para el formulario
    private VBox contenedorTabla; // contenedor solo con la tabla y su toolbar

    public ListaOrden() {
        CrearUI();
    }

    private void CrearUI() {
        // Botón Agregar
        btnAgregar = new Button();
        ImageView imv = new ImageView(getClass().getResource("/images/load7.png").toString());
        imv.setFitWidth(16);
        imv.setFitHeight(16);
        btnAgregar.setGraphic(imv);

        // Acción para mostrar formulario
        btnAgregar.setOnAction(e -> mostrarFormulario());

        // Barra de menú
        tlbMenu = new ToolBar(btnAgregar);

        // Crear tabla con su toolbar en un VBox aparte para mostrar u ocultar
        contenedorTabla = new VBox();
        contenedorTabla.getChildren().addAll(tlbMenu);

        CrearTabla();

        contenedorTabla.getChildren().add(tbvOrdenes);

        // Crear el contenedor principal que al inicio muestra la tabla
        vBox = new VBox();
        vBox.getChildren().add(contenedorTabla);
    }

    private void CrearTabla() {
        tbvOrdenes = new TableView<>();
        OrdenDAO obj = new OrdenDAO();

        TableColumn<OrdenDAO, Integer> colIdOrden = new TableColumn<>("ID Orden");
        colIdOrden.setCellValueFactory(new PropertyValueFactory<>("idOrden"));

        TableColumn<OrdenDAO, Integer> colIdCliente = new TableColumn<>("ID Cliente");
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

        TableColumn<OrdenDAO, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<OrdenDAO, Float> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<OrdenDAO, Integer> colNoMesa = new TableColumn<>("No. Mesa");
        colNoMesa.setCellValueFactory(new PropertyValueFactory<>("noMesa"));

        TableColumn<OrdenDAO, Integer> colIdEmpleado = new TableColumn<>("ID Empleado");
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));

        TableColumn<OrdenDAO, String> colEditar = new TableColumn<>("Editar");
        colEditar.setCellFactory(param -> new ButtonCell<>("Editar"));

        TableColumn<OrdenDAO, String> colEliminar = new TableColumn<>("Eliminar");
        colEliminar.setCellFactory(param -> new ButtonCell<>("Eliminar"));

        tbvOrdenes.getColumns().addAll(colIdOrden, colIdCliente, colFecha, colTotal, colNoMesa, colIdEmpleado, colEditar, colEliminar);
        tbvOrdenes.setItems(obj.SELECT());
    }

    // Método para mostrar el formulario en el mismo panel
    private void mostrarFormulario() {
        // Si ya se creó el formulario, solo lo mostramos
        if (formulario == null) {
            formulario = crearFormulario();
        }
        vBox.getChildren().clear();
        vBox.getChildren().add(formulario);
    }

    // Método para crear el formulario (puedes personalizarlo)
    private VBox crearFormulario() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        Label lblTitulo = new Label("Agregar Nueva Orden");

        TextField txtIdCliente = new TextField();
        txtIdCliente.setPromptText("ID Cliente");

        TextField txtFecha = new TextField();
        txtFecha.setPromptText("Fecha (YYYY-MM-DD)");

        TextField txtTotal = new TextField();
        txtTotal.setPromptText("Total");

        TextField txtNoMesa = new TextField();
        txtNoMesa.setPromptText("No. Mesa");

        TextField txtIdEmpleado = new TextField();
        txtIdEmpleado.setPromptText("ID Empleado");

        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");

        // Acción botón Cancelar: volver a mostrar tabla
        btnCancelar.setOnAction(e -> mostrarTabla());

        // Acción botón Guardar (aquí deberías agregar la lógica para guardar en BD)
        btnGuardar.setOnAction(e -> {
            System.out.println("Aquí se guardaría la orden con los datos del formulario");
            mostrarTabla(); // volver a tabla luego de guardar
        });

        HBox botones = new HBox(10, btnGuardar, btnCancelar);

        box.getChildren().addAll(lblTitulo, txtIdCliente, txtFecha, txtTotal, txtNoMesa, txtIdEmpleado, botones);

        return box;
    }

    // Método para volver a mostrar la tabla
    private void mostrarTabla() {
        vBox.getChildren().clear();
        vBox.getChildren().add(contenedorTabla);
    }

    // Método público para obtener la vista principal
    public VBox getVista() {
        return vBox;
    }
}