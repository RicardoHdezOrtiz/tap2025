package com.example.tap2025.views;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class VentasRestaurantes extends Stage {

    private Panel pnlRestaurante;

    private Scene escena;

    public  VentasRestaurantes() {
        CrearUI();
        this.setTitle("Fondita Doña lupe");
        this.setScene(escena);
        this.show();
    }

    void CrearUI() {
        pnlRestaurante = new Panel("Tacos Inge");
        pnlRestaurante.getStyleClass().add("panel-primary");
        escena = new Scene(pnlRestaurante,300,200);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }
}

