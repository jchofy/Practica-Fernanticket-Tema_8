package FERNANTICKET;

import java.util.ArrayList;

public class Tecnico extends Persona {

    //Atributos

    private ArrayList<Incidencia> incidenciasTecnico;

    //Constructor

    public Tecnico(int id, String nombre, String apel, String clave, String email) {
        super(id, nombre, apel, clave, email);
        incidenciasTecnico = new ArrayList<Incidencia>();
    }

    //Getters and setters unicos de la clase Técnico


    public ArrayList<Incidencia> getIncidenciasTecnico() {
        return incidenciasTecnico;
    }


    //toString
    @Override
    public String toString() {
        return "Técnico "+ super.toString();
    }


}
