package com.example.tap2025.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rompecabezas extends Stage {
    private GridPane gridRompecabezas;  // Contenedor para las piezas del rompecabezas
    private Button[][] piezas;  // Arreglo bidimensional que contiene las piezas como botones
    private int size = 3;  // Tamaño inicial del rompecabezas (3x3)
    private Label temporizadorLabel;  // Etiqueta para mostrar el tiempo transcurrido
    private int tiempoTranscurrido;  // Variable para almacenar el tiempo transcurrido
    private Timeline temporizador;  // Línea de tiempo para actualizar el temporizador

    // Constructor que inicializa la interfaz de usuario
    public Rompecabezas() {
        // Botones para diferentes tamaños de rompecabezas y acciones
        Button btn3x3 = new Button("3x3");
        Button btn4x4 = new Button("4x4");
        Button btn5x5 = new Button("5x5");
        Button btnReiniciar = new Button("Reiniciar");
        Button btnVerTiempos = new Button("Ver Tiempos");
        Button btnTerminado = new Button("Terminado");

        // Configuración de los eventos de los botones
        btn3x3.setOnAction(e -> crearRompecabezas(3));  // Cambiar a rompecabezas 3x3
        btn4x4.setOnAction(e -> crearRompecabezas(4));  // Cambiar a rompecabezas 4x4
        btn5x5.setOnAction(e -> crearRompecabezas(5));  // Cambiar a rompecabezas 5x5
        btnReiniciar.setOnAction(e -> reiniciarRompecabezas());  // Reiniciar el rompecabezas
        btnVerTiempos.setOnAction(e -> mostrarVentanaTiempos());  // Ver los tiempos registrados
        btnTerminado.setOnAction(e -> finalizarRompecabezas());  // Finalizar el juego

        // Alineación y espaciado de los botones en un contenedor horizontal
        HBox hBox = new HBox(20, btn3x3, btn4x4, btn5x5, btnReiniciar, btnVerTiempos, btnTerminado);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));

        // Crear el GridPane para organizar las piezas del rompecabezas
        gridRompecabezas = new GridPane();
        gridRompecabezas.setPadding(new Insets(10));
        gridRompecabezas.setHgap(5);
        gridRompecabezas.setVgap(5);
        gridRompecabezas.setAlignment(Pos.CENTER);  // Centrar las piezas dentro del Grid

        // Etiqueta que muestra el tiempo transcurrido
        temporizadorLabel = new Label("Tiempo: 0 segundos");

        // Crear la escena y configurar la ventana
        VBox root = new VBox(20, hBox, temporizadorLabel, gridRompecabezas);
        root.setAlignment(Pos.CENTER);  // Centrar todo el contenido
        Scene scene = new Scene(root, 800, 800);  // Establecer el tamaño de la ventana

        this.setTitle("Rompecabezas");  // Título de la ventana
        this.setScene(scene);  // Asignar la escena
        this.show();  // Mostrar la ventana

        crearRompecabezas(size);  // Crear el rompecabezas inicial
        iniciarTemporizador();  // Iniciar el temporizador
    }

    // crear el rompecabezas con un tamaño específico
    private void crearRompecabezas(int newSize) {
        size = newSize;  // Actualizar el tamaño del rompecabezas
        gridRompecabezas.getChildren().clear();  // Limpiar las piezas actuales
        piezas = new Button[size][size];  // Crear el arreglo para las piezas

        List<String> imagenes = new ArrayList<>();
        int inicio, fin;

        // Determinar el rango de imágenes según el tamaño
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
            throw new IllegalArgumentException("Tamaño no soportado");  // Validación de tamaño
        }

        // Llenar la lista de imágenes
        for (int i = inicio; i <= fin; i++) {
            imagenes.add("images/Rompecabezas-image/" + i + ".png");
        }

        Collections.shuffle(imagenes);  // Mezclar las imágenes aleatoriamente

        // Crear las piezas y añadirlas al GridPane
        for (int fila = 0; fila < size; fila++) {
            for (int columna = 0; columna < size; columna++) {
                final int f = fila;
                final int c = columna;
                Button pieza = new Button();
                pieza.setPrefSize(120, 120);  // Establecer el tamaño de cada pieza

                String imgPath = imagenes.remove(0);  // Obtener la siguiente imagen
                ImageView imgView = new ImageView(new Image(getClass().getResourceAsStream("/" + imgPath)));
                if (imgView.getImage() == null) {
                    System.out.println("Error al cargar la imagen: " + imgPath);  // Manejo de errores al cargar imagen
                }
                imgView.setFitWidth(110);  // Ajustar el tamaño de la imagen
                imgView.setFitHeight(110);
                pieza.setGraphic(imgView);  // Establecer la imagen de la pieza

                // Configuración para el arrastre de las piezas
                pieza.setOnDragDetected(event -> {
                    Dragboard db = pieza.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(f + "," + c);
                    db.setContent(content);
                    event.consume();
                });

                pieza.setOnDragOver(event -> {
                    if (event.getGestureSource() != pieza && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);  // Aceptar el movimiento de la pieza
                    }
                    event.consume();
                });

                pieza.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasString()) {
                        String[] indices = db.getString().split(",");
                        int filaOrigen = Integer.parseInt(indices[0]);
                        int columnaOrigen = Integer.parseInt(indices[1]);

                        // Intercambiar las piezas
                        intercambiarPiezas(filaOrigen, columnaOrigen, f, c);
                        event.setDropCompleted(true);  // Confirmar la acción de arrastre
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume();
                });

                piezas[fila][columna] = pieza;  // Guardar la pieza en el arreglo
                gridRompecabezas.add(pieza, columna, fila);  // Añadir la pieza al GridPane
            }
        }
        tiempoTranscurrido = 0;  // Reiniciar el temporizador
        actualizarTemporizador();  // Actualizar la visualización del tiempo
    }

    // intercambiar dos piezas del rompecabezas
    private void intercambiarPiezas(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {
        // Obtener los botones de las piezas a intercambiar
        Button botonOrigen = piezas[filaOrigen][columnaOrigen];
        Button botonDestino = piezas[filaDestino][columnaDestino];

        // Obtener las imágenes de las piezas
        ImageView imgViewOrigen = (ImageView) botonOrigen.getGraphic();
        ImageView imgViewDestino = (ImageView) botonDestino.getGraphic();

        // Verificar que ambas piezas tengan imágenes válidas antes de intercambiarlas
        if (imgViewOrigen != null && imgViewDestino != null) {
            botonOrigen.setGraphic(imgViewDestino);
            botonDestino.setGraphic(imgViewOrigen);
        }

        // Verificar si el rompecabezas ha sido resuelto
        if (isPuzzleSolved()) {
            mostrarMensajeVictoria();  // Mostrar mensaje de victoria
            temporizador.stop();  // Detener el temporizador
        }
    }

    // verificar si el rompecabezas está resuelto
    private boolean isPuzzleSolved() {
        int contador = 1;  // Contador para comparar las piezas en el orden correcto
        for (int fila = 0; fila < size; fila++) {
            for (int columna = 0; columna < size; columna++) {
                ImageView imgView = (ImageView) piezas[fila][columna].getGraphic();
                if (imgView != null && imgView.getImage() != null) {
                    String imgPath = imgView.getImage().getUrl();
                    if (imgPath != null) {
                        String nombreArchivo = imgPath.substring(imgPath.lastIndexOf('/') + 1);
                        int numeroImagen = Integer.parseInt(nombreArchivo.replace(".png", ""));
                        if (numeroImagen != contador) {
                            return false;  // Si las piezas no están en el orden correcto
                        }
                        contador++;
                    } else {
                        return false;  // Si la pieza no tiene imagen válida
                    }
                } else {
                    return false;  // Si la pieza no tiene imagen
                }
            }
        }
        return true;  // El rompecabezas está resuelto
    }

    // iniciar el temporizador
    private void iniciarTemporizador() {
        temporizador = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempoTranscurrido++;
            actualizarTemporizador();  // Actualizar el tiempo mostrado
        }));
        temporizador.setCycleCount(Timeline.INDEFINITE);  // Repetir indefinidamente
        temporizador.play();  // Iniciar el temporizador
    }

    // actualizar el temporizador en la interfaz
    private void actualizarTemporizador() {
        temporizadorLabel.setText("Tiempo: " + tiempoTranscurrido + " segundos");
    }

    // reiniciar el rompecabezas
    private void reiniciarRompecabezas() {
        crearRompecabezas(size);  // Crear un nuevo rompecabezas
        if (temporizador != null) {
            temporizador.stop();  // Detener el temporizador
        }
        iniciarTemporizador();  // Iniciar el temporizador
    }

    // mostrar un mensaje de victoria
    private void mostrarMensajeVictoria() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Felicidades!");  // Título del mensaje
        alert.setHeaderText(null);
        alert.setContentText("Has resuelto el rompecabezas en " + tiempoTranscurrido + " segundos.");
        alert.showAndWait();  // Mostrar el mensaje
    }

    // finalizar el rompecabezas
    private void finalizarRompecabezas() {
        mostrarMensajeVictoria();  // Mostrar el mensaje de victoria
        guardarTiempoEnArchivo();  // Guardar el tiempo en un archivo
        mostrarVentanaTiempos();  // Mostrar los tiempos registrados
    }

    // guardar el tiempo en un archivo
    private void guardarTiempoEnArchivo() {
        String userHome = System.getProperty("user.home");
        Path descargasPath = Paths.get(userHome, "Downloads", "tiempos.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(descargasPath.toFile(), true))) {
            writer.write("Tiempo: " + tiempoTranscurrido + " segundos\n");  // Escribir el tiempo en el archivo
        } catch (IOException e) {
            e.printStackTrace();  // Manejo de errores al escribir en el archivo
        }
    }

    // mostrar la ventana con los tiempos registrados
    private void mostrarVentanaTiempos() {
        Stage ventanaTiempos = new Stage();
        ventanaTiempos.setTitle("Tiempos Registrados");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);  // Hacer el área de texto no editable
        textArea.setWrapText(true);  // Habilitar el ajuste de texto

        // Leer los tiempos desde el archivo
        String userHome = System.getProperty("user.home");
        Path descargasPath = Paths.get(userHome, "Downloads", "tiempos.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(descargasPath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.appendText(line + "\n");  // Añadir cada línea al área de texto
            }
        } catch (IOException e) {
            textArea.appendText("Error al leer los tiempos.\n");  // Manejo de errores al leer el archivo
        }

        VBox vbox = new VBox(10, new Label("Tiempos registrados:"), textArea);
        Scene scene = new Scene(vbox, 400, 400);
        ventanaTiempos.setScene(scene);
        ventanaTiempos.show();  // Mostrar la ventana con los tiempos
    }
}