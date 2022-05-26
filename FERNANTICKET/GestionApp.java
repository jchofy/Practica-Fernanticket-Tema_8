package FERNANTICKET;

import DAO.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GestionApp {

    DAOManager dao = DAOManager.getSinglentonInstance();
    FernanTicketVista vista = new FernanTicketVista();
    DaoTecnicoSQL daoTecnicoSQL = new DaoTecnicoSQL();
    DaoUsuarioSQL daoUsuarioSQL = new DaoUsuarioSQL();
    DaoGestionSQL daoGestionSQL = new DaoGestionSQL();
    DaoIncidenciadataclassSQL daoIncidenciadataclassSQL = new DaoIncidenciadataclassSQL();
    Scanner s = new Scanner(System.in);

    HashMap<String,Usuario> usuarioHashMap = new HashMap<>();
    HashMap<String,Tecnico> tecnicoHashMap = new HashMap<>();
    HashMap<String,Admin> adminHashMapHashMap = new HashMap<>();

    public void  inicia(){

        daoGestionSQL.borrarDatos(dao);

        //Insertar un administrador
        Admin admin1 = new Admin(1,"Juan","Delgado Higueras","12345","jchofy98@gmail.com");
        daoGestionSQL.insertaAdmin(admin1,dao);
        adminHashMapHashMap.put(admin1.getEmail(),admin1);

        //Inserta un técnico
        Tecnico tecnico1 = new Tecnico(2,"Antonio","Martos pérez","12345","antonio@gmail.com");
        daoGestionSQL.insertaTecnico(tecnico1,dao);
        tecnicoHashMap.put(tecnico1.getEmail(),tecnico1);

        //Inserta un usuario
        Usuario usuario1 = new Usuario(3,"Manolo","Carrillo Martínez","12345","manolo@gmail.com");
        daoGestionSQL.insertaUsuario(usuario1,dao);
        usuarioHashMap.put(usuario1.getEmail(),usuario1);



        int opcionmenuprincipal = -1;
        String correo,clave;

        do {
            vista.pintaMenuInicio();
            vista.pideDato("Introduce una opción: ");

            try {
                opcionmenuprincipal = Integer.parseInt(s.nextLine());
            }catch (NumberFormatException e){
                vista.mensajeError("Debes escribir un número");
            }


            switch (opcionmenuprincipal){

                case 1:
                    vista.pideDato("Escribe tu correo: ");
                    correo = s.nextLine();
                    vista.pideDato("Escribe tu clave de acceso: ");
                    clave = s.nextLine();

                    //Menú Usuario
                    if (usuarioHashMap.containsKey(correo) && usuarioHashMap.get(correo).getClave().equals(clave)){

                        int opcionmenusuario = -1;

                        do {
                            vista.pintaMenuUsuario((usuarioHashMap.get(correo)),daoUsuarioSQL,dao);
                            vista.pideDato("Introduce una opción: ");
                            try {
                                opcionmenusuario = Integer.parseInt(s.nextLine());
                            }catch (NumberFormatException e){
                                vista.mensajeError("Debes escribir un número");
                            }



                            switch (opcionmenusuario){
                                case 1:
                                    daoUsuarioSQL.insertaIncidencia(creaIncidencia(usuarioHashMap.get(correo)),dao);
                                    break;
                                case 2:
                                    for (Incidencia i:daoGestionSQL.buscaIncidenciasAbiertasByUsuario(usuarioHashMap.get(correo).getId(),dao)
                                         ) {
                                        System.out.println(i);
                                    }
                                    break;
                                case 3:
                                    for (IncidenciaDataClass i:daoGestionSQL.buscaIncidenciasCerradasByUsuario(usuarioHashMap.get(correo).getId(),dao)
                                         ) {
                                        System.out.println(i);
                                    }
                                    break;
                                case 4:
                                    System.out.println(usuarioHashMap.get(correo));
                                    break;
                                case 5:
                                    vista.pideDato("Escribe la nueva contraseña");
                                    String pass= s.nextLine();

                                    if (daoUsuarioSQL.cambiarPasswordUsuario(pass,usuarioHashMap.get(correo),dao)){
                                        usuarioHashMap.get(correo).setClave(pass);
                                    }else vista.mensajeError("No se ha podido cambiar la contraseña");

                                    break;
                                case 6:
                                    vista.mensajeError("Saliendo del usuario");
                                    break;
                                default:
                                    vista.mensajeError("Escribe una opción correcta");
                                    break;
                            }

                        }while (opcionmenusuario!=6);

                    }
                    //Menú Técnico
                    else if (tecnicoHashMap.containsKey(correo) && tecnicoHashMap.get(correo).getClave().equals(clave)){
                        int opcionmenutecnico = -1;

                        do {
                            vista.pintaMenuTecnico(tecnicoHashMap.get(correo),daoTecnicoSQL,dao);
                            vista.pideDato("Introduce una opción: ");
                            try {
                                opcionmenutecnico = Integer.parseInt(s.nextLine());
                            }catch (NumberFormatException e){
                                vista.mensajeError("Debes escribir un número");
                            }


                            switch (opcionmenutecnico){
                                case 1:
                                    vista.mensaje("Mis incidencias no resueltas");
                                    System.out.println(daoGestionSQL.buscaIncidenciasAbiertasByTecnico(tecnicoHashMap.get(correo).getId(),dao));
                                    break;
                                case 2:
                                    vista.mensaje("Cerrar una incidencia ------");
                                    vista.mensaje("Mis incidencias no resueltas");
                                    System.out.println(daoGestionSQL.buscaIncidenciasAbiertasByTecnico(tecnicoHashMap.get(correo).getId(),dao));
                                    vista.pideDato("Introduzca el id de la incidencia a cerrar");
                                    int idincidencia=0;
                                    try {
                                        idincidencia = Integer.parseInt(s.nextLine());
                                    }catch (NumberFormatException e){
                                        vista.mensajeError("Debes escribir un número");
                                    }


                                    System.out.println(daoTecnicoSQL.buscaIncidenciaById(idincidencia,tecnicoHashMap.get(correo),dao));
                                    vista.pideDato("Introduzca la solucion a la incidencia");
                                    String solucion=s.nextLine();
                                    System.out.println();
                                    daoGestionSQL.cierraIncidencia(idincidencia,tecnicoHashMap.get(correo).getId(),solucion,dao);
                                    daoIncidenciadataclassSQL.insertaIncidenciadataclass(daoGestionSQL.buscaIncidencia(idincidencia,dao),daoGestionSQL.buscaUsuarioPorIncidencia(idincidencia,dao),tecnicoHashMap.get(correo),dao);
                                    break;
                                case 3:
                                    vista.mensaje("Incidencias Cerradas");
                                    System.out.println(daoGestionSQL.buscaIncidenciasCerradasByTecnico(tecnicoHashMap.get(correo).getId(),dao));
                                    break;
                                case 4:
                                    vista.mensaje("Perfil: ");
                                    System.out.println(tecnicoHashMap.get(correo));
                                    break;
                                case 5:

                                    vista.pideDato("Escribe la nueva contraseña");
                                    String pass= s.nextLine();

                                    if ( daoTecnicoSQL.cambiarPasswordTecnico(pass,tecnicoHashMap.get(correo),dao)){
                                        tecnicoHashMap.get(correo).setClave(pass);
                                    }else vista.mensajeError("No se ha podido cambiar la contraseña");

                                    break;
                                case 6:
                                    vista.mensajeError("Saliendo del técnico");
                                    break;
                                default:
                                    vista.mensajeError("Escribe una opción correcta");
                                    break;
                            }

                        }while (opcionmenutecnico!=6);
                    }
                    //Menú admin
                    else if (adminHashMapHashMap.containsKey(correo) && adminHashMapHashMap.get(correo).getClave().equals(clave)){
                        int opcionmenuadmin = -1;

                        do {
                            vista.pintaMenuAdmin(adminHashMapHashMap.get(correo),daoGestionSQL,dao);
                            vista.pideDato("Introduce una opción: ");

                            try {
                                opcionmenuadmin = Integer.parseInt(s.nextLine());
                            }catch (NumberFormatException e){
                                vista.mensajeError("Debes escribir un número");
                            }

                            switch (opcionmenuadmin){
                                case 1:
                                    vista.mensaje("Todas las incidencias abiertas");
                                    System.out.println(daoGestionSQL.buscaIncidenciasAbiertas(dao));
                                    break;
                                case 2:
                                    vista.mensaje("Incidencias cerradas");
                                    System.out.println(daoGestionSQL.buscaIncidenciasCerradas(dao));
                                    break;
                                case 3:
                                    vista.mensaje("Consulta incidencia por término");
                                    String termino=s.nextLine();
                                    System.out.println(daoGestionSQL.buscaIncidenciasByTerm(termino,dao));
                                    break;
                                case 4:
                                    vista.mensaje("Todos los técnicos ");
                                    for (Tecnico c :daoGestionSQL.buscartodosTecnicos(dao)
                                         ) {
                                        System.out.println(c);
                                    }
                                    break;
                                case 5:
                                    vista.mensaje("Técnicos para asignar: ");
                                    for (Tecnico c :daoGestionSQL.buscartodosTecnicos(dao)
                                    ) {
                                        System.out.println(c);
                                    }
                                    vista.pideDato("Inserta el id del técnico");
                                    int idtecnico = 0;
                                    try {
                                        idtecnico = Integer.parseInt(s.nextLine());
                                    }catch (NumberFormatException e){
                                        vista.mensajeError("Debes escribir un número");
                                    }

                                    vista.mensaje("Incidencias para asignar: ");
                                    for (IncidenciaDataClass c :daoGestionSQL.buscaIncidenciasSinAsignar(dao)){
                                        System.out.println(c);
                                    }
                                    vista.pideDato("Inserta el id de la incidencia");
                                    int idIncidencia =0;
                                    try {
                                        idIncidencia = Integer.parseInt(s.nextLine());
                                    }catch (NumberFormatException e){
                                        vista.mensajeError("Debes escribir un número");
                                    }
                                    daoGestionSQL.asignaIncidencia(idtecnico,idIncidencia,dao);
                                    break;
                                case 6:
                                    vista.mensaje("Registra un nuevo técnico");
                                    //Si se introduce correctamente el técnico en la base de datos, lo introducimos en el hashmap
                                    Tecnico tecnico = creaTecnico();
                                        if (daoGestionSQL.insertaTecnico(tecnico,dao)){
                                        tecnicoHashMap.put(tecnico.getEmail(),tecnico);
                                    }
                                    break;
                                case 7:
                                        vista.mensaje("Borra un técnico: ");
                                        vista.pideDato("Escribe el id del técnico a borrar");
                                        int idborrartecnico = 0;
                                    try {
                                        idborrartecnico = Integer.parseInt(s.nextLine());
                                    }catch (NumberFormatException e){
                                        vista.mensajeError("Debes escribir un número");
                                    }
                                        daoGestionSQL.deletetecnico(idborrartecnico,dao);
                                    break;
                                case 8:
                                    vista.mensaje("Todos los usuarios: ");
                                    for (Usuario usuario :daoGestionSQL.buscartodosUsuarios(dao)
                                    ) {
                                        System.out.println(usuario);
                                    }
                                    break;
                                case 9:
                                    vista.mensaje("Estadísticas de la aplicación: ");
                                    System.out.println("Prioridad media de la app: "+daoGestionSQL.prioridadMediaApp(dao));
                                    System.out.println("Incidencias abiertas: "+daoGestionSQL.incidenciasAbiertas(dao));
                                    System.out.println("Incidencias cerradas: "+daoGestionSQL.incidenciasCerradas(dao));
                                    System.out.println("Incidencias Abiertas sin asignar: "+(daoGestionSQL.incidenciasAbiertas(dao)-daoGestionSQL.incidenciasAbiertasAsignadas(dao)));
                                    System.out.println("Total incidencias: "+(daoGestionSQL.incidenciasAbiertas(dao)+daoGestionSQL.incidenciasCerradas(dao)));
                                    break;
                                case 10:
                                    vista.mensajeError("Saliendo del administrador");
                                    break;
                                default:
                                    vista.mensajeError("Escribe una opción correcta");
                                    break;
                            }

                        }while (opcionmenuadmin!=10);

                    }

                    else vista.mensajeError("No coinciden los datos");

                    break;
                case 2:
                    Usuario u = creaUsuario();
                    //Si se introduce correctamente el usuario en la base de datos, lo introducimos en el hashmap
                    if (daoGestionSQL.insertaUsuario(u,dao)){
                        usuarioHashMap.put(u.getEmail(),u);
                    }
                    break;
                case 3:
                    vista.mensajeError("...Cerrando aplicación...");
                    break;
                default:
                    vista.mensajeError("Escribe una opción correcta");
                    break;
            }

        }while (opcionmenuprincipal!=3);
    }

    public Usuario creaUsuario(){
        String nombre,apel,clave,correo;

        //Pedimos los datos para crear nuestro usuario
        vista.pideDato("Escribe el nombre del usuario: ");
        nombre= s.nextLine();
        vista.pideDato("Escribe los apellidos del usuario: ");
        apel= s.nextLine();
        vista.pideDato("Escribe el corre del usuario: ");
        correo= s.nextLine();

        //Verificamos que el correo no lo tiene ningún otro usuario asignado
        if (usuarioHashMap.containsKey(correo)){
            do {
                vista.mensajeError("Elige otro correo, ese ya está en uso");
                vista.pideDato("Escribe el corre del usuario: ");
                correo= s.nextLine();

            }while (usuarioHashMap.containsKey(correo));
        }

        vista.pideDato("Escribe la clave del usuario: ");
        clave= s.nextLine();

        Usuario usuario= new Usuario(daoGestionSQL.generaIdUser(dao),nombre,apel,clave,correo);
        return usuario;
    }

    public Incidencia creaIncidencia(Usuario usuario){
        String descripcion;
        int prioridad=0;

        //Pedimos los datos para crear nuestro usuario
        vista.pideDato("Escribe la descripcion de la incidencia: ");
        descripcion= s.nextLine();
        vista.pideDato("Escribe la prioridad: ");
        try {
            prioridad = Integer.parseInt(s.nextLine());
        }catch (NumberFormatException e){
            vista.mensajeError("Debes escribir un número");
        }



        Incidencia incidencia= new Incidencia(daoGestionSQL.generaIdIncidencia(dao),descripcion,prioridad,usuario.getId());
        return incidencia;
    }

    public Tecnico creaTecnico(){
        String nombre,apel,clave,correo;

        //Pedimos los datos para crear nuestro técnico
        vista.pideDato("Escribe el nombre del técnico: ");
        nombre= s.nextLine();
        vista.pideDato("Escribe los apellidos del técnico: ");
        apel= s.nextLine();
        vista.pideDato("Escribe el correo del técnico: ");
        correo= s.nextLine();

        //Verificamos que el correo no lo tiene ningún otro tecnico asignado
        if (tecnicoHashMap.containsKey(correo)){
            do {
                vista.mensajeError("Elige otro correo, ese ya está en uso");
                vista.pideDato("Escribe el corre del usuario: ");
                correo= s.nextLine();

            }while (tecnicoHashMap.containsKey(correo));
        }

        vista.pideDato("Escribe la clave del técnico: ");
        clave= s.nextLine();

        Tecnico tecnico= new Tecnico(daoGestionSQL.generaIdUser(dao),nombre,apel,clave,correo);
        return tecnico;
    }

}
