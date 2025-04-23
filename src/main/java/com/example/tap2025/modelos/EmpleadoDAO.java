package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class EmpleadoDAO {
    private int idEmpleado;
    private String nombres;
    private String apellido1;
    private String apellido2;
    private String curp;
    private String rfc;
    private float sueldo;
    private String horario;
    private String nssEmp;
    private String celEmp;
    private String fechaIngreso;

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public float getSueldo() {
        return sueldo;
    }

    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNssEmp() {
        return nssEmp;
    }

    public void setNssEmp(String nssEmp) {
        this.nssEmp = nssEmp;
    }

    public String getCelEmp() {
        return celEmp;
    }

    public void setCelEmp(String celEmp) {
        this.celEmp = celEmp;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void INSERT() {
        String query = "INSERT INTO empleado(nombres,apellido1,apellido2,curp,rfc,sueldo,horario,nssEmp,celEmp,fechaIngreso) " +
                "VALUES('" + nombres + "','" + apellido1 + "','" + apellido2 + "','" + curp + "','" + rfc + "'," + sueldo +
                ",'" + horario + "','" + nssEmp + "','" + celEmp + "','" + fechaIngreso + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE empleado SET nombres='" + nombres + "', apellido1='" + apellido1 + "', apellido2='" + apellido2 +
                "', curp='" + curp + "', rfc='" + rfc + "', sueldo=" + sueldo + ", horario='" + horario +
                "', nssEmp='" + nssEmp + "', celEmp='" + celEmp + "', fechaIngreso='" + fechaIngreso +
                "' WHERE idEmpleado=" + idEmpleado;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM empleado WHERE idEmpleado=" + idEmpleado;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<EmpleadoDAO> SELECT() {
        String query = "SELECT * FROM empleado";
        ObservableList<EmpleadoDAO> lista = FXCollections.observableArrayList();
        EmpleadoDAO obj;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                obj = new EmpleadoDAO();
                obj.setIdEmpleado(res.getInt("idEmpleado"));
                obj.setNombres(res.getString("nombres"));
                obj.setApellido1(res.getString("apellido1"));
                obj.setApellido2(res.getString("apellido2"));
                obj.setCurp(res.getString("curp"));
                obj.setRfc(res.getString("rfc"));
                obj.setSueldo(res.getFloat("sueldo"));
                obj.setHorario(res.getString("horario"));
                obj.setNssEmp(res.getString("nssEmp"));
                obj.setCelEmp(res.getString("celEmp"));
                obj.setFechaIngreso(res.getString("fechaIngreso"));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}