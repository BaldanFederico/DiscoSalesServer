/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author dell
 */
public class Genera {

    private String caratteri = "ABCDEFGHILMNOPQRSTUVZWYKabcdefghilmnopqrstuvzkxwy0123456789";

    public String codiceRoom() {
        Random r = new Random();
        String randomString = "";
        int length = 5;
        char[] text = new char[length];

        for (int x = 0; x < text.length; x++) {
            text[x] = caratteri.charAt(r.nextInt(caratteri.length()));
            randomString += text[x];
        }
        return randomString;

    }

    public String GetcodiceU() {  //serve per comunicare con gli altri utenti all'interno di una room

        Random r = new Random();
        String randomString = "";
        int length = 7;
        char[] text = new char[length];

        for (int x = 0; x < text.length; x++) {
            text[x] = caratteri.charAt(r.nextInt(caratteri.length()));
            randomString += text[x];
        }
        return randomString;

    }



    
    
    public void mandaMail(String codice,String Email) {
       
        String smtpHost = "smtp.google.com";
        String indirizzoDa = "discosales@security.com";
      

        try {
            Properties props = System.getProperties();

            props.put("mail.smtp.host", smtpHost);

            Session session = Session.getDefaultInstance(props, null);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(indirizzoDa));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(Email));

            message.setSubject("codice di attivazione");
            message.setText(codice);

            Transport.send(message);

        } catch (Exception e) {
            System.out.println("la mail non funziona");
        }
    }
}
