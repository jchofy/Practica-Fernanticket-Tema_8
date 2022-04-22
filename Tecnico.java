package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Tecnico implements Serializable {
    private static int id = 0;
    private String nombre;
    private String apellidos;
    private String clave;
    private String email;
    private ArrayList<Incidencia> incidenciasTecnico;

    //Constructor
    public Tecnico() {
    }

    public Tecnico(String nombre, String apellidos, String clave, String email) {
        this.id = id + 1;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.clave = clave;
        this.email = email;
        incidenciasTecnico = new ArrayList<>();
    }

    //Getter y setters


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

    public ArrayList<Incidencia> getIncidenciasTecnico() {
        return incidenciasTecnico;
    }


    public void setIncidenciasTecnico(ArrayList<Incidencia> incidenciasTecnico) {
        this.incidenciasTecnico = incidenciasTecnico;
    }

    public void buscaIncidenciaId(int id) {
        for (Incidencia a : incidenciasTecnico) {
            if (a.getId() == id) {
                System.out.println(a);
            }
        }
    }

    public ArrayList<Incidencia> buscaIncidenciaByTerm(String termino) {
        ArrayList<Incidencia> incidenciasportermino = new ArrayList<>();
        for (Incidencia a : incidenciasTecnico) {
            if (a.getDescripcion().contains(termino)) {
                incidenciasportermino.add(a);
            }
        }
        return incidenciasportermino;
    }

    public int incidenciasAbiertas() {
        int incidencias = 0;
        for (Incidencia a : incidenciasTecnico) {
            if (a.isEstaResuelta() == false) {
                incidencias += 1;
            }
        }
        return incidencias;
    }

    public int incidenciasCerradas() {
        int incidencias = 0;
        for (Incidencia a : incidenciasTecnico) {
            if (a.isEstaResuelta() == true) {
                incidencias += 1;
            }
        }
        return incidencias;
    }

    public float prioridadMediaTecnico() {

        int prioridad = 0;
        for (Incidencia a : incidenciasTecnico) {
            prioridad = prioridad + a.getPrioridad();
        }
        float prioridadMedia = prioridad / incidenciasTecnico.size();
        return prioridadMedia;
    }

    public void asignaIncidencia(Incidencia a) {
        incidenciasTecnico.add(a);
    }

    public boolean cierraIncidencia(int pos, String solucion) {
        incidenciasTecnico.get(pos).setEstaResuelta(true);
        return incidenciasTecnico.get(pos).isEstaResuelta();
    }


    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Apellidos: " + apellidos + "\n" +
                "Id: " + id + "\n" +
                "Email: " + email;
    }
}
