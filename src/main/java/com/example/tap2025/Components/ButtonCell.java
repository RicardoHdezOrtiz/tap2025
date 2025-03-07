package com.example.tap2025.Components;

import com.example.tap2025.modelos.ClientesDAO;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ButtonCell extends TableCell<ClientesDAO,String> {
    Button btnCelda;

    public ButtonCell(){
        btnCelda = new Button("Editar");
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if( !b ){
            this.setGraphic(btnCelda);
        }
    }
}