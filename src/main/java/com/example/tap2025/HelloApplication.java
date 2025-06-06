package com.example.tap2025;

import com.example.tap2025.modelos.Conexion;
import com.example.tap2025.views.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        Conexion.createConnection(); // Primero conectar
        new Login(Conexion.getConnection()); // Mostrar login
    }

    public static void main(String[] args) {
        launch();
    }
}