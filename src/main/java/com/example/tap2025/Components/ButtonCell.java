package com.example.tap2025.Components;

import com.example.tap2025.modelos.*;
import com.example.tap2025.views.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

@SuppressWarnings("unchecked")
public class ButtonCell<T> extends TableCell<T, String> {

    private final Button btnAccion;
    private final HBox hBox;

    public ButtonCell(String tipo) {
        btnAccion = new Button(tipo);
        hBox = new HBox(btnAccion);

        btnAccion.setOnAction(event -> {
            T obj = getTableView().getItems().get(getIndex());

            if (obj instanceof ClientesDAO cliente) {
                TableView<ClientesDAO> table = (TableView<ClientesDAO>) getTableView();
                if (tipo.equals("Eliminar")) {
                    cliente.DELETE();
                    table.setItems(cliente.SELECT());
                    table.refresh();
                } else if (tipo.equals("Editar")) {
                    new Cliente(table, cliente);
                }

            } else if (obj instanceof EmpleadoDAO empleado) {
                TableView<EmpleadoDAO> table = (TableView<EmpleadoDAO>) getTableView();
                if (tipo.equals("Eliminar")) {
                    empleado.DELETE();
                    table.setItems(empleado.SELECT());
                    table.refresh();
                } else if (tipo.equals("Editar")) {
                    new Empleado(table, empleado);
                }

            } else if (obj instanceof ProveedoresDAO proveedor) {
                TableView<ProveedoresDAO> table = (TableView<ProveedoresDAO>) getTableView();
                if (tipo.equals("Eliminar")) {
                    proveedor.DELETE();
                    table.setItems(proveedor.SELECT());
                    table.refresh();
                } else if (tipo.equals("Editar")) {
                    new Proveedores(table, proveedor);
                }

            } else if (obj instanceof MesasDAO mesa) {
                TableView<MesasDAO> table = (TableView<MesasDAO>) getTableView();
                if (tipo.equals("Eliminar")) {
                    mesa.DELETE();
                    table.setItems(mesa.SELECT());
                    table.refresh();
                } else if (tipo.equals("Editar")) {
                    new Mesa(table, mesa);
                }

            } else if (obj instanceof BebidasDAO bebida) {
                TableView<BebidasDAO> table = (TableView<BebidasDAO>) getTableView();
                if (tipo.equals("Eliminar")) {
                    bebida.DELETE();
                    table.setItems(bebida.SELECT());
                    table.refresh();
                } else if (tipo.equals("Editar")) {
                    new Bebidas(table, bebida);
                }

            } else if (obj instanceof ProductoDAO producto) {
                // Corregido aquí el casteo, para que coincida con ProductoDAO
                TableView<ProductoDAO> table = (TableView<ProductoDAO>) getTableView();
                if (tipo.equals("Eliminar")) {
                    producto.DELETE();
                    table.setItems(producto.SELECT());
                    table.refresh();
                } else if (tipo.equals("Editar")) {
                    new Producto(table, producto);
                }

            } else if (obj instanceof OrdenDAO orden) {
                TableView<OrdenDAO> table = (TableView<OrdenDAO>) getTableView();
                if (tipo.equals("Eliminar")) {
                    orden.DELETE();
                    table.setItems(orden.SELECT());
                    table.refresh();
                } else if (tipo.equals("Editar")) {
                    new Orden(table, orden);
                }
            }
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(empty ? null : hBox);
    }
}