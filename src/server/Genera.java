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
 * La classe "Genera" gestisce la creazione di codici per le chatroom e i codici di attivazione
 * @author DiscoSales
 */
public class Genera {

    private String caratteri = "ABCDEFGHILMNOPQRSTUVZWYKabcdefghilmnopqrstuvzkxwy0123456789";
/**
 * Il metodo restituisce il codice della room creata
 * @return randomString Restituisce il codice della stanza
 */
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
/** 
 * Serve per comunicare con gli altri utenti all'interno di una room
 * @return randomString Restituisce il codice di attivazione
 */
    public String GetcodiceU() {  //Serve per comunicare con gli altri utenti all'interno di una room

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

/**
 * Il metodo manda il codice di accesso tramite la mail
 * @param codice codice attivazione
 * @param Email mail 
 */
    
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
