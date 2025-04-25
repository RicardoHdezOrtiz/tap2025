package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class MesasDAO {
    private int idMesa;
    private int noMesa;
    private int capacidad;

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNoMesa() {
        return noMesa;
    }

    public void setNoMesa(int noMesa) {
        this.noMesa = noMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void INSERT() {
        String query = "INSERT INTO mesas (noMesa, capacidad) VALUES (?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, noMesa);
            stmt.setInt(2, capacidad);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE mesas SET noMesa = ?, capacidad = ? WHERE idMesa = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, noMesa);
            stmt.setInt(2, capacidad);
            stmt.setInt(3, idMesa);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM mesas WHERE idMesa = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idMesa);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MesasDAO> SELECT() {
        ObservableList<MesasDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM mesas";
        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                MesasDAO mesa = new MesasDAO();
                mesa.setIdMesa(res.getInt("idMesa"));
                mesa.setNoMesa(res.getInt("noMesa"));
                mesa.setCapacidad(res.getInt("capacidad"));
                lista.add(mesa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}