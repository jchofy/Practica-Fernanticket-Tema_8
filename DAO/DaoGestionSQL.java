package DAO;

import FERNANTICKET.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DaoGestionSQL implements DaoGestion {

    @Override
    public boolean insertaUsuario(Usuario usuario, DAOManager dao) {

        String sentencia;
        sentencia = "INSERT INTO usuario VALUES ("
                + usuario.getId() + ",'"
                + usuario.getNombre() + "','"
                +usuario.getApel() + "','"
                + usuario.getClave() + "','"
                + usuario.getEmail()+"')";


        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()){
            stm.executeUpdate(sentencia);
            return true;

        }catch (SQLException e){
            System.out.println("Ya existe un usuario con ese id");
        }

        try {
            dao.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean insertaTecnico(Tecnico tecnico, DAOManager dao) {
        String sentencia;
        sentencia = "INSERT INTO tecnico VALUES ("
                + tecnico.getId() + ",'"
                + tecnico.getNombre() + "','"
                +tecnico.getApel() + "','"
                + tecnico.getClave() + "','"
                + tecnico.getEmail()+"')";


        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()){
            stm.executeUpdate(sentencia);
            return true;

        }catch (SQLException e){
            System.out.println("Ya hay un tecnico con ese id");
        }

        try {
            dao.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertaAdmin(Admin admin, DAOManager dao) {

        String sentencia;
        sentencia = "INSERT INTO admin VALUES ("
                + admin.getId() + ",'"
                + admin.getNombre() + "','"
                +admin.getApel() + "','"
                + admin.getClave() + "','"
                + admin.getEmail()+"')";


        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()){
            stm.executeUpdate(sentencia);
            return true;

        }catch (SQLException e){
            System.out.println("Ya existe un admin con ese id");
        }

        try {
            dao.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

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
    public ArrayList<IncidenciaDataClass> buscaTodasIncidencias(DAOManager dao) {
        ArrayList<IncidenciaDataClass> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia ";



        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {

                    Tecnico t = buscaTecnico(rs.getInt("idtecnico"),dao);
                    Usuario u = buscaUsuarioPorIncidencia(rs.getInt("id"),dao);
                    Incidencia i = buscaIncidencia(rs.getInt("id"),dao);

                    //Comprobamos si la incidencia tiene un técnico asignado, si no lo tiene creamos uno por defecto
                    if (t==null){
                        incidencias.add(new IncidenciaDataClass(u,new Tecnico(0,null,null,null,null),i));
                    }else incidencias.add(new IncidenciaDataClass(u,t,i));

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
    public int incidenciasAbiertas(DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE estaResuelta = 0";
        int incidenciasAbiertas = 0;

        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {
                    incidenciasAbiertas+=1;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return incidenciasAbiertas;
    }

    @Override
    public int incidenciasAbiertasAsignadas(DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE estaResuelta = 0 and idTecnico != 0";
        int incidenciasAbiertas = 0;

        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {
                    incidenciasAbiertas+=1;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return incidenciasAbiertas;
    }

    @Override
    public int incidenciasCerradas(DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE estaResuelta = 1 ";
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
    public ArrayList<IncidenciaDataClass> buscaIncidenciasAsignadas(int idTecnico, DAOManager dao) {
        ArrayList<IncidenciaDataClass> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE idTecnico = " + idTecnico+ "";



        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {

                    Tecnico t = buscaTecnico(rs.getInt("idtecnico"),dao);
                    Usuario u = buscaUsuarioPorIncidencia(rs.getInt("id"),dao);
                    Incidencia i = buscaIncidencia(rs.getInt("id"),dao);

                    incidencias.add(new IncidenciaDataClass(u,t,i));
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
    public float prioridadMediaApp(DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia";
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
    public Incidencia buscaIncidencia(int idIncidencia2, DAOManager dao) {
        Incidencia incidencia = null;
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE id = "+idIncidencia2;



        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                if (rs.next()) {
                     incidencia= new Incidencia(rs.getInt("id"),rs.getString("descripcion"),rs.getString("solucion"),rs.getInt("prioridad"),
                             rs.getBoolean("estaResuelta"),rs.getString("fechaInicio"),rs.getString("fechaFin")
                             ,rs.getInt("idUsuario"), rs.getInt("idTecnico"));
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
    public Tecnico buscaTecnico(int idTecnico, DAOManager dao) {
        String sentencia;
        sentencia = "SELECT * FROM tecnico WHERE id = "+idTecnico;
        Tecnico tecnico = null;


        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                if (rs.next()) {
                    tecnico= new Tecnico(rs.getInt("id"),rs.getString("nombre"),rs.getString("apel"),rs.getString("clave"),rs.getString("email"));
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

        return tecnico;
    }

    @Override
    public boolean estaIncidenciaAsignada(int idIncidencia, DAOManager dao) { //TODO REVISAR QUE EN LOS DOS CASOS DA FALSE
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE id = "+idIncidencia;
        int incidenciasAbiertas = 0;

        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                if (rs.next()) {
                    if (rs.getInt("idTecnico")!=0){
                        return true;
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    @Override
    public Usuario buscaUsuarioPorIncidencia(int idIncidencia, DAOManager dao) {
        Incidencia incidencia = buscaIncidencia(idIncidencia,dao);
        Usuario usuario= null;

        String sentencia;
        sentencia = "SELECT * FROM usuario WHERE id = "+incidencia.getIdUsuario();


        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                if (rs.next()) {
                    usuario= new Usuario(rs.getInt("id"),rs.getString("nombre"),rs.getString("apel"),rs.getString("clave"),rs.getString("email"));
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

        return usuario;
    }

    @Override
    public boolean asignaIncidencia(int idTecnico, int idIncidencia, DAOManager dao) {
        String sentencia;
        sentencia = "UPDATE incidencia SET idTecnico ="+idTecnico+" WHERE Id = "+idIncidencia+"";
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
    public boolean cierraIncidencia(int idIncidencia, int idTecnico, String solucion, DAOManager dao) {

        SimpleDateFormat formato = new SimpleDateFormat("dd/M/yyyy");
        String fechaString = formato.format(new Date()); // Convierte Date a String
        Date miFecha = new Date(); // convierte String a Date


        String sentencia;
        sentencia = "UPDATE incidencia SET solucion ='"+solucion+"',estaResuelta = true,fechaFin ='"+formato.format(miFecha)+"' WHERE Id = "+idIncidencia+" and idTecnico = "+idTecnico;

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
    public ArrayList<IncidenciaDataClass> buscaIncidenciasByTerm(String termino, DAOManager dao) {
        ArrayList<IncidenciaDataClass> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE descripcion like '%" + termino + "%'";




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {


                    Incidencia i = buscaIncidencia(rs.getInt("id"),dao);
                    Tecnico t = buscaTecnico(rs.getInt("idtecnico"),dao);
                    Usuario u = buscaUsuarioPorIncidencia(rs.getInt("id"),dao);

                    if (t==null){
                        incidencias.add(new IncidenciaDataClass(u,i));
                    }else incidencias.add(new IncidenciaDataClass(u,t,i));

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
    public int incidenciasAsignadas(int idTecnico, DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE idTecnico = "+idTecnico;
        int incidenciasAsignadas = 0;

        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {
                    incidenciasAsignadas +=1;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return incidenciasAsignadas;
    }

    @Override
    public ArrayList<IncidenciaDataClass> buscaIncidenciasAbiertasByTecnico(int idTecnico, DAOManager dao) {
        ArrayList<IncidenciaDataClass> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE idTecnico = " + idTecnico+ " and estaResuelta = false";




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {

                    Tecnico t = buscaTecnico(rs.getInt("idtecnico"),dao);
                    Usuario u = buscaUsuarioPorIncidencia(rs.getInt("id"),dao);
                    Incidencia i = buscaIncidencia(rs.getInt("id"),dao);

                    incidencias.add(new IncidenciaDataClass(u,t,i));
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
    public ArrayList<IncidenciaDataClass> buscaIncidenciasCerradasByTecnico(int idTecnico, DAOManager dao) {
        ArrayList<IncidenciaDataClass> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE idTecnico = " + idTecnico+ " and estaResuelta = true";




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {

                    Tecnico t = buscaTecnico(rs.getInt("idtecnico"),dao);
                    Usuario u = buscaUsuarioPorIncidencia(rs.getInt("id"),dao);
                    Incidencia i = buscaIncidencia(rs.getInt("id"),dao);

                    incidencias.add(new IncidenciaDataClass(u,t,i));
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
    public ArrayList<IncidenciaDataClass> buscaIncidenciasCerradas(DAOManager dao) {
        ArrayList<IncidenciaDataClass> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE estaResuelta = true";




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {

                    Tecnico t = buscaTecnico(rs.getInt("idtecnico"),dao);
                    Usuario u = buscaUsuarioPorIncidencia(rs.getInt("id"),dao);
                    Incidencia i = buscaIncidencia(rs.getInt("id"),dao);

                    incidencias.add(new IncidenciaDataClass(u,t,i));
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
    public ArrayList<IncidenciaDataClass> buscaIncidenciasSinAsignar(DAOManager dao) {
        ArrayList<IncidenciaDataClass> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE idTecnico = 0";




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {

                    Tecnico t = buscaTecnico(rs.getInt("idtecnico"),dao);
                    Usuario u = buscaUsuarioPorIncidencia(rs.getInt("id"),dao);
                    Incidencia i = buscaIncidencia(rs.getInt("id"),dao);

                    incidencias.add(new IncidenciaDataClass(u,new Tecnico(0,null,null,null,null),i));
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
    public int incidenciasCerradasByUsuario(int idUsuario, DAOManager dao) {
        String sentencia;
        sentencia ="SELECT * FROM incidencia WHERE estaResuelta = 1 and idUsuario = "+idUsuario;
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
    public ArrayList<IncidenciaDataClass> buscaIncidenciasCerradasByUsuario(int idUsuario, DAOManager dao) {
        ArrayList<IncidenciaDataClass> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE idUsuario = " + idUsuario+ " and estaResuelta = true";




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {

                    Incidencia i = buscaIncidencia(rs.getInt("id"),dao);
                    Tecnico t = buscaTecnico(rs.getInt("idtecnico"),dao);
                    Usuario u = buscaUsuarioPorIncidencia(rs.getInt("id"),dao);


                    incidencias.add(new IncidenciaDataClass(u,t,i));
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
    public ArrayList<Incidencia> buscaIncidenciasAbiertasByUsuario(int idUsuario, DAOManager dao) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE idUsuario = " + idUsuario+ " and estaResuelta = false";




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {


                    incidencias.add(new Incidencia(rs.getInt("id"),rs.getString("descripcion"),
                            rs.getInt("prioridad"),rs.getInt("idUsuario")));
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

    public int generaIdUser(DAOManager dao){

        String sentencia;
        sentencia ="SELECT * FROM usuario ";
        int id;

        HashMap<Integer,String> idusuarios = new HashMap<>();
    String nan = "";
        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {
                    idusuarios.put(rs.getInt("id"),nan);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }


        do {
            id=(int)(Math.random()*10000+1);

        }while (idusuarios.containsKey(id));

        return id;

    }

    public int generaIdIncidencia(DAOManager dao){String sentencia;
        sentencia ="SELECT * FROM incidencia ";
        int id;

        HashMap<Integer,String> idIncidencias = new HashMap<>();
        String nan = "";
        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {
                    idIncidencias.put(rs.getInt("id"),nan);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }


        do {
            id=(int)(Math.random()*10000+1);

        }while (idIncidencias.containsKey(id));

        return id;

    }

    @Override
    public ArrayList<IncidenciaDataClass> buscaIncidenciasAbiertas(DAOManager dao) {
        ArrayList<IncidenciaDataClass> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia WHERE estaResuelta = false";


        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {



                    Incidencia i = buscaIncidencia(rs.getInt("id"),dao);
                    Tecnico t = buscaTecnico(rs.getInt("idtecnico"),dao);
                    Usuario u = buscaUsuarioPorIncidencia(rs.getInt("id"),dao);

                    if (t==null){
                        incidencias.add(new IncidenciaDataClass(u,i));
                    }else incidencias.add(new IncidenciaDataClass(u,t,i));

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
    public ArrayList<Tecnico> buscartodosTecnicos(DAOManager dao) {
        ArrayList<Tecnico> tecnicos = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM tecnico";




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {

                    tecnicos.add(new Tecnico(rs.getInt("id"),rs.getString("nombre"),
                            rs.getString("apel"),rs.getString("clave"),rs.getString("email")));
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

        return tecnicos;
    }

    @Override
    public boolean deletetecnico(int idtecnico, DAOManager dao) {
        String sentencia;
        sentencia = "DELETE FROM tecnico WHERE id="+idtecnico+"";


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
    public ArrayList<Usuario> buscartodosUsuarios(DAOManager dao) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM usuario";




        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()) {
            try (ResultSet rs = stm.executeQuery(sentencia)) {
                while (rs.next()) {

                    usuarios.add(new Usuario(rs.getInt("id"),rs.getString("nombre"),
                            rs.getString("apel"),rs.getString("clave"),rs.getString("email")));
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

        return usuarios;
    }

    @Override
    public boolean borrarDatos(DAOManager dao) {

        String sentencia1 = "TRUNCATE TABLE usuario;";
        String sentencia2 = "TRUNCATE TABLE incidencia;";
        String sentencia3 = "TRUNCATE TABLE incidenciadataclass;";
        String sentencia4 = "TRUNCATE TABLE tecnico;";

        try {
            dao.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Statement stm = dao.getConn().createStatement()){
            stm.executeUpdate(sentencia1);
            stm.executeUpdate(sentencia2);
            stm.executeUpdate(sentencia3);
            stm.executeUpdate(sentencia4);
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
