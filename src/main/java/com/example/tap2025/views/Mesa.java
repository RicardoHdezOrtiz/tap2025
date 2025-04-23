package com.example.tap2025.views;

import com.example.tap2025.modelos.MesasDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Mesa extends Stage {

    private TextField txtNumeroMesa, txtCapacidad;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;
    private TableView<MesasDAO> tbvMesas;
    private MesasDAO objM;

    public Mesa(TableView<MesasDAO> tbv, MesasDAO obj){
        this.tbvMesas = tbv;
        CrearUI();
        if(obj == null){
            objM = new MesasDAO();
        } else {
            objM = obj;
            txtNumeroMesa.setText(objM.getNumeroMesa());
            txtCapacidad.setText(objM.getCapacidad());
        }
        this.setTitle("Registrar Mesa");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        txtNumeroMesa = new TextField();
        txtCapacidad = new TextField();
        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objM.setNumeroMesa(txtNumeroMesa.getText());
            objM.setCapacidad(txtCapacidad.getText());

            if (objM.getIdMesa() > 0)
                objM.UPDATE();
            else
                objM.INSERT();

            tbvMesas.setItems(objM.SELECT());
            tbvMesas.refresh();
            this.close();
        });

        vBox = new VBox(txtNumeroMesa, txtCapacidad, btnGuardar);
        escena = new Scene(vBox, 200, 150);
    }
}