package DAO;

import FERNANTICKET.Incidencia;
import FERNANTICKET.Tecnico;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DaoTecnicoSQL implements  DaoTecnico{

    @Override
    public Incidencia buscaIncidenciaById(int idIncidencia, Tecnico tecnico, DAOManager dao) {
        Incidencia incidencia= null;
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE id="+idIncidencia+" and idTecnico ="+tecnico.getId();




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {
                    incidencia= new Incidencia(rs.getInt("id"),rs.getString("descripcion"),
                            rs.getString("solucion"),rs.getInt("prioridad"),
                            rs.getBoolean("estaResuelta"),rs.getString("fechaInicio"),
                            rs.getString("fechaFin"),rs.getInt("idUsuario"),rs.getInt("idTecnico"));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        try {
            dao.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return incidencia;
    }

    @Override
    public ArrayList<Incidencia> buscaIncidenciasByTerm(String termino, Tecnico tecnico, DAOManager dao) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE descripcion like '%"+termino+"%' and idTecnico ="+tecnico.getId();




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {
                    incidencias.add(new Incidencia(rs.getInt("id"),rs.getString("descripcion"),
                            rs.getString("solucion"),rs.getInt("prioridad"),
                            rs.getBoolean("estaResuelta"),rs.getString("fechaInicio"),
                            rs.getString("fechaFin"),rs.getInt("idUsuario"),rs.getInt("idTecnico")));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        try {
            dao.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return incidencias;
    }

    @Override
    public int incidenciasCerradas(Tecnico tecnico, DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE estaResuelta = 1 and idTecnico="+tecnico.getId();
        int incidenciasCerradas = 0;

        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {
                    incidenciasCerradas+=1;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return incidenciasCerradas;
    }

    @Override
    public int incidenciasAbiertas(Tecnico tecnico, DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE estaResuelta = 0 and idTecnico="+tecnico.getId();
        int incidenciasCerradas = 0;

        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {
                    incidenciasCerradas+=1;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return incidenciasCerradas;
    }

    @Override
    public float prioridadMediaTecnico(Tecnico tecnico, DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia where idTecnico="+tecnico.getId();
        float media = 0;
        int cantidadincidencias =0;

        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {
                    media=media+rs.getInt("prioridad");
                    cantidadincidencias+=1;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return media/cantidadincidencias;
    }

    @Override
    public boolean asignaIncidencia(Incidencia incidencia, Tecnico tecnico, DAOManager dao) {
        String sentencia;
        sentencia = "UPDATE incidencia SET idTecnico ="+tecnico.getId()+" WHERE Id = "+incidencia.getId()+"";
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

    @Override
    public boolean cierraIncidencia(int idIncidencia, String solucion, Tecnico tecnico, DAOManager dao) {

        SimpleDateFormat formato = new SimpleDateFormat("dd/M/yyyy");
        String fechaString = formato.format(new Date()); // Convierte Date a String
        Date miFecha = new Date(); // convierte String a Date


        String sentencia;
        sentencia = "UPDATE incidencia SET solucion ='"+solucion+"',estaResuelta = true,fechaFin ='"+formato.format(miFecha)+"' WHERE Id = "+idIncidencia+" and idTecnico = "+tecnico.getId();

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

    @Override
    public boolean cambiarPasswordTecnico(String contraenia, Tecnico tecnico, DAOManager dao){
        String sentencia;
        sentencia = "UPDATE tecnico SET clave ="+contraenia+" WHERE id = "+tecnico.getId()+"";

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
