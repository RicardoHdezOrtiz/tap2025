package com.example.tap2025.views;

import com.example.tap2025.modelos.OrdenDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Orden extends Stage {

    private TextField txtIdCliente, txtFecha, txtTotal, txtNoMesa, txtIdEmpleado;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;
    private TableView<OrdenDAO> tbvOrdenes;
    private OrdenDAO objOrden;

    public Orden(TableView<OrdenDAO> tbv, OrdenDAO obj) {
        this.tbvOrdenes = tbv;
        CrearUI();
        if (obj == null) {
            objOrden = new OrdenDAO();
        } else {
            objOrden = obj;
            txtIdCliente.setText(String.valueOf(obj.getIdCliente()));
            txtFecha.setText(obj.getFecha());
            txtTotal.setText(String.valueOf(obj.getTotal()));
            txtNoMesa.setText(String.valueOf(obj.getNoMesa()));
            txtIdEmpleado.setText(String.valueOf(obj.getIdEmpleado()));
        }
        this.setTitle("Registro de Orden");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtIdCliente = new TextField();
        txtIdCliente.setPromptText("ID Cliente");

        txtFecha = new TextField();
        txtFecha.setPromptText("Fecha (yyyy-mm-dd)");

        txtTotal = new TextField();
        txtTotal.setPromptText("Total");

        txtNoMesa = new TextField();
        txtNoMesa.setPromptText("No. Mesa");

        txtIdEmpleado = new TextField();
        txtIdEmpleado.setPromptText("ID Empleado");

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(e -> {
            try {
                objOrden.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
                objOrden.setFecha(txtFecha.getText());
                objOrden.setTotal(Float.parseFloat(txtTotal.getText()));
                objOrden.setNoMesa(Integer.parseInt(txtNoMesa.getText()));
                objOrden.setIdEmpleado(Integer.parseInt(txtIdEmpleado.getText()));

                if (objOrden.getIdOrden() > 0)
                    objOrden.UPDATE();
                else
                    objOrden.INSERT();

                tbvOrdenes.setItems(objOrden.SELECT());
                tbvOrdenes.refresh();
                this.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor ingresa datos válidos.");
                alert.show();
            }
        });

        vBox = new VBox(10, txtIdCliente, txtFecha, txtTotal, txtNoMesa, txtIdEmpleado, btnGuardar);
        escena = new Scene(vBox, 300, 250);
    }
}