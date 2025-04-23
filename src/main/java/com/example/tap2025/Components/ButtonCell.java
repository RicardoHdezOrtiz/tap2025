package com.example.tap2025.Components;

import com.example.tap2025.modelos.ClientesDAO;
import com.example.tap2025.modelos.EmpleadoDAO;
import com.example.tap2025.modelos.ProveedoresDAO;
import com.example.tap2025.modelos.MesasDAO;
import com.example.tap2025.modelos.BebidasDAO;
import com.example.tap2025.modelos.CategoriaDAO;
import com.example.tap2025.modelos.OrdenDAO;
import com.example.tap2025.views.Cliente;
import com.example.tap2025.views.Empleado;
import com.example.tap2025.views.Proveedores;
import com.example.tap2025.views.Mesa;
import com.example.tap2025.views.Bebidas;
import com.example.tap2025.views.Categoria;
import com.example.tap2025.views.Orden;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

@SuppressWarnings("unchecked") // Opcional: suprime advertencias de casting
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
                } else if (tipo.equals("Editar")) {
                    new Cliente(table, cliente);
                }

            } else if (obj instanceof EmpleadoDAO empleado) {
                TableView<EmpleadoDAO> table = (TableView<EmpleadoDAO>) getTableView();
                if (tipo.equals("Eliminar")) {
                    empleado.DELETE();
                    table.setItems(empleado.SELECT());
                } else if (tipo.equals("Editar")) {
                    new Empleado(table, empleado);
                }

            } else if (obj instanceof ProveedoresDAO proveedor) {
                TableView<ProveedoresDAO> table = (TableView<ProveedoresDAO>) getTableView();
                if (tipo.equals("Eliminar")) {
                    proveedor.DELETE();
                    table.setItems(proveedor.SELECT());
                } else if (tipo.equals("Editar")) {
                    new Proveedores(table, proveedor);
                }

            }else if (obj instanceof MesasDAO mesa) {
                if (tipo.equals("Eliminar")) {
                    mesa.DELETE();
                    ((TableView<MesasDAO>) getTableView()).setItems(mesa.SELECT());
                } else if (tipo.equals("Editar")) {
                    new Mesa((TableView<MesasDAO>) getTableView(), mesa);
                }

            }else if (obj instanceof BebidasDAO bebida) {
                if (tipo.equals("Eliminar")) {
                    bebida.DELETE();
                    ((TableView<BebidasDAO>) getTableView()).setItems(bebida.SELECT());
                } else if (tipo.equals("Editar")) {
                    new Bebidas((TableView<BebidasDAO>) getTableView(), bebida);
                }

            }else if (obj instanceof CategoriaDAO categoria) {
                if (tipo.equals("Eliminar")) {
                    categoria.DELETE();
                    ((TableView<CategoriaDAO>) getTableView()).setItems(categoria.SELECT());
                } else if (tipo.equals("Editar")) {
                    new Categoria((TableView<CategoriaDAO>) getTableView(), categoria);
                }

            }else if (obj instanceof OrdenDAO orden) {
                if (tipo.equals("Eliminar")) {
                    orden.DELETE();
                    ((TableView<OrdenDAO>) getTableView()).setItems(orden.SELECT());
                } else if (tipo.equals("Editar")) {
                    new Orden((TableView<OrdenDAO>) getTableView(), orden);
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