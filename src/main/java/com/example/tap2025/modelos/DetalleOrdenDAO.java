package com.example.tap2025.modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleOrdenDAO {
    private int idDetalle;
    private int idOrden;
    private int idProducto;
    private int cantidad;
    private Connection connection;

    public DetalleOrdenDAO() {
        connection = Conexion.connection;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void INSERT() {
        String query = "INSERT INTO detalleOrden (idOrden, idProducto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idOrden);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idDetalle = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE detalleOrden SET idOrden = ?, idProducto = ?, cantidad = ? WHERE idDetalle = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idOrden);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.setInt(4, idDetalle);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM detalleOrden WHERE idDetalle = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idDetalle);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DetalleOrdenDAO> SELECT() {
        List<DetalleOrdenDAO> lista = new ArrayList<>();
        String query = "SELECT * FROM detalleOrden";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DetalleOrdenDAO detalle = new DetalleOrdenDAO();
                detalle.setIdDetalle(rs.getInt("idDetalle"));
                detalle.setIdOrden(rs.getInt("idOrden"));
                detalle.setIdProducto(rs.getInt("idProducto"));
                detalle.setCantidad(rs.getInt("cantidad"));
                lista.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}