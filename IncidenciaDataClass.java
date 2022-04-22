package com.company;

import java.time.LocalDate;

public class IncidenciaDataClass {
    private int id;
    private String descripcion;
    private String solucion;
    private int prioridad;
    private boolean estaResuelta = false;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int idUsuario;
    private LocalDate resuelta;
    private int dias;
    private String nombreUsuario;
    private String emailUsuario;
    private int idTecnico;
    private String nombreTenico;
    private boolean asignada;

    //constructor


    public IncidenciaDataClass() {
    }

    public IncidenciaDataClass(int id, String descripcion, String solucion, int prioridad, boolean estaResuelta, LocalDate fechaInicio, LocalDate fechaFin, int idUsuario, LocalDate resuelta, int dias, String nombreUsuario, String emailUsuario, int idTecnico, String nombreTenico) {
        this.id = id;
        this.descripcion = descripcion;
        this.solucion = solucion;
        this.prioridad = prioridad;
        this.estaResuelta = estaResuelta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idUsuario = idUsuario;
        this.resuelta = resuelta;
        this.dias = dias;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.idTecnico = idTecnico;
        this.nombreTenico = nombreTenico;
    }

    public IncidenciaDataClass(Incidencia incidencia) {
        this.id = id;
        this.descripcion = descripcion;
        this.solucion = solucion;
        this.prioridad = prioridad;
        this.estaResuelta = estaResuelta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idUsuario = idUsuario;
        this.resuelta = resuelta;
        this.dias = dias;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.idTecnico = idTecnico;
        this.nombreTenico = nombreTenico;
    }

    //Getters and setters


    public boolean isAsignada() {
        return asignada;
    }

    public void setAsignada(boolean asignada) {
        this.asignada = asignada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isEstaResuelta() {
        return estaResuelta;
    }

    public void setEstaResuelta(boolean estaResuelta) {
        this.estaResuelta = estaResuelta;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getResuelta() {
        return resuelta;
    }

    public void setResuelta(LocalDate resuelta) {
        this.resuelta = resuelta;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNombreTenico() {
        return nombreTenico;
    }

    public void setNombreTenico(String nombreTenico) {
        this.nombreTenico = nombreTenico;
    }

    public String toString() {
        return "Id: " + id + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Solución: " + solucion + "\n" +
                "Prioridad: " + prioridad + "\n" +
                "Resuelta: " + estaResuelta + "\n" +
                "Fecha inicio: " + fechaInicio + "\n" +
                "Fecha fin: " + fechaFin + "\n" +
                "Días: " + dias + "\n" +
                "Nombre de usuario: " + nombreUsuario + "\n" +
                "Email de usuario: " + emailUsuario + "\n" +
                "Nombre del técnico: " + nombreTenico + "\n" +
                "Id del técnico: " + idTecnico;
    }
}
