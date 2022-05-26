package DAO;

import FERNANTICKET.Incidencia;
import FERNANTICKET.IncidenciaDataClass;
import FERNANTICKET.Tecnico;
import FERNANTICKET.Usuario;

import java.sql.SQLException;
import java.sql.Statement;

public class DaoIncidenciadataclassSQL implements  DaoIncidenciadataclass {


    @Override
    public boolean insertaIncidenciadataclass(Incidencia incidencia,Usuario usuario,Tecnico tecnico, DAOManager dao) {

        String sentencia;
        sentencia = "INSERT INTO incidenciadataclass VALUES ("+incidencia.getId()+",'"+incidencia.getDescripcion()+"','"+
                incidencia.getSolucion()+"',"+incidencia.getPrioridad()+","+incidencia.isEstaResuelta()+",'"+
                incidencia.getFechaInicio()+"','"+incidencia.getFechaFin()+"',"+usuario.getId()+","+0+",'"+
                usuario.getNombre()+"',"+tecnico.getId()+",'"+tecnico.getNombre()+"');";
        System.out.println(sentencia);


        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()){
            stm.executeUpdate(sentencia);
            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            dao.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
