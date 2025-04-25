package com.example.tap2025.views;

import com.example.tap2025.modelos.EmpleadoDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class Empleado extends Stage {

    private Button btnGuardar;
    private TextField txtNombres, txtApellido1, txtApellido2, txtCURP, txtRFC, txtSueldo, txtHorario, txtNSS, txtCelular, txtFechaIngreso;
    private VBox vBox;
    private Scene escena;
    private EmpleadoDAO objEmpleado;
    private TableView<EmpleadoDAO> tbvEmpleados;

    public Empleado(TableView<EmpleadoDAO> tbvEmpleados, EmpleadoDAO obj) {
        this.tbvEmpleados = tbvEmpleados;
        CrearUI();
        if (obj == null) {
            objEmpleado = new EmpleadoDAO();
        } else {
            objEmpleado = obj;
            txtNombres.setText(objEmpleado.getNombres());
            txtApellido1.setText(objEmpleado.getApellido1());
            txtApellido2.setText(objEmpleado.getApellido2());
            txtCURP.setText(objEmpleado.getCurp());
            txtRFC.setText(objEmpleado.getRfc());
            txtSueldo.setText(String.valueOf(objEmpleado.getSueldo()));
            txtHorario.setText(objEmpleado.getHorario());
            txtNSS.setText(objEmpleado.getNssEmp());
            txtCelular.setText(objEmpleado.getCelEmp());
            txtFechaIngreso.setText(String.valueOf(objEmpleado.getFechaIngreso()));
        }
        this.setTitle("Registrar Empleado");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNombres = new TextField();
        txtApellido1 = new TextField();
        txtApellido2 = new TextField();
        txtCURP = new TextField();
        txtRFC = new TextField();
        txtSueldo = new TextField();
        txtHorario = new TextField();
        txtNSS = new TextField();
        txtCelular = new TextField();
        txtFechaIngreso = new TextField();

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {
            objEmpleado.setNombres(txtNombres.getText());
            objEmpleado.setApellido1(txtApellido1.getText());
            objEmpleado.setApellido2(txtApellido2.getText());
            objEmpleado.setCurp(txtCURP.getText());
            objEmpleado.setRfc(txtRFC.getText());
            objEmpleado.setSueldo(Float.parseFloat(txtSueldo.getText()));
            objEmpleado.setHorario(txtHorario.getText());
            objEmpleado.setNssEmp(txtNSS.getText());
            objEmpleado.setCelEmp(txtCelular.getText());
            objEmpleado.setFechaIngreso(txtFechaIngreso.getText());

            if (objEmpleado.getIdEmp() > 0) {
                objEmpleado.UPDATE();
            } else {
                objEmpleado.INSERT();
            }

            tbvEmpleados.setItems(objEmpleado.SELECT());
            tbvEmpleados.refresh();
            this.close();
        });

        vBox = new VBox(txtNombres, txtApellido1, txtApellido2, txtCURP, txtRFC, txtSueldo,
                txtHorario, txtNSS, txtCelular, txtFechaIngreso, btnGuardar);
        escena = new Scene(vBox, 300, 400);
    }
}