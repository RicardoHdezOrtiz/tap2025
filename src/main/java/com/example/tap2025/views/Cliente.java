package com.example.tap2025.views;

import com.example.tap2025.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cliente extends Stage {

    private Button btnGuardar;
    private TextField txtNomCte, txtApellidoPaterno, txtApellidoMaterno, txtDireccion, txtTelCte, txtEmail;
    private VBox vBox;
    private Scene escena;
    private ClientesDAO objC;
    private TableView<ClientesDAO> tbvClientes;

    public Cliente(TableView<ClientesDAO> tbvCte, ClientesDAO obj) {
        this.tbvClientes = tbvCte;
        CrearUI();
        if (obj == null) {
            objC = new ClientesDAO(); // Si el objeto es null, se crea un nuevo objeto vacío
        } else {
            objC = obj;
            txtNomCte.setText(objC.getNomCte());
            txtApellidoPaterno.setText(objC.getApellidoPaterno());
            txtApellidoMaterno.setText(objC.getApellidoMaterno());
            txtDireccion.setText(objC.getDireccion());
            txtEmail.setText(objC.getEmailCte());
            txtTelCte.setText(objC.getTelCte());
        }
        this.setTitle("Registrar Cliente");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNomCte = new TextField();
        txtApellidoPaterno = new TextField();
        txtApellidoMaterno = new TextField();
        txtDireccion = new TextField();
        txtTelCte = new TextField();
        txtEmail = new TextField();
        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objC.setNomCte(txtNomCte.getText());
            objC.setApellidoPaterno(txtApellidoPaterno.getText());
            objC.setApellidoMaterno(txtApellidoMaterno.getText());
            objC.setDireccion(txtDireccion.getText());
            objC.setTelCte(txtTelCte.getText());
            objC.setEmailCte(txtEmail.getText());
            objC.INSERT(); // Guarda el cliente
            tbvClientes.setItems(new ClientesDAO().SELECT()); // Actualiza la lista de clientes
            this.close();
        });

        vBox = new VBox(txtNomCte, txtApellidoPaterno, txtApellidoMaterno, txtDireccion, txtEmail, txtTelCte, btnGuardar);
        escena = new Scene(vBox, 400, 300);
    }
}