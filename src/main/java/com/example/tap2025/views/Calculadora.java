package com.example.tap2025.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Calculadora extends Stage {

    private Scene escena;
    private TextField txtDisplay;
    private VBox vBox;
    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;
    // Definición de teclas: "C" para limpiar y "=" para evaluar
    private String strTeclas[] = {"7", "8", "9", "+", "4", "5", "6", "/", "1", "2", "3", "-", ".", "=", "0", "C"};

    public Calculadora() {
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();
    }

    public void CrearUI() {
        CrearKeyboard();
        txtDisplay = new TextField("0");
        txtDisplay.setEditable(false);
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);
        vBox = new VBox(txtDisplay, gdpTeclado);
        vBox.getStyleClass().add("root");
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        escena = new Scene(vBox, 200, 200);
        escena.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        escena.getStylesheets().add(getClass().getResource("/styles/calcu.css").toExternalForm());
    }

    public void CrearKeyboard() {
        arBtnTeclado = new Button[4][4];
        gdpTeclado = new GridPane();
        gdpTeclado.setHgap(5);
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBtnTeclado[i][j] = new Button(strTeclas[pos]);
                final int finalPos = pos;
                arBtnTeclado[i][j].setOnAction(e -> EventoTeclado(strTeclas[finalPos]));
                arBtnTeclado[i][j].setPrefSize(50, 50);
                gdpTeclado.add(arBtnTeclado[i][j], j, i);
                pos++;
            }
        }
    }

    private void EventoTeclado(String tecla) {
        if (tecla.equals("C")) {
            clearDisplay();
        } else if (tecla.equals("=")) {
            String resultado = evaluateExpression(txtDisplay.getText());
            txtDisplay.setText(resultado);
        } else {
            appendDigit(tecla);
        }
    }

    private void appendDigit(String digito) {
        String actual = txtDisplay.getText();
        // Si el display solo tiene un "0", lo reemplazamos
        if (actual.equals("0") && !digito.equals(".")) {
            txtDisplay.setText(digito);
        } else {
            txtDisplay.appendText(digito);
        }
    }

    private void clearDisplay() {
        txtDisplay.setText("0");
    }

    // Evalúa la expresión validando primero la entrada
    private String evaluateExpression(String expresion) {
        // Eliminar espacios en blanco
        expresion = expresion.replaceAll("\\s+", "");

        // Validación básica de la expresión
        if (expresion.isEmpty() || !expresion.matches("[0-9+\\-*/.]+") ||
                expresion.matches(".*[+\\-*/]{2,}.*") ||
                expresion.matches(".*[+\\-*/]$.*")) { // No permitir operadores al final
            return "Error";
        }

        // Evitar división por 0
        if (expresion.contains("/0") || expresion.contains("0/")) {
            return "Error";
        }

        try {
            double resultado = simpleEvaluator(expresion);
            return String.valueOf(resultado);
        } catch (Exception e) {
            return "Error";
        }
    }

    // Método auxiliar para evaluar la expresión
    private double simpleEvaluator(String expresion) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            Object result = engine.eval(expresion);
            return Double.parseDouble(result.toString());
        } catch (ScriptException e) {
            System.err.println("Error al evaluar la expresión: " + e.getMessage());
            return 0.0;
        }
    }
}