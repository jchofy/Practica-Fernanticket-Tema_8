package FERNANTICKET;

import java.util.ArrayList;

public class Usuario extends Persona {

    //Atributos

    private ArrayList<Incidencia> incidenciasUsuario;

    //Conctructor llamando al constructor padre e inicializando el arraylist de incidencias de usuario

    public Usuario(int id, String nombre, String apel, String clave, String email) {
        super(id, nombre, apel, clave, email);
        incidenciasUsuario= new ArrayList<Incidencia>();
    }

    //Getters and setter únicos de la clase Usuario

    public ArrayList<Incidencia> getIncidenciasUsuario() {
        return incidenciasUsuario;
    }

    //toString
    @Override
    public String toString() {
        return "Usuario "+ super.toString();
    }

}
