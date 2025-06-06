package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class ReservacionDAO {

    private int idReservacion;
    private String nombreCliente;
    private Timestamp fechaHora; // Usamos Timestamp para fecha y hora
    private int numPersonas;
    private int numMesa;  // CORREGIDO: es numMesa, no idMesa

    // Getters y setters
    public int getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }

    public int getNumMesa() {  // CORREGIDO: es numMesa
        return numMesa;
    }

    public void setNumMesa(int numMesa) {  // CORREGIDO: es numMesa
        this.numMesa = numMesa;
    }

    // Insertar nueva reservación
    public void INSERT() {
        String query = "INSERT INTO reservaciones(nombreCliente, fechaHora, numPersonas, numMesa) VALUES (?, ?, ?, ?)";  // CORREGIDO: numMesa
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setString(1, nombreCliente);
            stmt.setTimestamp(2, fechaHora);
            stmt.setInt(3, numPersonas);
            stmt.setInt(4, numMesa);  // CORREGIDO: numMesa
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar reservación existente
    public void UPDATE() {
        String query = "UPDATE reservaciones SET nombreCliente = ?, fechaHora = ?, numPersonas = ?, numMesa = ? WHERE idReservacion = ?";  // CORREGIDO: numMesa
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setString(1, nombreCliente);
            stmt.setTimestamp(2, fechaHora);
            stmt.setInt(3, numPersonas);
            stmt.setInt(4, numMesa);  // CORREGIDO: numMesa
            stmt.setInt(5, idReservacion);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar reservación
    public void DELETE() {
        String query = "DELETE FROM reservaciones WHERE idReservacion = ?";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setInt(1, idReservacion);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Obtener lista de todas las reservaciones
    public ObservableList<ReservacionDAO> SELECT() {
        String query = "SELECT * FROM reservaciones";
        ObservableList<ReservacionDAO> listaR = FXCollections.observableArrayList();
        ReservacionDAO objR;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objR = new ReservacionDAO();
                objR.setIdReservacion(res.getInt("idReservacion"));
                objR.setNombreCliente(res.getString("nombreCliente"));
                objR.setFechaHora(res.getTimestamp("fechaHora"));
                objR.setNumPersonas(res.getInt("numPersonas"));
                objR.setNumMesa(res.getInt("numMesa"));  // CORREGIDO: numMesa
                listaR.add(objR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaR;
    }
}