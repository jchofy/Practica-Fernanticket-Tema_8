package FERNANTICKET;

import java.util.Date;

public class IncidenciaDataClass {

    //Atributos
    private int id;
    private String descripcion;
    private String solucion;
    private int prioridad;
    private boolean estaResuelta;
    private String fechaInicio;
    private String fechaFin;
    private int idUsuario;
    private int dias = 0;
    private String nombreUsuario;
    private int idTecnico;
    private String nombreTecnico;

    //Constructor

    public IncidenciaDataClass(Usuario usuario,Tecnico tecnico,Incidencia incidencia) {
        this.id = incidencia.getId();
        this.descripcion = incidencia.getDescripcion();
        this.solucion = incidencia.getSolucion();
        this.prioridad = incidencia.getPrioridad();
        this.estaResuelta = incidencia.isEstaResuelta();
        this.fechaInicio = incidencia.getFechaInicio();
        this.fechaFin = incidencia.getFechaFin();
        this.idUsuario = usuario.getId();
        this.dias = dias;
        this.nombreUsuario = usuario.getNombre();
        this.idTecnico = tecnico.getId();
        this.nombreTecnico = tecnico.getNombre();
    }

    //Constructor

    public IncidenciaDataClass(Usuario usuario,Incidencia incidencia) {
        this.id = incidencia.getId();
        this.descripcion = incidencia.getDescripcion();
        this.solucion = incidencia.getSolucion();
        this.prioridad = incidencia.getPrioridad();
        this.estaResuelta = incidencia.isEstaResuelta();
        this.fechaInicio = incidencia.getFechaInicio();
        this.fechaFin = incidencia.getFechaFin();
        this.idUsuario = usuario.getId();
        this.dias = dias;
        this.nombreUsuario = usuario.getNombre();
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

    public boolean isEstaresuelta() {
        return estaResuelta;
    }

    public void setEstaresuelta(boolean estaresuelta) {
        this.estaResuelta = estaresuelta;
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

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    //toString

    @Override
    public String toString() {
        return "\nIncidencia con Id: "+id+
                "\nDescripci??n: "+descripcion+
                "\nSolucion: "+((estaResuelta)?solucion:"A??n no est?? resuelta")+
                "\nPrioridad: "+prioridad+
                "\nResuelta: "+((estaResuelta)?"Est?? resuelta":"A??n no est?? resuelta")+
                "\nFecha Inicio: "+fechaInicio+
                "\nFecha Fin: "+((estaResuelta)?fechaFin:"A??n no est?? resuelta")+
                "\nId Usuario: "+idUsuario+
                "\nD??as abierta: "+dias+
                "\nNombre de usuario: "+nombreUsuario+
                "\nId Tecnico: "+((idTecnico==0)?"Incidencia no asignada":idTecnico)+
                "\nNombre t??cnico: "+((idTecnico==0)?"Tecnico no asignado":nombreTecnico)+
                "\n----------------------------------------------";
    }
}
