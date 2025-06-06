package com.example.tap2025.modelos;

import com.example.tap2025.views.Usuario;
import java.sql.*;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Usuario validarCredenciales(String username, String password) {
        try {
            String sql = "SELECT tipo_usuario FROM Usuario WHERE username = ? AND password = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo_usuario");
                return new Usuario(username, tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}