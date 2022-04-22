package com.company;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;


public class GestionApp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private ArrayList<Usuario> usuarios;
    private ArrayList<Tecnico> tecnicos;
    private ArrayList<Admin> admins;
    private HashMap<String, Usuario> loginapp;
    private HashMap<String, Tecnico> loginapptecnico;
    private HashMap<String, Admin> loginappadmin;
    IncidenciaDataClass historicoincidencias;

    //Añadimos una instancia de la clase properties para utilizar el archivo properties

    Properties properties = new Properties();

    public Scanner scanner = new Scanner(System.in);

    public GestionApp() throws FileNotFoundException {

    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    Vista vista = new Vista();
    Notificaciones notificacion = new Notificaciones();

    FileOutputStream fos = new FileOutputStream("propiedades.properties");

    public void inicia() throws Exception {


        //Permitir acceso al sistema
        properties.setProperty("Invitado","No");
        //Localización del archivo properties
        properties.setProperty("Ubicacion_archivo_properties","propiedades.properties");



        try {
            properties.store(fos, "Fichero de configuración");
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            properties.load(new FileReader("propiedades.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }


        usuarios = new ArrayList<>();
        tecnicos = new ArrayList<>();
        admins = new ArrayList<>();
        loginapp = new HashMap<>();
        loginapptecnico = new HashMap<>();
        loginappadmin = new HashMap<>();
        historicoincidencias = new IncidenciaDataClass();
        //Instanciamos un objeto de la clase log para registrar la actividad de los usuarios
        Log log=new Log();


        //Creamos un nuevo admin por defecto

        /*Introdicimos valores*/
        Admin admin = new Admin(1, "Juan", "Delgado Higueras", "admin", "admin");
        Usuario usuario = new Usuario("Alejandro", "Martos Ruíz", "12345", "jchofy98@gmail.com");
        Tecnico tecnico = new Tecnico("Manuel", "Guillermo Pérez", "12345", "jchofy@gmail.com");
        loginapp.put("jchofy98@gmail.com", usuario);
        usuarios.add(usuario);
        tecnicos.add(tecnico);
        loginapptecnico.put("jchofy@gmail.com", tecnico);
        admins.add(admin);
        loginappadmin.put(admin.getEmail(), admin);
        Incidencia inici = new Incidencia("No me van los auriculares", 2);
        inici.setId(generaIdIncidencia());
        usuario.getIncidenciasUsuario().add(inici);




        int seleccion = 0;

        do {


            do {
                pintaLogin();
                try {
                    seleccion = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Debes de introducir un número");
                }

            } while (seleccion < 1 || seleccion > 4);

            /*------------------*/
            String nombre;
            String apellidos;
            String clave;
            String email;
            //Para comprobar que este usuario no lo tiene otro usuario

            Boolean emailunico = true;

            switch (seleccion) {
                case 1:
                    boolean login;
                    //Booleano para saber si ya se ha accedido a un usuario
                    boolean acceso = false;
                    System.out.println("Escriba su correo electrónico: ");
                    email = scanner.nextLine();
                    System.out.println("Escriba su contraseña: ");
                    clave = scanner.nextLine();


                    //Acceso como administrador
                    if (loginappadmin.containsKey(email) && acceso == false) {
                        if (loginappadmin.get(email).getClave().equals(clave)) {
                            System.out.println("Has iniciado sesión como Administrador");
                            acceso = true;
                            log.escribir("Inicio de sesión| "+loginappadmin.get(email).getNombre()+"| ADMIN |Fecha de inicio de sesion:"+ LocalDateTime.now());

                            int eleccion = 0;
                            do {
                                pintaMenuAdmin(email);
                                try {
                                    eleccion = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Debes de introducir un número");
                                }

                                switch (eleccion) {
                                    case 1:
                                        System.out.println("Incidencias Abiertas");
                                        for (Incidencia a : buscaTodasIncidenciasAbiertas()
                                        ) {
                                            System.out.println(a);
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Incidencias Cerradas");
                                        for (Incidencia a : buscaTodasIncidenciasCerradas()
                                        ) {
                                            System.out.println(a);
                                        }
                                        break;
                                    case 3:
                                        System.out.println("Consulta incidencia por término: ");
                                        System.out.println("Inserta el término a mostrar");
                                        String termino = scanner.nextLine();
                                        System.out.println(buscaincidenciasByTerm(termino));
                                        break;
                                    case 4:
                                        System.out.println("Consulta los técnicos");
                                        for (Tecnico t : tecnicos
                                        ) {
                                            System.out.println(t);
                                        }
                                        break;
                                    case 5:
                                        System.out.println("Asigna incidencia a técnico");
                                        for (Incidencia a : buscaTodasIncidenciasAbiertas()
                                        ) {
                                            if (a.isAsignada() == false) {
                                                System.out.println(a);
                                            }
                                        }

                                        System.out.println("Indica el id de la incidencia");
                                        int idincidencia = Integer.parseInt(scanner.nextLine());

                                        for (Tecnico a : tecnicos
                                        ) {
                                            System.out.println(tecnicos);
                                        }
                                        System.out.println("Indica el id del técnico");
                                        int idtecnico = Integer.parseInt(scanner.nextLine());

                                        //Asignamos la incidencia al técnico
                                        asignaIncidencia(idincidencia, idtecnico);
                                        System.out.println("Incidencia asignada con éxito");
                                        buscaincidenciaId(idincidencia).setEstado("Asignada sin resolver");
                                        String mensajetelegram = "Nueva Incidencia Asignada:" +
                                                "IdIncidencia: " + idincidencia +
                                                " IdTécnico: " + idtecnico;
                                        log.escribir("Nueva incidencia asignada| Nombre del técnico "+buscatecnicoid(idtecnico).getNombre()+" |Fecha de asignación:"+ LocalDateTime.now());
                                        notificacion.enviaMensajetelegram(mensajetelegram);

                                        break;
                                    case 6:
                                        System.out.println("Dar de alta un técnico");
                                        System.out.println("Escribe su nombre:");
                                        nombre = scanner.nextLine();
                                        System.out.println("Escribe sus apellidos:");
                                        apellidos = scanner.nextLine();
                                        System.out.println("Escribe el correo electrónico:");
                                        String correo = scanner.nextLine();
                                        System.out.println("Escribe la contraseña:");
                                        clave = scanner.nextLine();
                                        tecnico = new Tecnico(nombre, apellidos, clave, correo);
                                        loginapptecnico.put(correo, tecnico);
                                        tecnicos.add(tecnico);
                                        System.out.println("Técnico Asignado con éxito");
                                        break;

                                    case 7:
                                        int i = 1;
                                        for (Tecnico a : tecnicos
                                        ) {
                                            System.out.println(i + ". " + a);
                                        }

                                        System.out.println("Selecciona un técnico para borrar");
                                        try {
                                            eleccion = Integer.parseInt(scanner.nextLine());
                                        } catch (NumberFormatException e) {
                                            System.out.println("Solo puedes introducir un número");
                                        }


                                        tecnicos.remove(eleccion - 1);


                                        break;
                                    case 8:
                                        for (Usuario u : usuarios
                                        ) {
                                            System.out.println(u);
                                        }
                                        break;
                                    case 9:
                                        System.out.println("Estadísticas de la aplicación");
                                        System.out.println("-------------------------------------");
                                        System.out.println("Incidencias totales abiertas: " + incidenciasAbiertas());
                                        System.out.println("Incidencias abiertas asignadas: " + incidenciasAbiertasAsignadas());
                                        System.out.println("Incidencias abiertas sin asignar: " + (incidenciasAbiertas() - incidenciasAbiertasAsignadas()));
                                        System.out.println("Incidencias totales cerradas: " + incidenciasCerradas());
                                        System.out.println("Prioridad media App: " + prioridadmediaapp());
                                        break;
                                    case 10:
                                        BufferedReader bf = new BufferedReader(new FileReader("propiedades.properties"));
                                        try {
                                            String linea = bf.readLine();

                                            do {

                                                if (linea.contains("@")){}
                                                else System.out.println(linea);


                                                linea = bf.readLine();
                                            }while (linea!=null);

                                            System.out.println("Acceso de usuarios");
                                            bf = new BufferedReader(new FileReader("propiedades.properties"));
                                            linea = bf.readLine();

                                            do {

                                                if (linea.contains("@")){
                                                    System.out.println(linea);
                                                }

                                                linea = bf.readLine();
                                            }while (linea!=null);
                                            bf.close();
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }

                                        break;
                                    case 11:
                                        String csv="";
                                        for (Incidencia a: incidenciasAbiertaSinAsignar()
                                             ) {
                                            csv=""+a.getId()+","+a.getDescripcion()+","+a.getIdUsuario()+";";
                                        }

                                        i = 1;
                                        for (Tecnico a : tecnicos
                                        ) {
                                            System.out.println(i + ". " + a);
                                        }

                                        System.out.println("Selecciona un técnico para enviar el listado excel");
                                        try {
                                            eleccion = Integer.parseInt(scanner.nextLine());
                                        } catch (NumberFormatException e) {
                                            System.out.println("Solo puedes introducir un número");
                                        }

                                        notificacion.enviarConGMail(tecnicos.get(eleccion-1).getEmail(),"Correo Incidencias Abiertas",csv);

                                        break;
                                    case 12:
                                        System.out.println("Has cerrado sesión, pulse enter para continuar");
                                        log.escribir("Cierre de sesión| "+loginappadmin.get(email).getNombre()+"| ADMIN |Fecha de cierre de sesion:"+ LocalDateTime.now());
                                        scanner.nextLine();
                                        break;

                                }
                            } while (eleccion != 12);
                        }
                    }

                    //Acceso como técnico
                    if (loginapptecnico.containsKey(email) && acceso == false) {
                        if (loginapptecnico.isEmpty() == false && loginapptecnico.get(email).getClave().equals(clave)) {
                            System.out.println("Has iniciado sesión como Técnico");
                            log.escribir("Inicio de sesión| "+loginapptecnico.get(email).getNombre()+"| TÉCNICO |Fecha de inicio de sesion:"+ LocalDateTime.now());
                            acceso = true;
                            int eleccion = 0;
                            do {
                                pintaMenuTecnico(email);
                                try {
                                    eleccion = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Debes de introducir un número");
                                }

                                switch (eleccion) {
                                    case 1:
                                        /*Este método ya funciona*/
                                        for (Incidencia a : loginapptecnico.get(email).getIncidenciasTecnico()
                                        ) {
                                            if (a.isEstaResuelta() == false) {
                                                System.out.println(a);
                                            }
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Marcar incidencia como resuelta");
                                        for (Incidencia a : loginapptecnico.get(email).getIncidenciasTecnico()
                                        ) {
                                            if (a.isEstaResuelta() == false) {
                                                System.out.println(a);
                                            }
                                        }
                                        System.out.println("Indica el id de la incidencia");
                                        int idinci = 0;
                                        try {
                                            idinci = Integer.parseInt(scanner.nextLine());
                                        } catch (NumberFormatException e) {
                                            System.out.println("Debes de introducir un número");
                                        }

                                        System.out.println("Indica el id del usuario que la creó");
                                        int idusu = 0;
                                        try {
                                            idusu = Integer.parseInt(scanner.nextLine());
                                        } catch (NumberFormatException e) {
                                            System.out.println("Debes de introducir un número");
                                        }

                                        System.out.println("Escribe la solución al cliente");
                                        String solucion = scanner.nextLine();
                                        cierraIncidencia(idusu, idinci, solucion);
                                        String mensajetelegram = "Incidencia Cerrada " +
                                                "Id Usuario: " + idusu +
                                                "Id Incidencia: " + idinci;
                                        log.escribir("Incidencia cerrada| "+buscaUsuariobyIncidencia(idinci).getNombre()+" |Fecha que se cerró la incidencia:"+ LocalDateTime.now());
                                        notificacion.enviaMensajetelegram(mensajetelegram);



                                        break;

                                    case 3:
                                        System.out.println("Incidencias Cerradas");
                                        for (Incidencia a : loginapptecnico.get(email).getIncidenciasTecnico()) {
                                            if (a.isEstaResuelta()) {
                                                System.out.println(a);
                                            }
                                        }
                                        break;
                                    case 4:
                                        System.out.println("Mi perfil:");
                                        System.out.println(loginapptecnico.get(email));
                                        break;
                                    case 5:
                                        System.out.println("Escribe tu nueva contraseña");
                                        String pass = scanner.nextLine();
                                        loginapptecnico.get(email).setClave(pass);
                                        System.out.println("Has cambiado tu contraseña con éxito");
                                        break;
                                    case 6:
                                        System.out.println("Has cerrado sesión, pulse enter para continuar");
                                        log.escribir("Cierre de sesión| "+loginapptecnico.get(email).getNombre()+"| TÉCNICO |Fecha de cierre de sesion:"+ LocalDateTime.now());
                                        scanner.nextLine();
                                        break;

                                }
                            } while (eleccion != 6);
                        } else System.out.println("La contraseña introducida no es correcta");
                    }

                    //Acceso como usuario
                    if (loginapp.isEmpty() == false && loginapp.containsKey(email) && acceso == false) {
                        if (loginapp.get(email).getClave().equals(clave)) {
                            String fecha=""+LocalDate.now();
                            properties.computeIfAbsent(email,value-> fecha);

                            fos = new FileOutputStream("propiedades.properties");

                            try {
                                properties.store(fos, "Fichero de configuración");
                            }catch (IOException e){
                                e.printStackTrace();
                            }

                            System.out.println("Iniciaste sesión por útima vez el día "+ properties.getProperty(loginapp.get(email).getEmail()));
                            System.out.println("Has iniciado sesión como Usuario Normal");
                            log.escribir("Inicio de sesión| "+loginapp.get(email).getNombre()+"| USUARIO NORMAL |Fecha de inicio de sesion:"+ LocalDateTime.now());
                            acceso = true;

                            int eleccion = 0;
                            do {
                                pintaMenuUsuario(loginapp.get(email).getEmail());
                                try {
                                    eleccion = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Debes de introducir un número");
                                }

                                switch (eleccion) {
                                    case 1:
                                        System.out.println("Registra una nueva incidencia");
                                        System.out.println("Escribe el asunto");
                                        String asunto = scanner.nextLine();
                                        System.out.println("Escribe la prioridad 1-10");
                                        int prioridad = 0;
                                        try {
                                            prioridad = Integer.parseInt(scanner.nextLine());
                                        } catch (NumberFormatException a) {
                                            System.out.println("Debes de elegir un número");
                                        }


                                        Incidencia incidencia = new Incidencia(asunto, prioridad);
                                        incidencia.setId(generaIdIncidencia());
                                        incidencia.setIdUsuario(loginapp.get(email).getId());
                                        loginapp.get(email).getIncidenciasUsuario().add(incidencia);
                                        System.out.println("Has añadido una nueva incidencia");

                                        String mensajetelegram = "Nueva Incidencia de " +
                                                loginapp.get(email).getNombre() +
                                                " Correo: " + loginapp.get(email).getEmail();

                                        notificacion.enviaMensajetelegram(mensajetelegram);
                                        log.escribir("Nueva incidencia creada| Nombre del usuario "+loginapp.get(email).getNombre()+" |Fecha de creación:"+ LocalDateTime.now());
                                        break;
                                    case 2:
                                        System.out.println("Incidencias Abiertas");
                                        for (Incidencia a : loginapp.get(email).getIncidenciasUsuario()) {
                                            if (a.isEstaResuelta() == false) {
                                                System.out.println(a);
                                            }
                                        }
                                        break;
                                    case 3:
                                        System.out.println("Incidencias Cerradas");
                                        for (Incidencia a : loginapp.get(email).getIncidenciasUsuario()) {
                                            if (a.isEstaResuelta()) {
                                                System.out.println(a);
                                            }
                                        }
                                        break;
                                    case 4:
                                        System.out.println("Mi perfil:");
                                        System.out.println(loginapp.get(email));
                                        break;
                                    case 5:
                                        System.out.println("Escribe tu nueva contraseña");
                                        String pass = scanner.nextLine();
                                        loginapp.get(email).setClave(pass);
                                        System.out.println("Has cambiado tu contraseña con éxito");
                                        break;
                                    case 6:
                                        System.out.println("Has cerrado sesión, pulse enter para continuar");
                                        log.escribir("Cierre de sesión| "+loginapp.get(email).getNombre()+"| USUARIO NORMAL |Fecha de cierre de sesion:"+ LocalDateTime.now());
                                        scanner.nextLine();
                                        break;

                                }
                            } while (eleccion != 6);


                        }
                    }

                    if (acceso == false) System.out.println("Los datos introducidos no son correctos");
                    break;

                case 2:
                    System.out.println("Escribe tu nombre:");
                    nombre = scanner.nextLine();
                    System.out.println("Escribe tus apellidos:");
                    apellidos = scanner.nextLine();

                    do {
                        System.out.println("Escribe tu correo de acceso:");
                        email = scanner.nextLine();


                        if (loginapp.containsKey(email)) {
                            System.out.println("Este email ya está cogido");
                        }

                        if (loginapp.containsKey(email) == false) {
                            System.out.println("Este email está disponible");
                            emailunico = false;
                        }

                        if (loginapp.isEmpty() == true) {
                            emailunico = false;
                        }
                    } while (emailunico);

                    System.out.println("Escribe la clave de acceso: ");
                    clave = scanner.nextLine();

                    //Añadimos el usuario al arraylist de usuarios
                    usuarios.add(usuario);

                    //Colocamos los datos de acceso al hasmap para evitar correos repetidos
                    loginapp.put(email, usuario);

                    System.out.println("Usuario Creado");


                    //Enviamos Token al correo
                    boolean token = true;
                    System.out.println("Hemos enviado un código a tu correo para confirmar tu cuenta");
                    int tokencorreo = (int) (Math.random() * 10000) + 1000;


                    notificacion.enviarConGMail(loginapp.get(email).getEmail(), "Token Verificación FernandTicket", String.valueOf(tokencorreo));

                    /*Envío el código por consola para que sea más fácil (Igualmente envía mensajes al correo*/
                    System.out.println("El token de verificación es: " + tokencorreo);

                    do {
                        System.out.println("Escribe el código que hemos enviado a su correo");
                        String tokenintroducido = scanner.nextLine();

                        if (tokenintroducido.equals(String.valueOf(tokencorreo))) {
                            System.out.println("Has confirmado tu cuenta en 2 pasos");
                            token = false;
                        } else System.out.println("El código de verificación no coincide");
                    } while (token);


                    System.out.println("Pulse Enter para continuar");
                    scanner.nextLine();

                    //Notificación a telegram cuando un usuario nuevo se registra
                    String mensajetelegram = "Nuevo Usuario Registrado " +
                            "Nombre: " + loginapp.get(email).getNombre() +
                            " Correo: " + loginapp.get(email).getEmail();

                    notificacion.enviaMensajetelegram(mensajetelegram);

                    break;
                case 3:
                    if (properties.getProperty("Invitado").equalsIgnoreCase("No")){
                        System.out.println("No está habilitado el modo invitado");
                    }

                    if (properties.getProperty("Invitado").equalsIgnoreCase("Si")){
                        System.out.println("Has entrado en el modo invitado");

                        incidenciasAbiertas();
                    }
                    break;

                case 4:
                    System.out.println("Has seleccionado apagar el programa, pulsa enter para continuar");

                    scanner.nextLine();
                    break;

            }

            salvar_usuarios();
            salvar_tecnicos();
        } while (seleccion != 4);


    }

    public boolean insertaUsuario(String nombre, String apellidos, String clave, String email) {
        boolean usuarioinsertado = true;

        if (loginapp.containsKey(email)) {
            usuarioinsertado = false;
        } else {
            Usuario usuario = new Usuario(nombre, apellidos, clave, email);

            //Añadimos el usuario al arraylist de usuarios
            usuarios.add(usuario);

            //Colocamos los datos de acceso al hasmap para evitar correos repetidos
            loginapp.put(email, usuario);
        }
        return usuarioinsertado;
    }

    public boolean insertaTecnico(String nombre, String apellidos, String clave, String email) {
        boolean tecnicoInsertado = true;

        if (loginapptecnico.containsKey(email)) {
            tecnicoInsertado = false;
        } else {
            Tecnico tecnico = new Tecnico(nombre, apellidos, clave, email);

            //Añadimos el Tecnico al arraylist de tecnicos
            tecnicos.add(tecnico);

            //Colocamos los datos de acceso al hasmap para evitar correos repetidos
            loginapptecnico.put(email, tecnico);
        }
        return tecnicoInsertado;
    }

    public boolean insertaIncidencia(String asunto, int prioridad) {

        boolean incidenciainsertada = true;
        Incidencia inicidencia = new Incidencia(asunto, prioridad);

        int seleccion = 0;
        int i = 1;
        for (Usuario u : usuarios) {

            System.out.println(i + " ." + u);
            i++;
        }
        System.out.println("Selecciona al usuario que quieres insertar la incidencia");
        try {
            seleccion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException a) {
            System.out.println("Debes de elegir un número");
        }


        if (seleccion >= 1 && seleccion <= usuarios.size()) {
            usuarios.get(seleccion - 1).getIncidenciasUsuario().add(inicidencia);
        } else {
            System.out.println("La incidencia no ha podido ser asignada con éxito");
            incidenciainsertada = false;
        }
        return incidenciainsertada;
    }

    public ArrayList<Incidencia> buscaTodasIncidenciasAbiertas() {

        ArrayList<Incidencia> incidenciasAbiertas = new ArrayList<>();
        for (Usuario a : usuarios) {
            for (int i = 0; i < a.getIncidenciasUsuario().size(); i++) {
                if (!a.getIncidenciasUsuario().get(i).isEstaResuelta()) {
                    incidenciasAbiertas.add(a.getIncidenciasUsuario().get(i));
                }
            }
        }
        return incidenciasAbiertas;
    }

    public ArrayList<Incidencia> buscaTodasIncidenciasCerradas() {

        ArrayList<Incidencia> incidenciasCerradas = new ArrayList<>();
        for (Tecnico a : tecnicos) {
            for (int i = 0; i < a.getIncidenciasTecnico().size(); i++) {
                if (a.getIncidenciasTecnico().get(i).isEstaResuelta()) {
                    incidenciasCerradas.add(a.getIncidenciasTecnico().get(i));
                }
            }
        }
        return incidenciasCerradas;
    }

    public int incidenciasAbiertas() {
        int n = 0;
        for (Usuario a : usuarios) {
            for (int i = 0; i < a.getIncidenciasUsuario().size(); i++) {
                if (!a.getIncidenciasUsuario().get(i).isEstaResuelta()) {
                    n++;
                }
            }
        }
        return n;
    }

    public int incidenciasAbiertasAsignadas() {
        int n = 0;
        for (Usuario a : usuarios) {
            for (int i = 0; i < a.getIncidenciasUsuario().size(); i++) {
                if (!a.getIncidenciasUsuario().get(i).isEstaResuelta() && a.getIncidenciasUsuario().get(i).isAsignada()) {
                    n++;
                }
            }
        }
        return n;
    }

    public ArrayList<Incidencia> incidenciasAbiertaSinAsignar() {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        int n = 0;
        for (Usuario a : usuarios) {
            for (int i = 0; i < a.getIncidenciasUsuario().size(); i++) {
                if (!a.getIncidenciasUsuario().get(i).isEstaResuelta() && a.getIncidenciasUsuario().get(i).isAsignada() == false) {
                    incidencias.add(a.getIncidenciasUsuario().get(i));
                }
            }
        }
        return incidencias;
    }

    public int incidenciasCerradas() {
        int n = 0;
        for (Usuario a : usuarios) {
            for (int i = 0; i < a.getIncidenciasUsuario().size(); i++) {
                if (a.getIncidenciasUsuario().get(i).isEstaResuelta()) {
                    n++;
                }
            }
        }
        return n;
    }

    public ArrayList<IncidenciaDataClass> buscaIncidenciasAsignadas() {

        ArrayList<IncidenciaDataClass> incidenciasAsignada = new ArrayList<>();
        for (Usuario a : usuarios) {
            for (int i = 0; i < a.getIncidenciasUsuario().size(); i++) {
                if (a.getIncidenciasUsuario().get(i).isAsignada()) {
                    IncidenciaDataClass incidenciaabierta = new IncidenciaDataClass(a.getIncidenciasUsuario().get(i));
                    incidenciasAsignada.add(incidenciaabierta);
                }
            }
        }
        return incidenciasAsignada;
    }

    public int generaIdIncidencia() {

        boolean idrepetido;
        int aleatorio;
        do {

            aleatorio = (int) (Math.random() * 10000) + 999;
            idrepetido = false;

            for (Usuario a : usuarios) {
                for (int i = 0; i < a.getIncidenciasUsuario().size(); i++) {
                    if (a.getIncidenciasUsuario().get(i).getId() == aleatorio) {
                        idrepetido = true;
                        break;
                    }
                }
            }
        } while (idrepetido);
        return aleatorio;
    }

    public int generaIdUsuario() {

        boolean idrepetido;
        int aleatorio;
        do {

            aleatorio = (int) (Math.random() * 10000) + 999;
            idrepetido = false;

            for (Usuario a : usuarios) {

                if (a.getId() == aleatorio) {
                    idrepetido = true;
                }

            }
        } while (idrepetido);
        return aleatorio;
    }

    public Usuario buscaUsuarioId(int id) {

        int usuario = 0;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                usuario = i;
            }
        }
        return usuarios.get(usuario);
    }

    public Usuario buscaUsuarionombre(String nombre) {

        int usuario = 0;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equalsIgnoreCase(nombre)) {
                usuario = i;
            }
        }
        return usuarios.get(usuario);
    }

    public float prioridadmediaapp() {

        float prioridad = 0;
        int incidenciastotales = 0;
        for (Usuario u : usuarios) {
            for (int i = 0; i < u.getIncidenciasUsuario().size(); i++) {
                prioridad = prioridad + u.getIncidenciasUsuario().get(i).getPrioridad();
                incidenciastotales++;
            }
        }
        prioridad = prioridad / incidenciastotales;

        return prioridad;
    }

    public Incidencia buscaincidenciaId(int idincidencia) {
        int posincidencia = 0;
        int posusuario = 0;

        for (int i = 0; i < usuarios.size(); i++) {
            for (int j = 0; j < usuarios.get(i).getIncidenciasUsuario().size(); j++) {
                if (usuarios.get(i).getIncidenciasUsuario().get(j).getId() == idincidencia) {
                    posincidencia = j;
                    posusuario = i;
                }
            }
        }
        return usuarios.get(posusuario).getIncidenciasUsuario().get(posincidencia);
    }

    public Tecnico buscatecnicoid(int idTecnico) {
        int tecnico = 0;
        for (int i = 0; i < tecnicos.size(); i++) {
            if (tecnicos.get(i).getId() == idTecnico) {
                tecnico = i;
            }
        }
        return tecnicos.get(tecnico);
    }

    public boolean estaIncidenciaAsignada(int idIncidencia) {
        boolean incidenciaasignada = false;

        for (Usuario u : usuarios) {
            for (int i = 0; i < u.getIncidenciasUsuario().size(); i++) {
                if (u.getIncidenciasUsuario().get(i).getId() == idIncidencia) {
                    if (u.getIncidenciasUsuario().get(i).isAsignada()) {
                        incidenciaasignada = true;
                    }
                }
            }
        }
        return incidenciaasignada;
    }

    private Usuario buscaUsuariobyIncidencia(int idIncidencia) {

        int usuario = 0;
        for (int i = 0; i < usuarios.size(); i++) {
            for (int j = 0; j < usuarios.get(i).getIncidenciasUsuario().size(); j++) {
                if (usuarios.get(i).getIncidenciasUsuario().get(j).getId() == idIncidencia) {
                    usuario = i;
                }
            }
        }
        return usuarios.get(usuario);
    }


    public boolean asignaIncidencia(int idIncidencia, int idTecnico) {

        boolean asignada = false;
        int posincidencia = 0;
        int posicionusuario = 0;
        int posiciontecnico = 0;
        boolean incidenciafind = false;
        boolean tecnicofind = false;

        //Vamos a encontrar primero la incidencia
        for (int i = 0; i < usuarios.size(); i++) {
            for (int j = 0; j < usuarios.get(i).getIncidenciasUsuario().size(); j++) {
                if (usuarios.get(i).getIncidenciasUsuario().get(j).getId() == idIncidencia) {
                    posicionusuario = i;
                    posincidencia = j;
                    incidenciafind = true;
                }
            }
        }

        //Vamos a encontrar al Técnico
        for (int i = 0; i < tecnicos.size(); i++) {
            if (tecnicos.get(i).getId() == idTecnico) {
                posiciontecnico = i;
                tecnicofind = true;
            }

        }

        //Asignamos la incidencia al técnico
        tecnicos.get(posiciontecnico).getIncidenciasTecnico().add(usuarios.get(posicionusuario).getIncidenciasUsuario().get(posincidencia));
        //Indicamos que ya está asignada
        usuarios.get(posicionusuario).getIncidenciasUsuario().get(posincidencia).setAsignada(true);

        //Si encuentra el usuario y la incidencia, la asignará por lo tanto asignada la colocamos en true
        if (tecnicofind && incidenciafind) {
            asignada = true;
        }

        return asignada;
    }

    public Boolean cierraIncidencia(int idusuario, int idIncidencia, String solucion) {
        boolean cerrada = false;
        int posicionincidencia = 0;

        for (int i = 0; i < buscaUsuarioId(idusuario).getIncidenciasUsuario().size(); i++) {
            if (buscaUsuarioId(idusuario).getIncidenciasUsuario().get(i).getId() == idIncidencia) {
                posicionincidencia = i;
                cerrada = true;
                buscaUsuarioId(idusuario).getIncidenciasUsuario().get(i).setSolucion(solucion);
                buscaUsuarioId(idusuario).getIncidenciasUsuario().get(i).setEstaResuelta(true);
                buscaUsuarioId(idusuario).getIncidenciasUsuario().get(i).setEstado("Resuelta");
                buscaUsuarioId(idusuario).getIncidenciasUsuario().get(i).setFechaFin(LocalDate.now());

            }
        }

        return cerrada;
    }

    public ArrayList<Incidencia> buscaincidenciasByTerm(String termino) {

        ArrayList<Incidencia> incidenciasportermino = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {
            for (int j = 0; j < usuarios.get(i).getIncidenciasUsuario().size(); j++) {
                if (usuarios.get(i).getIncidenciasUsuario().get(j).getDescripcion().contains(termino)) {

                    incidenciasportermino.add(usuarios.get(i).getIncidenciasUsuario().get(j));
                }

            }
        }

        return incidenciasportermino;
    }

    public int incidenciasAsignadas(int idTecnico) {
        int incidencias = buscatecnicoid(idTecnico).getIncidenciasTecnico().size();
        return incidencias;
    }


    public void pintaLogin() {
        System.out.println("""
                 _________________________________________
                ||_______________________________________||
                ||              MENÚ INICIO              || 
                                
                Bienvenido a Fernanticket
                *** Recuerda que para reportar una incidencia tienes que estar registrado ***
                                
                ___________________________________________
                [1]. Ya estoy registrado
                ___________________________________________
                [2]. Registrarme
                ___________________________________________
                [3]. Acceso como invitado
                ___________________________________________
                [4]. Apagar Programa
                ___________________________________________
                                
                Elige una opción :
                               
                """);
    }
    //Métodos para cargar los datos de nuestra aplicación

    public ArrayList<Usuario> cargarusuarios(String fichero) throws IOException, ClassNotFoundException {

            ArrayList<Usuario> arrayListusuarios=new ArrayList<>();
            FileInputStream fis =new FileInputStream(fichero);
            ObjectInputStream ois=new ObjectInputStream(fis);
            arrayListusuarios=(ArrayList<Usuario>) ois.readObject();
            ois.close();

        return arrayListusuarios;
    }

    public ArrayList<Tecnico> cargartecnico(String fichero) throws IOException, ClassNotFoundException,WriteAbortedException {
        ArrayList<Tecnico> arrayListtecnicos;
        FileInputStream fis =new FileInputStream(fichero);
        ObjectInputStream ois=new ObjectInputStream(fis);

            arrayListtecnicos=(ArrayList<Tecnico>) ois.readObject();


        ois.close();

        return arrayListtecnicos;
    }
    public HashMap<String,Usuario> cargarloginapp(String fichero) throws IOException, ClassNotFoundException {
        HashMap<String,Usuario>  loginapps;
        FileInputStream fis =new FileInputStream(fichero);
        ObjectInputStream ois=new ObjectInputStream(fis);
        loginapps=(HashMap<String,Usuario>) ois.readObject();
        ois.close();

        return loginapps;
    }
    public HashMap<String,Tecnico> cargarloginapptecnico(String fichero) throws IOException, ClassNotFoundException {
        HashMap<String,Tecnico>  loginappstecnico;
        FileInputStream fis =new FileInputStream(fichero);
        ObjectInputStream ois=new ObjectInputStream(fis);
        loginappstecnico=(HashMap<String,Tecnico>) ois.readObject();
        ois.close();

        return loginappstecnico;
    }

    //Método para guardar los usuarios en un array list y también guardamos el hasmap
    public void salvar_usuarios() throws Exception{
        try
        {
            FileOutputStream fos = new FileOutputStream("listausuarios.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(usuarios);
            fos = new FileOutputStream("usuarioshasmap.dat");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(loginapp);
            oos.close();
            fos.close();

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }

    //Método para guardar los técnicos en un array list y también guardamos el hasmap
    public void salvar_tecnicos() throws Exception{
        try
        {
            FileOutputStream fos = new FileOutputStream("listatecnicos.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tecnicos);
            fos = new FileOutputStream("tecnicoshasmap.dat");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(loginapptecnico);
            oos.close();
            fos.close();

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }


    public void pintaMenuUsuario(String email) {
        System.out.println("""
                 _________________________________________
                ||_______________________________________||
                ||              MENÚ USUARIO             ||""");

        System.out.println("Bienvenid@ " + loginapp.get(email).getNombre() + " tiene usted perfil de usuario normal");
        System.out.println("Actualmente tiene " + loginapp.get(email).getIncidenciasUsuario().size() + " incidencias");
        System.out.println("""     
                ___________________________________________
                [1]. Registrar una incidencia
                ___________________________________________
                [2]. Consultar mis incidencias abiertas
                ___________________________________________
                [3]. Consultar mis incidencias cerradas
                ___________________________________________
                [4]. Mostrar mi perfil
                ___________________________________________
                [5]. Cambiar clave de acceso
                ___________________________________________
                [6]. Cerrar sesión
                ___________________________________________
                                
                Elige una opción:
                """);
    }


    public void pintaMenuTecnico(String email) {
        System.out.println("""
                 _________________________________________
                ||_______________________________________||
                ||               MENÚ TÉCNICO            ||""");

        System.out.println("Bienvenid@ " + loginapptecnico.get(email).getNombre() + " tiene usted perfil de técnico");
        System.out.println("Actualmente tiene " + incidenciasAbiertas() + " incidencias abiertas, de las\n" +
                "cuales " + (incidenciasAbiertas() - incidenciasAbiertasAsignadas() + " están sin asignar"));
        System.out.println("""     
                ___________________________________________
                [1]. Consultar las incidencias asignadas no resueltas
                ___________________________________________
                [2]. Marcar una incidencia como resuelta
                ___________________________________________
                [3]. Consultar mis incidencias cerradas
                ___________________________________________
                [4]. Mostrar mi perfil
                ___________________________________________
                [5]. Cambiar clave de acceso
                ___________________________________________
                [6]. Cerrar sesión
                ___________________________________________
                                
                Elige una opción:
                """);
    }

    public void pintaMenuAdmin(String email) {
        System.out.println("""
                 _________________________________________
                ||_______________________________________||
                ||           MENÚ ADMINISTRADOR          ||""");

        System.out.println("Bienvenid@ " + loginappadmin.get(email).getNombre() + " tiene usted perfil de administrador");
        System.out.println("Actualmente tiene " + incidenciasAbiertas() + " incidencias abiertas, de las\n" +
                "cuales " + (incidenciasAbiertas() - incidenciasAbiertasAsignadas() + " están sin asignar"));
        System.out.println("""     
                ___________________________________________
                [1]. Consulta todas las incidencias abiertas
                ___________________________________________
                [2]. Consultar incidencias cerradas
                ___________________________________________
                [3]. Consultar incidencias por término
                ___________________________________________
                [4]. Consultar los técnicos
                ___________________________________________
                [5]. Asignar una incidencia a un técnico
                ___________________________________________
                [6]. Dar de alta a un técnico
                ___________________________________________
                [7]. Borrar un técnico
                ___________________________________________
                [8]. Consultar los usuarios
                ___________________________________________
                [9]. Estadísticas de la aplicación
                ___________________________________________
                [10]. Ver configuración properties
                ___________________________________________
                [11]. Enviar Incidencias por correo
                ___________________________________________
                [12]. Cerrar sesión
                ___________________________________________
                                
                Elige una opción:
                                
                """);
    }

}
