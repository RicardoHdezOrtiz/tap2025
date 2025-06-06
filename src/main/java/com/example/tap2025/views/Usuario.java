package com.example.tap2025.views;

public class Usuario {
    private String username;
    private String tipo;

    public Usuario(String username, String tipo) {
        this.username = username;
        this.tipo = tipo;
    }

    public String getUsername() {
        return username;
    }

    public String getTipo() {
        return tipo;
    }
}