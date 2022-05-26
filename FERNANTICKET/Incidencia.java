package FERNANTICKET;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Incidencia {

    //Atributos

    private int id;
    private String descripcion;
    private String solucion;
    private int prioridad;
    private boolean estaResuelta;
    private String fechaInicio;
    private String fechaFin;
    private int idUsuario;
    private int idTecnico;

    //Constructor

    public Incidencia(int id, String descripcion,String solucion, int prioridad, boolean estaResuelta,String fechaInicio,String fechaFin, int idUsuario,int idTecnico) {

        SimpleDateFormat formato = new SimpleDateFormat("dd/M/yyyy");


        String fechaString = formato.format(new Date()); // Convierte Date a String
        Date miFecha = null; // convierte String a Date
        try {
            miFecha = formato.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fe = formato.format(miFecha);


        this.id = id;
        this.descripcion = descripcion;
        this.solucion = solucion;
        this.prioridad = prioridad;
        this.estaResuelta = estaResuelta;
        this.fechaInicio = formato.format(miFecha) ;
        this.fechaFin = fechaFin;
        this.idUsuario = idUsuario;
        this.idTecnico=idTecnico;
    }

    //Constructor copia
    public Incidencia(int id, String descripcion, int prioridad, int idUsuario) {

        SimpleDateFormat formato = new SimpleDateFormat("dd/M/yyyy");
        String fechaString = formato.format(new Date()); // Convierte Date a String
        Date miFecha = null; // convierte String a Date
        try {
            miFecha = formato.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fe = formato.format(miFecha);


        this.id = id;
        this.descripcion = descripcion;
        this.solucion = null;
        this.prioridad = prioridad;
        this.estaResuelta = false;
        this.fechaInicio = formato.format(miFecha) ;
        this.fechaFin = null;
        this.idUsuario = idUsuario;
        this.idTecnico=0;
    }

    //Getters and setters


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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    //toString

    public String toString(){
        return "Incidencia con Id: "+id+
                "\nDescripción: "+descripcion+
                "\nSolucion: "+((estaResuelta)?solucion:"Aún no está resuelta")+
                "\nPrioridad: "+prioridad+
                "\nResuelta: "+((estaResuelta)?"Está resuelta":"Aún no está resuelta")+
                "\nFecha Inicio: "+fechaInicio+
                "\nFecha Fin: "+((estaResuelta)?fechaFin:"Aún no está resuelta")+
                "\nId Usuario: "+idUsuario+
                "\nId Tecnico: "+((idTecnico==0)?"Incidencia no asignada":idTecnico)+
                "\n----------------------------------------------";
    }
}
