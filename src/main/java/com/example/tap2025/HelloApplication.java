package com.example.tap2025;

import com.example.tap2025.views.VentasRestaurantes;
import com.example.tap2025.views.Calculadora;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2025.views.VentasRestaurantes;

import java.io.IOException;

public class HelloApplication extends Application {
    //private Button btnSaludo, btnSaludo2, btnSaludo3;
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2;
    private MenuItem mitCalculadora, miRestaurante;
    private Scene escena;

    void CrearUI() {
        mitCalculadora = new MenuItem("Calculadora");

        mitCalculadora.setOnAction(e -> {
            Calculadora calculadora = new Calculadora();  // Crear una nueva instancia de la calculadora
            calculadora.show();  // Mostrar la ventana de la calculadora
        });
        miRestaurante = new MenuItem("Restaurante");
        miRestaurante.setOnAction(event -> new VentasRestaurantes());
        menCompetencia1 = new Menu("Competencia1");
        menCompetencia1.getItems().addAll(mitCalculadora);
        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menCompetencia1);  // Agrega ambos menús a la barra
        vBox = new VBox(mnbPrincipal);
        escena = new Scene(vBox);
        escena.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());

    }

    @Override
    public void start(Stage stage) throws IOException {
        CrearUI();
        stage.setTitle("Hola Mundo de Eventos :) ");
        stage.setScene(escena);
        stage.show();
        stage.setMaximized(true);
    }

    public static void main(String[] args) {launch();}

        void clickEvent(){
            System.out.println("Evento desde un metodo :)");
        }
    }

    //Se ocupa despues
    /*btnSaludo = new Button("bienvenido amiguito ");
        btnSaludo.setOnAction(event -> clickEvent());
        btnSaludo2 = new Button("bienvenido amiguito ");
        btnSaludo2.setOnAction(event -> clickEvent());
        btnSaludo3 = new Button("bienvenido amiguito ");
        vBox = new VBox(btnSaludo, btnSaludo2, btnSaludo3);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 0,0,0));*/