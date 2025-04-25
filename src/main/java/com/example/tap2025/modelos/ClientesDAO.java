package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientesDAO {

    private int idCte;
    private String nomCte;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String direccion;
    private String emailCte;
    private String telCte;

    public int getIdCte() {
        return idCte;
    }

    public void setIdCte(int idCte) {
        this.idCte = idCte;
    }

    public String getNomCte() {
        return nomCte;
    }

    public void setNomCte(String nomCte) {
        this.nomCte = nomCte;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmailCte() {
        return emailCte;
    }

    public void setEmailCte(String emailCte) {
        this.emailCte = emailCte;
    }

    public String getTelCte() {
        return telCte;
    }

    public void setTelCte(String telCte) {
        this.telCte = telCte;
    }

    public void INSERT() {
        String query = "INSERT INTO clientes(nomCliente, apellidoPaterno, apellidoMaterno, direccion, emailCliente, telCliente) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setString(1, nomCte);
            stmt.setString(2, apellidoPaterno);
            stmt.setString(3, apellidoMaterno);
            stmt.setString(4, direccion);
            stmt.setString(5, emailCte);
            stmt.setString(6, telCte);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE clientes SET nomCliente = ?, apellidoPaterno = ?, apellidoMaterno = ?, direccion = ?, emailCliente = ?, telCliente = ? WHERE idCte = ?";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setString(1, nomCte);
            stmt.setString(2, apellidoPaterno);
            stmt.setString(3, apellidoMaterno);
            stmt.setString(4, direccion);
            stmt.setString(5, emailCte);
            stmt.setString(6, telCte);
            stmt.setInt(7, idCte);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM clientes WHERE idCte = ?";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setInt(1, idCte);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ClientesDAO> SELECT() {
        String query = "SELECT * FROM clientes";
        ObservableList<ClientesDAO> listaC = FXCollections.observableArrayList();
        ClientesDAO objC;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objC = new ClientesDAO();
                objC.setIdCte(res.getInt("idCte"));
                objC.setNomCte(res.getString("nomCliente"));
                objC.setApellidoPaterno(res.getString("apellidoPaterno"));
                objC.setApellidoMaterno(res.getString("apellidoMaterno"));
                objC.setDireccion(res.getString("direccion"));
                objC.setEmailCte(res.getString("emailCliente"));
                objC.setTelCte(res.getString("telCliente"));
                listaC.add(objC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaC;
    }
}