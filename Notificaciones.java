package com.company;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class Notificaciones implements Serializable {
    public void enviarConGMail(String destinatario, String asunto, String cuerpo) {
        String remitente = "jchofyxs@gmail.com";
        String clave = "Javi14071998";
// Propiedades de la conexión que se va a establecer con el servidor de correo SMTP
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);
        props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
        ((Properties) props).put("mail.smtp.starttls.enable", "true"); // Conectar de manera segura
        props.put("mail.smtp.port", "587"); // Puerto SMTP seguro de Google
// Se obtiene la sesión en el servidor de correo
        Session session = Session.getDefaultInstance(props);
        try {
// Creación del mensaje a enviar
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinatario));

            message.setSubject(asunto);
//message.setText(cuerpo); // Para enviar texto plano
            message.setContent(cuerpo, "text/html; charset=utf-8"); // Para enviar html
// Definición de los parámetros del protocolo de transporte
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public boolean enviaMensajetelegram(String mensaje){
        String direccion;
        String fijo="https://api.telegram.org/bot5115346628:AAEJ02lKnkSrBhSBC20aMf-xE5R-6aWJPw4/sendMessage?chat_id=1856374302&text=";
        direccion=fijo+mensaje;
        URL url;
        Boolean dev;
        dev=false;
        try {
            url=new URL(direccion);
            URLConnection con = url.openConnection();
            BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream()));
            dev=true;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return dev;
    }
}
