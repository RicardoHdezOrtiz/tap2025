package com.example.tap2025.views;

import com.example.tap2025.modelos.CategoriaDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Categoria extends Stage {

    private TextField txtNombre, txtDescripcion;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;
    private TableView<CategoriaDAO> tbvCategoria;
    private CategoriaDAO objC;

    public Categoria(TableView<CategoriaDAO> tbv, CategoriaDAO obj) {
        this.tbvCategoria = tbv;
        CrearUI();
        if (obj == null) {
            objC = new CategoriaDAO();
        } else {
            objC = obj;
            txtNombre.setText(obj.getNomCategoria());
            txtDescripcion.setText(obj.getDescripcionCategoria());
        }
        this.setTitle("Registro de Categoría");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNombre = new TextField();
        txtDescripcion = new TextField();
        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(e -> {
            objC.setNomCategoria(txtNombre.getText());
            objC.setDescripcionCategoria(txtDescripcion.getText());

            if (objC.getNoCategoria() > 0)
                objC.UPDATE();
            else
                objC.INSERT();

            tbvCategoria.setItems(objC.SELECT());
            tbvCategoria.refresh();
            this.close();
        });

        vBox = new VBox(txtNombre, txtDescripcion, btnGuardar);
        escena = new Scene(vBox, 250, 150);
    }
}