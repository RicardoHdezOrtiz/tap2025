package com.example.tap2025.views;

import com.example.tap2025.modelos.OrdenDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Orden extends Stage {

    private TextField txtIdCliente, txtFecha, txtTotal, txtIdMesa, txtIdEmpleado;
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
            txtIdMesa.setText(String.valueOf(obj.getIdMesa()));
            txtIdEmpleado.setText(String.valueOf(obj.getIdEmpleado()));
        }
        this.setTitle("Registro de Orden");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtIdCliente = new TextField();
        txtFecha = new TextField();
        txtTotal = new TextField();
        txtIdMesa = new TextField();
        txtIdEmpleado = new TextField();
        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(e -> {
            objOrden.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
            objOrden.setFecha(txtFecha.getText());
            objOrden.setTotal(Float.parseFloat(txtTotal.getText()));
            objOrden.setIdMesa(Integer.parseInt(txtIdMesa.getText()));
            objOrden.setIdEmpleado(Integer.parseInt(txtIdEmpleado.getText()));

            if (objOrden.getIdOrden() > 0)
                objOrden.UPDATE();
            else
                objOrden.INSERT();

            tbvOrdenes.setItems(objOrden.SELECT());
            tbvOrdenes.refresh();
            this.close();
        });

        vBox = new VBox(txtIdCliente, txtFecha, txtTotal, txtIdMesa, txtIdEmpleado, btnGuardar);
        escena = new Scene(vBox, 300, 250);
    }
}