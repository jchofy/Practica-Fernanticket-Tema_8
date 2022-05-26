package FERNANTICKET;

import DAO.*;

public class FernanTicketVista {

    //Atributos estáticos
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";

    //Métodos
    public void mensaje(String mensaje){
        System.out.println(ANSI_GREEN+mensaje+ANSI_RESET);
    }

    public void mensajeError(String mensajeError){

        System.out.println(ANSI_RED+mensajeError+ANSI_RESET);
    }

    public void pideDato(String dato){
        System.out.println(ANSI_PURPLE+"Introduce "+dato+ANSI_RESET);
    }

    public void pintaMenuInicio(){

        System.out.println(ANSI_PURPLE+"""
              ╔═════════════════════════════════════════════════════════════════════════════════╗  
                ║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║ MENU DE INICIO ║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║
                
                        Bienvenido a FernanTicket
                *** Recuerda que para reportar una incidencia tienes que estar registrado
                
                    ----------------------------------------------------
                    [1]. Iniciar Sesión
                    ----------------------------------------------------
                    [2]. Registrarse
                    ----------------------------------------------------
                    [3]. Salir
                    ----------------------------------------------------
             
              ╚═════════════════════════════════════════════════════════════════════════════════╝   
               
                """+ANSI_RESET);
    }

    public void pintaMenuUsuario(Usuario usuario, DaoUsuarioSQL daoUsuario, DAOManager dao){

        System.out.println(ANSI_PURPLE+"""
              ╔═════════════════════════════════════════════════════════════════════════════════╗  
                ║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║ MENU DE USUARIO ║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║ """);

        System.out.println("\n\tBienvenid@ "+usuario.getNombre()+", tiene usted perfil de usuario normal"); //TODO ARREGLAR ESTA PARTE DEL CÓDIGO PARA QUE FUNCIONE BIEN
        System.out.println("\tActualmente, tiene "+daoUsuario.incidenciasSinasignar(usuario,dao)+" incidencias sin asignar y "+daoUsuario.incidenciasyaAsignadas(usuario,dao)+" incidencias ya asignadas");

        System.out.println("""
                
                     ----------------------------------------------------
                      [1]. Registrar una incidencia
                     ----------------------------------------------------
                      [2]. Consultar mis incidencias abiertas
                     ----------------------------------------------------
                      [3]. Consultar mis incidencias cerradas
                     ----------------------------------------------------
                      [4]. Mostrar mi perfil
                     ----------------------------------------------------
                      [5]. Cambiar clave de acceso
                     ----------------------------------------------------
                      [6]. Cerrar sesión
                     ----------------------------------------------------
                
                ╚═════════════════════════════════════════════════════════════════════════════════╝
                """+ANSI_RESET);


}

    public void pintaMenuTecnico(Tecnico tecnico, DaoTecnico  daoTecnico,DAOManager dao){

        System.out.println(ANSI_PURPLE+"""
              ╔═════════════════════════════════════════════════════════════════════════════════╗  
                ║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║ MENU DE TÉCNICO ║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║ """);

        System.out.println("\n\tBienvenid@ "+tecnico.getNombre()+", tiene usted perfil de técnico"); //TODO ARREGLAR ESTA PARTE DEL CÓDIGO PARA QUE FUNCIONE BIEN
        System.out.println("\tActualmente, tiene "+daoTecnico.incidenciasAbiertas(tecnico,dao)+" incidencias abiertas y "+daoTecnico.incidenciasCerradas(tecnico,dao)+" incidencias cerradas");

        System.out.println("""
                
                     ----------------------------------------------------
                      [1]. Consultar las incidencias asignadas no resueltas
                     ----------------------------------------------------
                      [2]. Marcar una incidencia como resuelta
                     ----------------------------------------------------
                      [3]. Consultar mis incidencias cerradas
                     ----------------------------------------------------
                      [4]. Mostrar mi perfil
                     ----------------------------------------------------
                      [5]. Cambiar clave de acceso
                     ----------------------------------------------------
                      [6]. Cerrar sesión
                     ----------------------------------------------------
                
                ╚═════════════════════════════════════════════════════════════════════════════════╝
                """+ANSI_RESET);


    }

    public void pintaMenuAdmin(Admin admin, DaoGestionSQL daoGestionSQL, DAOManager dao){

        System.out.println(ANSI_PURPLE+"""
              ╔═════════════════════════════════════════════════════════════════════════════════╗  
                ║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║ MENU DE ADMIN ║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║ """);

        System.out.println("\n\tBienvenid@ "+admin.getNombre()+", tiene usted perfil de administrador"); //TODO ARREGLAR ESTA PARTE DEL CÓDIGO PARA QUE FUNCIONE BIEN
        System.out.println("\tActualmente, hay "+daoGestionSQL.incidenciasAbiertas(dao)+" incidencias abiertas, de las cuales\n\t" +
                (daoGestionSQL.incidenciasAbiertas(dao)-daoGestionSQL.incidenciasAbiertasAsignadas(dao))+" no están asignadas");

        System.out.println("""
                
                     ----------------------------------------------------
                      [1]. Consultar todas las incidencias abiertas
                     ----------------------------------------------------
                      [2]. Consultar incidencias cerradas
                     ----------------------------------------------------
                      [3]. Consultar incidencias por término
                     ----------------------------------------------------
                      [4]. Consultar los técnicos
                     ----------------------------------------------------
                      [5]. Asignar una incidencia a un técnico
                     ----------------------------------------------------
                      [6]. Dar de alta a un técnico
                     ----------------------------------------------------
                      [7]. Borrar un técnico
                     ----------------------------------------------------
                      [8]. Consultar los usuarios
                     ----------------------------------------------------
                      [9]. Estadísticas de la aplicación
                     ----------------------------------------------------
                      [10]. Cerrar sesión
                     ----------------------------------------------------
                
                ╚═════════════════════════════════════════════════════════════════════════════════╝
                """+ANSI_RESET);


    }
}
