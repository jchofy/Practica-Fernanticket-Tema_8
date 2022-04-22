package com.company;

import java.io.Serializable;

public class Admin implements Serializable {
    //Atributos
    private int id;
    private String nombre;
    private String apellidos;
    private String clave;
    private String email;

    //Constructores
    public Admin() {
    }

    public Admin(int id, String nombre, String apel, String clave, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apel;
        this.clave = clave;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApel() {
        return apellidos;
    }

    public void setApel(String apel) {
        this.apellidos = apel;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Apellidos: " + apellidos + "\n" +
                "Email: " + email;
    }
}
