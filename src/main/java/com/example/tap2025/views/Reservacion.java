package com.example.tap2025.views;

import com.example.tap2025.modelos.ReservacionDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Timestamp;

public class Reservacion extends Stage {

    private Button btnGuardar;
    private TextField txtNombreCliente, txtFechaHora, txtNumPersonas, txtNumMesa;
    private VBox vBox;
    private Scene escena;
    private ReservacionDAO objR;
    private TableView<ReservacionDAO> tbvReservaciones;

    public Reservacion(TableView<ReservacionDAO> tbvRes, ReservacionDAO obj) {
        this.tbvReservaciones = tbvRes;
        CrearUI();
        if (obj == null) {
            objR = new ReservacionDAO(); // Nuevo objeto vacío
        } else {
            objR = obj;
            txtNombreCliente.setText(objR.getNombreCliente());
            txtFechaHora.setText(objR.getFechaHora().toString()); // Timestamp a String
            txtNumPersonas.setText(String.valueOf(objR.getNumPersonas()));
            txtNumMesa.setText(String.valueOf(objR.getNumMesa()));
        }
        this.setTitle("Registrar Reservación");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNombreCliente = new TextField();
        txtNombreCliente.setPromptText("Nombre del Cliente");

        txtFechaHora = new TextField();
        txtFechaHora.setPromptText("Fecha y Hora (yyyy-MM-dd HH:mm:ss)");

        txtNumPersonas = new TextField();
        txtNumPersonas.setPromptText("Número de Personas");

        txtNumMesa = new TextField();
        txtNumMesa.setPromptText("Número de Mesa");

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objR.setNombreCliente(txtNombreCliente.getText());

            // Convertimos String a Timestamp
            try {
                objR.setFechaHora(Timestamp.valueOf(txtFechaHora.getText()));
            } catch (Exception e) {
                System.out.println("Formato de fecha incorrecto.");
                return;
            }

            objR.setNumPersonas(Integer.parseInt(txtNumPersonas.getText()));
            objR.setNumMesa(Integer.parseInt(txtNumMesa.getText()));

            if (objR.getIdReservacion() == 0) {
                objR.INSERT();
            } else {
                objR.UPDATE();
            }

            tbvReservaciones.setItems(new ReservacionDAO().SELECT());
            this.close();
        });

        vBox = new VBox(txtNombreCliente, txtFechaHora, txtNumPersonas, txtNumMesa, btnGuardar);
        vBox.setSpacing(5);
        escena = new Scene(vBox, 400, 250);
    }
}