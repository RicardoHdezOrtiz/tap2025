package com.example.tap2025.views;

import com.example.tap2025.modelos.BebidasDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Bebidas extends Stage {

    private TextField txtNombre, txtPrecio, txtCosto, txtCategoria;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;
    private TableView<BebidasDAO> tbvBebidas;
    private BebidasDAO objB;

    public Bebidas(TableView<BebidasDAO> tbv, BebidasDAO obj) {
        this.tbvBebidas = tbv;
        CrearUI();
        if(obj == null){
            objB = new BebidasDAO();
        } else {
            objB = obj;
            txtNombre.setText(obj.getNombreBebida());
            txtPrecio.setText(String.valueOf(obj.getPrecio()));
            txtCosto.setText(String.valueOf(obj.getCosto()));
            txtCategoria.setText(String.valueOf(obj.getIdCategoria()));
        }
        this.setTitle("Registro de Bebidas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        txtNombre = new TextField();
        txtPrecio = new TextField();
        txtCosto = new TextField();
        txtCategoria = new TextField();
        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(e -> {
            objB.setNombreBebida(txtNombre.getText());
            objB.setPrecio(Float.parseFloat(txtPrecio.getText()));
            objB.setCosto(Float.parseFloat(txtCosto.getText()));
            objB.setIdCategoria(Integer.parseInt(txtCategoria.getText()));

            if(objB.getIdBebida() > 0)
                objB.UPDATE();
            else
                objB.INSERT();

            tbvBebidas.setItems(objB.SELECT());
            tbvBebidas.refresh();
            this.close();
        });

        vBox = new VBox(txtNombre, txtPrecio, txtCosto, txtCategoria, btnGuardar);
        escena = new Scene(vBox, 250, 200);
    }
}