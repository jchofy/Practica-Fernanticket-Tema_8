package DAO;

import FERNANTICKET.Incidencia;
import FERNANTICKET.IncidenciaDataClass;
import FERNANTICKET.Tecnico;
import FERNANTICKET.Usuario;

public interface DaoIncidenciadataclass {

    boolean insertaIncidenciadataclass(Incidencia incidencia,Usuario usuario,Tecnico tecnico, DAOManager dao);
}
