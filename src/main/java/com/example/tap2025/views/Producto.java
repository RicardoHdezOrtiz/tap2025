package com.example.tap2025.views;

import com.example.tap2025.modelos.ProductoDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Producto extends Stage {

    private TextField txtNombre, txtPrecio, txtImagen, txtIdCategoria;
    private Button btnGuardar, btnEliminar;
    private VBox vBox;
    private Scene escena;
    private TableView<ProductoDAO> tbvProducto;
    private ProductoDAO objProducto;

    public Producto(TableView<ProductoDAO> tbv, ProductoDAO obj) {
        this.tbvProducto = tbv;
        this.objProducto = (obj == null) ? new ProductoDAO() : obj;

        crearUI();

        if (obj != null) {
            // Llenar campos si estamos editando
            txtNombre.setText(obj.getNombreProducto());
            txtPrecio.setText(String.valueOf(obj.getPrecio()));
            txtImagen.setText(obj.getImagen());
            txtIdCategoria.setText(String.valueOf(obj.getIdCategoria()));
        }

        this.setTitle("Registro de Producto");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del producto");

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");

        txtImagen = new TextField();
        txtImagen.setPromptText("Ruta de la imagen");

        txtIdCategoria = new TextField();
        txtIdCategoria.setPromptText("ID de categoría");

        btnGuardar = new Button("Guardar");
        btnEliminar = new Button("Eliminar");

        btnGuardar.setOnAction(e -> guardarProducto());
        btnEliminar.setOnAction(e -> eliminarProducto());

        Separator separador = new Separator();

        // Mostrar o no el botón eliminar según si es nuevo o existente
        if (objProducto.getIdProducto() > 0) {
            vBox = new VBox(10, txtNombre, txtPrecio, txtImagen, txtIdCategoria, separador, btnGuardar, btnEliminar);
        } else {
            vBox = new VBox(10, txtNombre, txtPrecio, txtImagen, txtIdCategoria, separador, btnGuardar);
        }

        vBox.setPadding(new Insets(10));
        escena = new Scene(vBox, 350, 320);
    }

    private void guardarProducto() {
        if (txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty()
                || txtImagen.getText().isEmpty() || txtIdCategoria.getText().isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        try {
            objProducto.setNombreProducto(txtNombre.getText());
            objProducto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            objProducto.setImagen(txtImagen.getText());
            objProducto.setIdCategoria(Integer.parseInt(txtIdCategoria.getText()));

            if (objProducto.getIdProducto() > 0) {
                objProducto.UPDATE();
            } else {
                objProducto.INSERT();
            }

            // Actualizar tabla y cerrar ventana
            tbvProducto.setItems(objProducto.SELECT());
            tbvProducto.refresh();
            this.close();

        } catch (NumberFormatException ex) {
            mostrarAlerta("Precio o ID de categoría tienen formato incorrecto.");
        } catch (Exception ex) {
            mostrarAlerta("Error inesperado: " + ex.getMessage());
        }
    }

    private void eliminarProducto() {
        if (objProducto.getIdProducto() > 0) {
            objProducto.DELETE();

            // Actualizar tabla y cerrar ventana
            tbvProducto.setItems(objProducto.SELECT());
            tbvProducto.refresh();
            this.close();
        } else {
            mostrarAlerta("No se puede eliminar un producto no registrado.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}