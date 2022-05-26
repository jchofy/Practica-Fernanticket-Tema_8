package DAO;

import FERNANTICKET.Incidencia;
import FERNANTICKET.Usuario;

import java.util.ArrayList;

public interface DaoUsuario {

    boolean insertaIncidencia(Incidencia incidencia,DAOManager dao);
    boolean deleteIncidencia(int idIncidencia,Usuario usuario,DAOManager dao);
    Incidencia buscaIncidenciaById(int idIncidencia,Usuario usuario, DAOManager dao);
    ArrayList<Incidencia> buscaIncidenciasByTerm(String termino,Usuario usuario,DAOManager dao);
    int incidenciasAbiertas(Usuario usuario,DAOManager dao);
    float prioridadMediaUsuario(Usuario usuario,DAOManager dao);
    boolean cambiarPasswordUsuario(String contraenia, Usuario usuario, DAOManager dao);

    int incidenciasSinasignar(Usuario usuario, DAOManager dao);

    int incidenciasyaAsignadas(Usuario usuario, DAOManager dao);
}
