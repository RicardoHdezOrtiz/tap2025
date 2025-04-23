package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenDAO {
    private int idOrden;
    private int idCliente;
    private String fecha;
    private float total;
    private int idMesa;
    private int idEmpleado;

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void INSERT() {
        String query = "INSERT INTO ordenes (idCliente, fecha, total, idMesa, idEmpleado) VALUES (" +
                idCliente + ", '" + fecha + "', " + total + ", " + idMesa + ", " + idEmpleado + ")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE ordenes SET idCliente = " + idCliente + ", fecha = '" + fecha +
                "', total = " + total + ", idMesa = " + idMesa + ", idEmpleado = " + idEmpleado +
                " WHERE idOrden = " + idOrden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM ordenes WHERE idOrden = " + idOrden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<OrdenDAO> SELECT() {
        ObservableList<OrdenDAO> lista = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ordenes");
            while (rs.next()) {
                OrdenDAO o = new OrdenDAO();
                o.setIdOrden(rs.getInt("idOrden"));
                o.setIdCliente(rs.getInt("idCliente"));
                o.setFecha(rs.getString("fecha"));
                o.setTotal(rs.getFloat("total"));
                o.setIdMesa(rs.getInt("idMesa"));
                o.setIdEmpleado(rs.getInt("idEmpleado"));
                lista.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}