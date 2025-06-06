package com.example.tap2025.views;

import com.example.tap2025.modelos.UsuarioDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;

public class Login extends Stage {
    public Login(Connection conexion) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Usuario");
        PasswordField campoClave = new PasswordField();
        campoClave.setPromptText("Contraseña");
        Button btnIngresar = new Button("Ingresar");

        btnIngresar.setOnAction(e -> {
            String usuario = campoUsuario.getText();
            String clave = campoClave.getText();

            UsuarioDAO dao = new UsuarioDAO(conexion);
            Usuario usuarioAutenticado = dao.validarCredenciales(usuario, clave);

            if (usuarioAutenticado != null) {
                this.close(); // Cierra ventana login
                new RestauranteMenu(usuarioAutenticado.getTipo()); // Muestra menú
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Usuario o contraseña incorrectos");
                alerta.showAndWait();
            }
        });

        layout.getChildren().addAll(campoUsuario, campoClave, btnIngresar);
        Scene escena = new Scene(layout, 300, 200);
        this.setScene(escena);
        this.setTitle("Inicio de sesión");
        this.show();
    }
}