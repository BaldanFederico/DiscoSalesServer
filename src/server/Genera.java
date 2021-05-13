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

/**
 *
 * @author dell
 */
public class Genera {

    private String caratteri = "ABCDEFGHILMNOPQRSTUVZWYKabcdefghilmnopqrstuvzkxwy0123456789";

    public String codice() {
        Random r = new Random();
        String randomString = "";
        int length = 10;
        char[] text = new char[length];

        for (int x = 0; x < text.length; x++) {
            text[x] = caratteri.charAt(r.nextInt(caratteri.length()));
            randomString += text[x];
        }
        return randomString;

    }

    public String GetRoomID() {  //serve per comunicare con gli altri utenti all'interno di una room

        Random r = new Random();
        String randomString = "";
        int length = 10;
        char[] text = new char[length];

        for (int x = 0; x < text.length; x++) {
            text[x] = caratteri.charAt(r.nextInt(caratteri.length()));
            randomString += text[x];
        }
        return randomString;

    }



    
    
    public void mandaMail() {

//
//   final String fromEmail = "myemailid@gmail.com"; //requires valid gmail id
//		final String password = "mypassword"; // correct password for gmail id
//		final String toEmail ="myemail@yahoo.com"; // can be any email id 
//		
//		System.out.println("TLSEmail Start");
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
//		props.put("mail.smtp.port", "587"); //TLS Port
//		props.put("mail.smtp.auth", "true"); //enable authentication
//		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//		
//                //create Authenticator object to pass in Session.getInstance argument
//		Authenticator auth = new Authenticator() {
//			//override the getPasswordAuthentication method
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(fromEmail, password.toCharArray());
//			}
//		};
//		Session session = Session.getInstance(props, auth);
//		
//		EmailUtil.sendEmail(session, toEmail,"TLSEmail Testing Subject", "TLSEmail Testing Body");
//		
//	}
//	
//    }
    }
}
