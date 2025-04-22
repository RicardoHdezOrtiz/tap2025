package com.example.tap2025.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {

    private Scene escena;
    private TextField txtDisplay;
    private VBox vBox;
    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;

    private String strTeclas[] = {"C", "", "", "",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            ".", "0", "=", "+"};

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
        escena.getStylesheets().add(getClass().getResource("/styles/calcu.css").toExternalForm());
    }

    public void CrearKeyboard() {
        arBtnTeclado = new Button[5][4];
        gdpTeclado = new GridPane();
        gdpTeclado.setHgap(4);
        gdpTeclado.setVgap(4);
        int pos = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                String valorTecla = strTeclas[pos];
                Button btn = new Button(valorTecla);

                if (valorTecla.isEmpty()) {
                    btn.setDisable(true);
                } else {
                    final int finalPos = pos;
                    btn.setOnAction(e -> EventoTeclado(strTeclas[finalPos]));
                }

                btn.setPrefSize(50, 50);
                gdpTeclado.add(btn, j, i);
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

        if (actual.equals("0") && !digito.equals(".")) {
            txtDisplay.setText(digito);
        } else if (actual.length() < 9) {
            if (isValidInput(actual, digito)) {
                txtDisplay.appendText(digito);
            }
        }
    }

    private boolean isValidInput(String actual, String nuevo) {
        if (nuevo.matches("[+\\-*/]")) {
            if (actual.isEmpty()) {
                return nuevo.equals("-"); // Permitir '-' solo al inicio
            }
            return !actual.matches(".*[+\\-*/]$"); // No permitir operadores seguidos
        }
        return true; // Siempre permitir números y puntos decimales
    }

    private void clearDisplay() {
        txtDisplay.setText("0");
    }

    private String evaluateExpression(String expresion) {
        try {
            if (expresion.contains("/0")) {
                return "Error";
            }
            double resultado = calcularExpresion(expresion);
            return String.valueOf(resultado);
        } catch (Exception e) {
            return "Error";
        }
    }

    private double calcularExpresion(String expresion) {
        expresion = expresion.replaceAll("\\s+", ""); // Eliminar espacios innecesarios

        if (!expresion.matches("[-]?[0-9]+(?:[+\\-*/][-]?[0-9]+)*")) {
            throw new IllegalArgumentException("Expresión no válida");
        }

        return evaluar(expresion);
    }

    private double evaluar(String expresion) {
        try {
            String[] tokens = expresion.split("(?<=[+\\-*/])|(?=[+\\-*/])");

            int index = 0;
            double resultado = 0;
            if (tokens[0].equals("-")) {
                resultado = -Double.parseDouble(tokens[1]);
                index = 2;
            } else {
                resultado = Double.parseDouble(tokens[0]);
                index = 1;
            }

            for (; index < tokens.length; index += 2) {
                String operador = tokens[index];
                double numero = Double.parseDouble(tokens[index + 1]);

                switch (operador) {
                    case "+": resultado += numero; break;
                    case "-": resultado -= numero; break;
                    case "*": resultado *= numero; break;
                    case "/":
                        if (numero == 0) throw new ArithmeticException("División por cero");
                        resultado /= numero;
                        break;
                }
            }
            return resultado;
        } catch (Exception e) {
            throw new IllegalArgumentException("Expresión no válida");
        }
    }
}