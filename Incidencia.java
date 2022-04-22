package com.company;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Incidencia implements Serializable {

    private int id;
    private String descripcion;
    private String solucion;
    private int prioridad;
    private boolean estaResuelta;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int idUsuario;
    private LocalDate resuelta;
    private boolean asignada;
    private String estado;

    //Constructor
    public Incidencia() {
    }

    public Incidencia(String descripcion, int prioridad) {

        this.id = id;
        this.descripcion = descripcion;
        this.solucion = solucion;
        this.prioridad = prioridad;
        estaResuelta = false;
        this.fechaInicio = LocalDate.now();
        this.idUsuario = idUsuario;
        asignada = false;
        estado = "Sin Asignar";
    }

    //Getters and Setters


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getResuelta() {
        return resuelta;
    }

    public void setResuelta(LocalDate resuelta) {
        this.resuelta = resuelta;
    }

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
        if (estaResuelta) {
            resuelta = LocalDate.now();
        }
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

    /*----------------------------------------------------------------------------------------*/
    public long diasAbierta() {
        long dias = DAYS.between(fechaInicio, LocalDate.now());
        return diasAbierta();
    }

    /*----------------------------------------------------------------------------------------*/
    public long diasEnresolverse() {
        long dias = DAYS.between(fechaInicio, fechaFin);
        return diasEnresolverse();
    }

    /*----------------------------------------------------------------------------------------*/
    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Solución: " + solucion + "\n" +
                "Prioridad: " + prioridad + "\n" +
                "Fecha inicio: " + fechaInicio + "\n" +
                "Id Usuario: " + idUsuario + "\n" +
                "Estado: " + estado + "\n";


    }
}
