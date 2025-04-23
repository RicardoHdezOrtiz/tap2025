package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class BebidasDAO {
    private int idBebida;
    private String nombreBebida;
    private float precio;
    private float costo;
    private int idCategoria;

    public int getIdBebida() {
        return idBebida;
    }

    public void setIdBebida(int idBebida) {
        this.idBebida = idBebida;
    }

    public String getNombreBebida() {
        return nombreBebida;
    }

    public void setNombreBebida(String nombreBebida) {
        this.nombreBebida = nombreBebida;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void INSERT(){
        String query = "INSERT INTO bebidas(nombreBebida, precio, costo, idCategoria) VALUES('" +
                nombreBebida + "'," + precio + "," + costo + "," + idCategoria + ")";
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void UPDATE(){
        String query = "UPDATE bebidas SET nombreBebida = '" + nombreBebida +
                "', precio = " + precio + ", costo = " + costo +
                ", idCategoria = " + idCategoria + " WHERE idBebida = " + idBebida;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM bebidas WHERE idBebida = " + idBebida;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<BebidasDAO> SELECT(){
        ObservableList<BebidasDAO> lista = FXCollections.observableArrayList();
        try{
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bebidas");
            while(rs.next()){
                BebidasDAO b = new BebidasDAO();
                b.setIdBebida(rs.getInt("idBebida"));
                b.setNombreBebida(rs.getString("nombreBebida"));
                b.setPrecio(rs.getFloat("precio"));
                b.setCosto(rs.getFloat("costo"));
                b.setIdCategoria(rs.getInt("idCategoria"));
                lista.add(b);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }
}