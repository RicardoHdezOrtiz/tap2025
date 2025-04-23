package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class MesasDAO {
    private int idMesa;
    private String numeroMesa;
    private String capacidad;

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public void INSERT(){
        String query = "INSERT INTO mesas(numeroMesa, capacidad) VALUES('" + numeroMesa + "', '" + capacidad + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE(){
        String query = "UPDATE mesas SET numeroMesa = '" + numeroMesa + "', capacidad = '" + capacidad + "' WHERE idMesa = " + idMesa;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM mesas WHERE idMesa = " + idMesa;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MesasDAO> SELECT(){
        ObservableList<MesasDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM mesas";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                MesasDAO mesa = new MesasDAO();
                mesa.setIdMesa(res.getInt("idMesa"));
                mesa.setNumeroMesa(res.getString("numeroMesa"));
                mesa.setCapacidad(res.getString("capacidad"));
                lista.add(mesa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}