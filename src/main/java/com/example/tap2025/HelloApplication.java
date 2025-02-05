package com.example.tap2025;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    //private Button btnSaludo, btnSaludo2, btnSaludo3;
    private VBox vBox;

    private MenuBar mnbPrincipal;

    private Menu menCompetencia1, menCompetencia2;

    private MenuItem mitCalculadora;

    private Scene escena;

    void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        menCompetencia1 = new Menu("Competencia1");
        menCompetencia2.getItems().addAll(mitCalculadora);
        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menCompetencia1);
    }

    @Override
    public void start(Stage stage) throws IOException {
        /*btnSaludo = new Button("bienvenido amiguito ");
        btnSaludo.setOnAction(event -> clickEvent());
        btnSaludo2 = new Button("bienvenido amiguito ");
        btnSaludo2.setOnAction(event -> clickEvent());
        btnSaludo3 = new Button("bienvenido amiguito ");
        vBox = new VBox(btnSaludo, btnSaludo2, btnSaludo3);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 0,0,0));*/


        vBox = new VBox();//EMPIEZA SEGUNDO CODIGO O EXTENCION
        stage.setTitle("Hola Mundo de Eventos:) ");
        stage.setScene(new Scene(vBox, 200, 200));
        stage.show();
        stage.setMaximized(true);
    }

    public static void main(String[] args) {launch();}

        void clickEvent(){
            System.out.println("Evento desde un metodo :)");
        }
    }