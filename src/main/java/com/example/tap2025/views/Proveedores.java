package com.example.tap2025.views;

import com.example.tap2025.modelos.ProveedoresDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Proveedores extends Stage {

    private Button btnGuardar;
    private TextField txtNombre, txtTelContacto, txtDireccion, txtEmail, txtNota;
    private VBox vBox;
    private Scene escena;
    private ProveedoresDAO objP;
    private TableView<ProveedoresDAO> tbvProveedores;

    public Proveedores(TableView<ProveedoresDAO> tbvProv, ProveedoresDAO obj) {
        this.tbvProveedores = tbvProv;
        CrearUI();
        if (obj == null) {
            objP = new ProveedoresDAO();
        } else {
            objP = obj;
            txtNombre.setText(objP.getNombre());
            txtTelContacto.setText(objP.getTelContacto());
            txtDireccion.setText(objP.getDireccion());
            txtEmail.setText(objP.getEmail());
            txtNota.setText(objP.getNota());
        }
        this.setTitle("Registrar Proveedor");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNombre = new TextField();
        txtTelContacto = new TextField();
        txtDireccion = new TextField();
        txtEmail = new TextField();
        txtNota = new TextField();
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {
            objP.setNombre(txtNombre.getText());
            objP.setTelContacto(txtTelContacto.getText());
            objP.setDireccion(txtDireccion.getText());
            objP.setEmail(txtEmail.getText());
            objP.setNota(txtNota.getText());
            if (objP.getIdProveedor() > 0)
                objP.UPDATE();
            else
                objP.INSERT();
            tbvProveedores.setItems(objP.SELECT());
            tbvProveedores.refresh();
            this.close();
        });
        vBox = new VBox(txtNombre, txtTelContacto, txtDireccion, txtEmail, txtNota, btnGuardar);
        escena = new Scene(vBox, 200, 180);
    }
}