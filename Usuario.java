package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Usuario implements Serializable {

    private static int id;
    private String nombre;
    private String apellidos;
    private String clave;
    private String email;
    private ArrayList<Incidencia> incidenciasUsuario;

    Scanner scanner = new Scanner(System.in);

    //constructor
    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, String clave, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.clave = clave;
        this.email = email;
        incidenciasUsuario = new ArrayList<>();
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public ArrayList<Incidencia> getIncidenciasUsuario() {
        return incidenciasUsuario;
    }

    public void setIncidenciasUsuario(ArrayList<Incidencia> incidenciasUsuario) {
        this.incidenciasUsuario = incidenciasUsuario;
    }

    //Métodos
    public void insertaIncidencia(String descripcion, int prioridad) {
        incidenciasUsuario.add(new Incidencia(descripcion, prioridad));
    }

    public void borrarIncidencia() {
        int posicionaborrar;
        int posicion = 1;

        for (Incidencia a : incidenciasUsuario) {
            System.out.print(posicion + ". " + a);
            posicion++;
        }

        System.out.println("¿Qué posición quieres borrar?");
        posicionaborrar = Integer.parseInt(scanner.nextLine());
        incidenciasUsuario.remove(posicionaborrar - 1);
        System.out.println("Has borrado la incidencia número " + posicionaborrar);
    }

    public Incidencia buscaIncidenciaId(int id) {

        int incidencia = 0;
        for (int i = 0; i < incidenciasUsuario.size(); i++) {
            if (incidenciasUsuario.get(i).getId() == id) {
                incidencia = i;
            }
        }
        return incidenciasUsuario.get(incidencia);
    }

    public ArrayList<Incidencia> buscaIncidenciaByTerm(String termino) {
        ArrayList<Incidencia> incidenciasportermino = new ArrayList<>();
        for (Incidencia a : incidenciasUsuario) {
            if (a.getDescripcion().contains(termino)) {
                incidenciasportermino.add(a);
            }
        }
        return incidenciasportermino;
    }

    public int incidenciasAbiertas() {
        int incidencias = 0;
        for (Incidencia a : incidenciasUsuario) {
            if (a.isEstaResuelta() == false) {
                incidencias++;
            }
        }

        return incidencias;
    }

    public float prioridadMediaUsuario() {

        int prioridad = 0;
        for (Incidencia a : incidenciasUsuario) {
            prioridad = prioridad + a.getPrioridad();
        }
        float prioridadMedia = prioridad / incidenciasUsuario.size();
        return prioridadMedia;

    }


    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Apellidos: " + apellidos + "\n" +
                "Id: " + id + "\n" +
                "Email: " + email;
    }

}
