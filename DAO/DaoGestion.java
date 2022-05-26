package DAO;

import FERNANTICKET.*;

import java.util.ArrayList;
import java.util.Date;

public interface DaoGestion {

    boolean insertaUsuario(Usuario usuario, DAOManager dao);
    boolean insertaTecnico(Tecnico tecnico, DAOManager dao);

    boolean insertaAdmin(Admin admin, DAOManager dao);

    boolean insertaIncidencia(Incidencia incidencia, DAOManager dao);
    ArrayList<IncidenciaDataClass> buscaTodasIncidencias(DAOManager dao);
    int incidenciasAbiertas(DAOManager dao);
    int incidenciasAbiertasAsignadas(DAOManager dao);
    int incidenciasCerradas(DAOManager dao);
    ArrayList<IncidenciaDataClass> buscaIncidenciasAsignadas(int idTecnico,DAOManager dao);
    //Genera idUser
    float prioridadMediaApp(DAOManager dao);
    Incidencia buscaIncidencia(int idIncidencia,DAOManager dao);
    Tecnico buscaTecnico(int idTecnico,DAOManager dao);
    boolean estaIncidenciaAsignada(int idIncidencia,DAOManager dao);
    Usuario buscaUsuarioPorIncidencia(int idIncidencia,DAOManager dao);
    boolean asignaIncidencia(int idTecnico,int idIncidencia,DAOManager dao);
    boolean cierraIncidencia(int idIncidencia,int idTecnico,String solucion,DAOManager dao);
    ArrayList<IncidenciaDataClass> buscaIncidenciasByTerm(String termino,DAOManager dao);
    int incidenciasAsignadas(int idTecnico,DAOManager dao);
    ArrayList<IncidenciaDataClass> buscaIncidenciasAbiertasByTecnico(int idTecnico,DAOManager dao);
    ArrayList<IncidenciaDataClass> buscaIncidenciasCerradasByTecnico(int idTecnico,DAOManager dao);
    ArrayList<IncidenciaDataClass> buscaIncidenciasCerradas(DAOManager dao);
    ArrayList<IncidenciaDataClass> buscaIncidenciasSinAsignar(DAOManager dao);
    int incidenciasCerradasByUsuario(int idUsuario,DAOManager dao);
    ArrayList<IncidenciaDataClass> buscaIncidenciasCerradasByUsuario(int idUsuario,DAOManager dao);
    ArrayList<Incidencia> buscaIncidenciasAbiertasByUsuario(int idUsuario,DAOManager dao);
    ArrayList<IncidenciaDataClass> buscaIncidenciasAbiertas(DAOManager dao);

    ArrayList<Tecnico> buscartodosTecnicos(DAOManager dao);

    boolean deletetecnico(int idtecnico, DAOManager dao);

    ArrayList<Usuario> buscartodosUsuarios(DAOManager dao);

    boolean borrarDatos(DAOManager dao);
}
