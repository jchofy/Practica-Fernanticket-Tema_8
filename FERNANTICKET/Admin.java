package FERNANTICKET;

public class Admin extends Persona{

    //Atributos

    //Constructor

    public Admin(int id, String nombre, String apel, String clave, String email) {
        super(id, nombre, apel, clave, email);
    }

    //Métodos


    //toString
    @Override
    public String toString() {
        return "Admin "+ super.toString();
    }
}
