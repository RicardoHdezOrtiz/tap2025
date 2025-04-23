package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class CategoriaDAO {
    private int noCategoria;
    private String nomCategoria;
    private String descripcionCategoria;

    public int getNoCategoria() {
        return noCategoria;
    }

    public void setNoCategoria(int noCategoria) {
        this.noCategoria = noCategoria;
    }

    public String getNomCategoria() {
        return nomCategoria;
    }

    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public void INSERT() {
        String query = "INSERT INTO categorias(nomCategoria, descripcionCategoria) VALUES('" +
                nomCategoria + "', '" + descripcionCategoria + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE categorias SET nomCategoria = '" + nomCategoria +
                "', descripcionCategoria = '" + descripcionCategoria +
                "' WHERE noCategoria = " + noCategoria;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM categorias WHERE noCategoria = " + noCategoria;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<CategoriaDAO> SELECT() {
        ObservableList<CategoriaDAO> lista = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categorias");
            while (rs.next()) {
                CategoriaDAO c = new CategoriaDAO();
                c.setNoCategoria(rs.getInt("noCategoria"));
                c.setNomCategoria(rs.getString("nomCategoria"));
                c.setDescripcionCategoria(rs.getString("descripcionCategoria"));
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}