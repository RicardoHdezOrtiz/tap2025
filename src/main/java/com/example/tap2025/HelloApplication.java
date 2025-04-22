package com.example.tap2025;

import com.example.tap2025.Components.Hilo;
import com.example.tap2025.modelos.Conexion;
import com.example.tap2025.views.*;
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
    private MenuItem mitCalculadora, mitRestaurante, mitRompecabezas, mitHilos;
    private Scene escena;

    void CrearUI() {
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(e -> {
            Calculadora calculadora = new Calculadora();  // Crear una nueva instancia de la calculadora
            calculadora.show();  // Mostrar la ventana de la calculadora
        });

        mitRestaurante = new MenuItem("Restaurante");
        mitRestaurante.setOnAction(event -> new ListaClientes());

        mitRompecabezas = new MenuItem("Rompecabezas");
        mitRompecabezas.setOnAction(event -> new Rompecabezas());


        menCompetencia1 = new Menu("Competencia1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitRestaurante, mitRompecabezas);

        menCompetencia2 = new Menu("Competencia2");

        mitHilos = new MenuItem("Celayork");
        menCompetencia2.getItems().addAll(mitHilos);
        mitHilos.setOnAction(event -> new Celayork());


        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menCompetencia1, menCompetencia2);  // Agrega ambos menús a la barra
        vBox = new VBox(mnbPrincipal);
        escena = new Scene(vBox);
        escena.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());

    }

    @Override
    public void start(Stage stage) throws IOException {

        /*new Hilo("Ruta Pinos").start();
        new Hilo("Ruta Laureles").start();
        new Hilo("Ruta San Juan de la Vega").start();
        new Hilo("Ruta Monte Blanco").start();
        new Hilo("Ruta Teneria").start();*/

        Conexion.createConnection();
        CrearUI();
        stage.setTitle("Hola Mundo de Eventos :)");
        stage.setScene(escena);
        stage.show();
        stage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch();
    }

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