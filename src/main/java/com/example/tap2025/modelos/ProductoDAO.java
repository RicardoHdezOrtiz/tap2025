package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductoDAO {
    private int idProducto, idCategoria;
    private String nombreProducto, imagen;
    private double precio;

    // Getters y Setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    // INSERT
    public void INSERT() {
        String query = "INSERT INTO Producto(nombreProducto, precio, imagen, idCategoria) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = Conexion.connection.prepareStatement(query)) {
            pstmt.setString(1, nombreProducto);
            pstmt.setDouble(2, precio);
            pstmt.setString(3, imagen);
            pstmt.setInt(4, idCategoria);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void UPDATE() {
        String query = "UPDATE Producto SET nombreProducto = ?, precio = ?, imagen = ?, idCategoria = ? WHERE idProducto = ?";
        try (PreparedStatement pstmt = Conexion.connection.prepareStatement(query)) {
            pstmt.setString(1, nombreProducto);
            pstmt.setDouble(2, precio);
            pstmt.setString(3, imagen);
            pstmt.setInt(4, idCategoria);
            pstmt.setInt(5, idProducto);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void DELETE() {
        String query = "DELETE FROM Producto WHERE idProducto = ?";
        try (PreparedStatement pstmt = Conexion.connection.prepareStatement(query)) {
            pstmt.setInt(1, idProducto);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SELECT
    public ObservableList<ProductoDAO> SELECT() {
        ObservableList<ProductoDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM Producto";
        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ProductoDAO p = new ProductoDAO();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombreProducto(rs.getString("nombreProducto"));
                p.setPrecio(rs.getDouble("precio"));
                p.setImagen(rs.getString("imagen"));
                p.setIdCategoria(rs.getInt("idCategoria"));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}