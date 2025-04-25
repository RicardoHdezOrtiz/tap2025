package com.example.tap2025.views;

import com.example.tap2025.modelos.MesasDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class Mesa extends Stage {

    private TextField txtNoMesa, txtCapacidad;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;
    private TableView<MesasDAO> tbvMesas;
    private MesasDAO objM;

    public Mesa(TableView<MesasDAO> tbv, MesasDAO obj) {
        this.tbvMesas = tbv;
        CrearUI();
        if (obj == null) {
            objM = new MesasDAO();
        } else {
            objM = obj;
            txtNoMesa.setText(String.valueOf(objM.getNoMesa()));
            txtCapacidad.setText(String.valueOf(objM.getCapacidad()));
        }
        this.setTitle("Registrar Mesa");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNoMesa = new TextField();
        txtCapacidad = new TextField();
        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objM.setNoMesa(Integer.parseInt(txtNoMesa.getText()));
            objM.setCapacidad(Integer.parseInt(txtCapacidad.getText()));

            if (tbvMesas.getItems().contains(objM))
                objM.UPDATE();
            else
                objM.INSERT();

            tbvMesas.setItems(objM.SELECT());
            tbvMesas.refresh();
            this.close();
        });

        vBox = new VBox(txtNoMesa, txtCapacidad, btnGuardar);
        escena = new Scene(vBox, 200, 150);
    }
}