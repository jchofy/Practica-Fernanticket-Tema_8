package DAO;

import FERNANTICKET.Incidencia;
import FERNANTICKET.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DaoUsuarioSQL implements DaoUsuario {

    @Override
    public boolean insertaIncidencia(Incidencia incidencia, DAOManager dao) {
        String sentencia;
        sentencia = "INSERT INTO incidencia VALUES ("
                + incidencia.getId() + ",'"
                + incidencia.getDescripcion() + "','"
                +incidencia.getSolucion() + "',"
                + incidencia.getPrioridad() + ","
                + incidencia.isEstaResuelta()+",'"
                +incidencia.getFechaInicio()+"','"
                +incidencia.getFechaFin()+"',"
                +incidencia.getIdUsuario()+","
                +incidencia.getIdTecnico()+")";


        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()){
            stm.executeUpdate(sentencia);
            return true;

        }catch (SQLException e){
            System.out.println("Ya hay un incidencia con ese id");
        }

        try {
            dao.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteIncidencia(int idIncidencia, Usuario usuario, DAOManager dao) {
        String sentencia;
        sentencia = "DELETE FROM incidencia WHERE id="+idIncidencia+" and idUsuario ="+usuario.getId();

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
    public Incidencia buscaIncidenciaById(int idIncidencia, Usuario usuario, DAOManager dao) {
        Incidencia incidencia= null;
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE id="+idIncidencia+" and idUsuario ="+usuario.getId();




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
    public ArrayList<Incidencia> buscaIncidenciasByTerm(String termino, Usuario usuario, DAOManager dao) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE descripcion like '%"+termino+"%' and idUsuario ="+usuario.getId();




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
    public int incidenciasAbiertas(Usuario usuario, DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE estaResuelta = 0 and idUsuario ="+usuario.getId();


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
    public float prioridadMediaUsuario(Usuario usuario, DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia where idUsuario = "+usuario.getId();
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
    public boolean cambiarPasswordUsuario(String contraenia, Usuario usuario, DAOManager dao){
        String sentencia;
        sentencia = "UPDATE usuario SET clave ="+contraenia+" WHERE id = "+usuario.getId()+"";

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
    public int incidenciasSinasignar(Usuario usuario, DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE idUsuario = "+usuario.getId()+"  and idTecnico= 0";
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
    public int incidenciasyaAsignadas(Usuario usuario, DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE idUsuario = "+usuario.getId()+"  and idTecnico!= 0";
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
}
