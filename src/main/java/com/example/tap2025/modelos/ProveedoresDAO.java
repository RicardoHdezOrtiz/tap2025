package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ProveedoresDAO {

    private int idProveedor;
    private String nombre;
    private String telContacto;
    private String direccion;
    private String email;
    private String nota;

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelContacto() {
        return telContacto;
    }

    public void setTelContacto(String telContacto) {
        this.telContacto = telContacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void INSERT(){
        String query = "INSERT INTO proveedores(nombre, telContacto, direccion, email, nota) " +
                "VALUES('" + nombre + "','" + telContacto + "','" + direccion + "','" + email + "','" + nota + "')";
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void UPDATE(){
        String query = "UPDATE proveedores SET nombre = '" + nombre + "', telContacto = '" + telContacto + "', " +
                "direccion = '" + direccion + "', email = '" + email + "', nota = '" + nota + "' WHERE idProveedor = " + idProveedor;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM proveedores WHERE idProveedor = " + idProveedor;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<ProveedoresDAO> SELECT(){
        String query = "SELECT * FROM proveedores";
        ObservableList<ProveedoresDAO> listaP = FXCollections.observableArrayList();
        ProveedoresDAO objP;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                objP = new ProveedoresDAO();
                objP.setIdProveedor(res.getInt("idProveedor"));
                objP.setNombre(res.getString("nombre"));
                objP.setTelContacto(res.getString("telContacto"));
                objP.setDireccion(res.getString("direccion"));
                objP.setEmail(res.getString("email"));
                objP.setNota(res.getString("nota"));
                listaP.add(objP);
            }
        }catch(Exception e){
            System.out.println("Error en SELECT de proveedores: " + e.getMessage());
            e.printStackTrace();
        }
        return listaP;
    }
}