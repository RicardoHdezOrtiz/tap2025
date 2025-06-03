package com.example.tap2025.views;

import com.example.tap2025.modelos.ProductoDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;

public class ListaProducto {

    private TableView<ProductoDAO> tbvProducto;
    private VBox vBox;

    public ListaProducto() {
        tbvProducto = new TableView<>();
        ProductoDAO objProducto = new ProductoDAO();

        TableColumn<ProductoDAO, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));

        TableColumn<ProductoDAO, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<ProductoDAO, String> colImagen = new TableColumn<>("Imagen");
        colImagen.setCellValueFactory(new PropertyValueFactory<>("imagen"));

        colImagen.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ProductoDAO, String> call(TableColumn<ProductoDAO, String> param) {
                return new TableCell<>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String rutaImagen, boolean empty) {
                        super.updateItem(rutaImagen, empty);
                        if (empty || rutaImagen == null || rutaImagen.isEmpty()) {
                            setGraphic(null);
                        } else {
                            try {
                                Image img = new Image(getClass().getResourceAsStream(rutaImagen), 80, 80, true, true);
                                imageView.setImage(img);
                                setGraphic(imageView);
                            } catch (Exception e) {
                                System.out.println("Error cargando imagen: " + rutaImagen);
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });

        TableColumn<ProductoDAO, Integer> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        // Columna de acciones (Eliminar)
        TableColumn<ProductoDAO, Void> colAcciones = new TableColumn<>("Acciones");
        Callback<TableColumn<ProductoDAO, Void>, TableCell<ProductoDAO, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ProductoDAO, Void> call(final TableColumn<ProductoDAO, Void> param) {
                return new TableCell<>() {
                    private final Button btnEliminar = new Button("Eliminar");

                    {
                        btnEliminar.setOnAction(e -> {
                            ProductoDAO producto = getTableView().getItems().get(getIndex());
                            producto.DELETE(); // Elimina de BD
                            tbvProducto.getItems().remove(producto); // Elimina de la tabla
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnEliminar);
                        }
                    }
                };
            }
        };
        colAcciones.setCellFactory(cellFactory);

        tbvProducto.getColumns().addAll(colNombre, colPrecio, colImagen, colCategoria, colAcciones);
        tbvProducto.setItems(FXCollections.observableArrayList(objProducto.SELECT()));

        Button btnAgregar = new Button("Agregar Producto");
        btnAgregar.setOnAction(e -> new Producto(tbvProducto, null)); // Abre formulario

        ScrollPane submenuCategorias = crearVistaSubmenuCategorias();

        vBox = new VBox(15, btnAgregar, tbvProducto, submenuCategorias);
        vBox.setPadding(new Insets(10));
    }

    public VBox getVista() {
        return vBox;
    }

    private ScrollPane crearVistaSubmenuCategorias() {
        VBox contenedor = new VBox(30);
        contenedor.setPadding(new Insets(30));
        contenedor.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ccc;");

        List<ProductoDAO> productos = new ProductoDAO().SELECT();

        List<ProductoDAO> alimentos = productos.stream()
                .filter(p -> p.getIdCategoria() == 1).collect(Collectors.toList());
        List<ProductoDAO> bebidas = productos.stream()
                .filter(p -> p.getIdCategoria() == 2).collect(Collectors.toList());
        List<ProductoDAO> postres = productos.stream()
                .filter(p -> p.getIdCategoria() == 3).collect(Collectors.toList());

        contenedor.getChildren().addAll(
                crearCeldaCategoria("Alimentos", alimentos),
                crearCeldaCategoria("Bebidas", bebidas),
                crearCeldaCategoria("Postres", postres)
        );

        ScrollPane scrollPane = new ScrollPane(contenedor);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(300);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return scrollPane;
    }

    private VBox crearCeldaCategoria(String titulo, List<ProductoDAO> productos) {
        VBox celda = new VBox(10);
        celda.setPadding(new Insets(15));
        celda.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: #fdfdfd;");
        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox productosVBox = new VBox(8);
        productosVBox.setAlignment(Pos.CENTER_LEFT);

        for (ProductoDAO producto : productos) {
            VBox card = new VBox(5);
            card.setPadding(new Insets(5));

            Label lblNombre = new Label(producto.getNombreProducto());
            lblNombre.setStyle("-fx-font-weight: bold;");

            Label lblPrecio = new Label("Precio: $" + String.format("%.2f", producto.getPrecio()));

            ImageView imgView = new ImageView();
            try {
                Image img = new Image(getClass().getResourceAsStream(producto.getImagen()), 60, 60, true, true);
                imgView.setImage(img);
            } catch (Exception e) {
                System.out.println("No se encontró imagen: " + producto.getImagen());
            }

            card.getChildren().addAll(imgView, lblNombre, lblPrecio);
            productosVBox.getChildren().add(card);
        }

        celda.getChildren().addAll(lblTitulo, new Separator(), productosVBox);
        return celda;
    }
}