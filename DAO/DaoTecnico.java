package DAO;

import FERNANTICKET.Incidencia;
import FERNANTICKET.Tecnico;
import FERNANTICKET.Usuario;

import java.util.ArrayList;

public interface DaoTecnico {


    Incidencia buscaIncidenciaById(int idIncidencia, Tecnico tecnico, DAOManager dao);
    ArrayList<Incidencia> buscaIncidenciasByTerm(String termino, Tecnico tecnico, DAOManager dao);
    int incidenciasCerradas(Tecnico tecnico,DAOManager dao);
    int incidenciasAbiertas(Tecnico tecnico,DAOManager dao);
    float prioridadMediaTecnico(Tecnico tecnico,DAOManager dao);
    boolean asignaIncidencia(Incidencia incidencia,Tecnico tecnico,DAOManager dao);
    boolean cierraIncidencia(int idIncidencia,String solucion,Tecnico tecnico,DAOManager dao);
    boolean cambiarPasswordTecnico(String contraenia, Tecnico tecnico, DAOManager dao);
}
