package com.example.tap2025.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rompecabezas extends Stage {
    private GridPane gridRompecabezas;
    private Button[][] piezas;
    private int size = 3;
    private Label temporizadorLabel;
    private int tiempoTranscurrido;
    private Timeline temporizador;
    private boolean victoriaMostrada = false;

    public Rompecabezas() {
        Button btn3x3 = new Button("3x3");
        Button btn4x4 = new Button("4x4");
        Button btn5x5 = new Button("5x5");
        Button btnReiniciar = new Button("Reiniciar");
        Button btnVerTiempos = new Button("Ver Tiempos");
        Button btnTerminado = new Button("Terminado");

        btn3x3.setOnAction(e -> crearRompecabezas(3));
        btn4x4.setOnAction(e -> crearRompecabezas(4));
        btn5x5.setOnAction(e -> crearRompecabezas(5));
        btnReiniciar.setOnAction(e -> reiniciarRompecabezas());
        btnVerTiempos.setOnAction(e -> mostrarVentanaTiempos());
        btnTerminado.setOnAction(e -> finalizarRompecabezas());

        HBox hBox = new HBox(20, btn3x3, btn4x4, btn5x5, btnReiniciar, btnVerTiempos, btnTerminado);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));

        gridRompecabezas = new GridPane();
        gridRompecabezas.setPadding(new Insets(10));
        gridRompecabezas.setHgap(5);
        gridRompecabezas.setVgap(5);
        gridRompecabezas.setAlignment(Pos.CENTER);

        temporizadorLabel = new Label("Tiempo: 0 segundos");

        VBox root = new VBox(20, hBox, temporizadorLabel, gridRompecabezas);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 800, 800);

        this.setTitle("Rompecabezas");
        this.setScene(scene);
        this.show();

        crearRompecabezas(size);
        iniciarTemporizador();
    }

    private void crearRompecabezas(int newSize) {
        size = newSize;
        gridRompecabezas.getChildren().clear();
        piezas = new Button[size][size];

        List<String> nombresArchivos = new ArrayList<>();
        int inicio, fin;

        if (size == 3) {
            inicio = 1;
            fin = 9;
        } else if (size == 4) {
            inicio = 10;
            fin = 25;
        } else if (size == 5) {
            inicio = 26;
            fin = 50;
        } else {
            throw new IllegalArgumentException("Tamaño no soportado");
        }

        for (int i = inicio; i <= fin; i++) {
            nombresArchivos.add(i + ".png");
        }

        Collections.shuffle(nombresArchivos);

        for (int fila = 0; fila < size; fila++) {
            for (int columna = 0; columna < size; columna++) {
                final int f = fila;
                final int c = columna;
                Button pieza = new Button();
                pieza.setPrefSize(120, 120);

                String nombreArchivo = nombresArchivos.remove(0);
                String imgPath = "images/Rompecabezas/" + nombreArchivo;
                ImageView imgView = new ImageView();
                try {
                    Image image = new Image(getClass().getResourceAsStream("/" + imgPath));
                    if (image.isError()) {
                        throw new IOException("Error al cargar la imagen: " + imgPath + " - " + image.getException());
                    }
                    imgView.setImage(image);
                } catch (IOException e) {
                    System.out.println("Error al cargar la imagen: " + imgPath);
                }

                imgView.setFitWidth(110);
                imgView.setFitHeight(110);
                pieza.setGraphic(imgView);

                // Asignar el nombre del archivo como userData
                pieza.setUserData(nombreArchivo);

                pieza.setOnDragDetected(event -> {
                    Dragboard db = pieza.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(f + "," + c);
                    db.setContent(content);
                    event.consume();
                });

                pieza.setOnDragOver(event -> {
                    if (event.getGestureSource() != pieza && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                pieza.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasString()) {
                        String[] indices = db.getString().split(",");
                        int filaOrigen = Integer.parseInt(indices[0]);
                        int columnaOrigen = Integer.parseInt(indices[1]);

                        intercambiarPiezas(filaOrigen, columnaOrigen, f, c);
                        event.setDropCompleted(true);

                        if (rompecabezasResuelto()) {
                            // No mostrar el mensaje de victoria aquí, solo revisar
                        }
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume();
                });

                piezas[fila][columna] = pieza;
                gridRompecabezas.add(pieza, columna, fila);
            }
        }
        tiempoTranscurrido = 0;
        actualizarTemporizador();
        victoriaMostrada = false; // Resetear estado de victoria
    }

    private boolean rompecabezasResuelto() {
        int contadorInicio = (size == 3) ? 1 : (size == 4) ? 10 : 26;
        boolean resuelto = true;
        int contador = contadorInicio;

        for (int fila = 0; fila < size; fila++) {
            for (int columna = 0; columna < size; columna++) {
                Button pieza = piezas[fila][columna];
                String nombreArchivoActual = (String) pieza.getUserData();
                String nombreArchivoEsperado = contador + ".png";

                if (!nombreArchivoActual.equals(nombreArchivoEsperado)) {
                    resuelto = false;
                    break;
                }
                contador++;
            }
            if (!resuelto) {
                break;
            }
        }

        if (resuelto && !victoriaMostrada) {
            victoriaMostrada = true;
            mostrarMensajeVictoria();
            if (temporizador != null) {
                temporizador.stop();
            }
            guardarTiempoEnArchivo();
            return true;
        }
        return false;
    }

    private void mostrarMensajeVictoria() {
        System.out.println("¡Victoria!");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Felicidades!");
        alert.setHeaderText(null);
        alert.setContentText("Has resuelto el rompecabezas en " + tiempoTranscurrido + " segundos.");
        alert.showAndWait();
    }

    private void intercambiarPiezas(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {
        Button botonOrigen = piezas[filaOrigen][columnaOrigen];
        Button botonDestino = piezas[filaDestino][columnaDestino];

        ImageView imgViewOrigen = (ImageView) botonOrigen.getGraphic();
        ImageView imgViewDestino = (ImageView) botonDestino.getGraphic();

        Object userDataOrigen = botonOrigen.getUserData();
        Object userDataDestino = botonDestino.getUserData();

        botonOrigen.setGraphic(imgViewDestino);
        botonOrigen.setUserData(userDataDestino);

        botonDestino.setGraphic(imgViewOrigen);
        botonDestino.setUserData(userDataOrigen);
    }

    private void iniciarTemporizador() {
        temporizador = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempoTranscurrido++;
            actualizarTemporizador();
        }));
        temporizador.setCycleCount(Timeline.INDEFINITE);
        temporizador.play();
    }

    private void actualizarTemporizador() {
        temporizadorLabel.setText("Tiempo: " + tiempoTranscurrido + " segundos");
    }

    private void reiniciarRompecabezas() {
        crearRompecabezas(size);
        if (temporizador != null) {
            temporizador.stop();
        }
        iniciarTemporizador();
    }

    private void finalizarRompecabezas() {
        mostrarMensajeVictoria();
        guardarTiempoEnArchivo();
        mostrarVentanaTiempos();
    }

    private void guardarTiempoEnArchivo() {
        String userHome = System.getProperty("user.home");
        Path descargasPath = Paths.get(userHome, "Downloads", "tiempos.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(descargasPath.toFile(), true))) {
            writer.write("Tiempo: " + tiempoTranscurrido + " segundos\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarVentanaTiempos() {
        Stage ventanaTiempos = new Stage();
        ventanaTiempos.setTitle("Tiempos Registrados");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        String userHome = System.getProperty("user.home");
        Path descargasPath = Paths.get(userHome, "Downloads", "tiempos.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(descargasPath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.appendText(line + "\n");
            }
        } catch (IOException e) {
            textArea.appendText("Error al leer los tiempos.\n");
        }

        VBox vbox = new VBox(10, new Label("Tiempos registrados:"), textArea);
        Scene scene = new Scene(vbox, 400, 400);
        ventanaTiempos.setScene(scene);
        ventanaTiempos.show();
    }
}