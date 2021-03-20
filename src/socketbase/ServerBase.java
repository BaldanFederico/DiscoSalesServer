/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketbase;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pogliani.mattia
 */
public class ServerBase {

    private int port;
    private ServerSocket SS;

    public ServerBase(int port) {
        this.port = port;

    }

    public void runServer() {
      
        String nome, password, Email;
        String scrivi;
        Scanner sc = new Scanner(System.in);
        String protocollo;
        String risposta;
        gestione g = new gestione();
        genera g2 = new genera();
        try {
            SS = new ServerSocket(port);
            System.out.println("Server creato con successo");
            System.out.println("in attesa di richieste");
            Socket s = SS.accept();
            System.out.println("Un client connesso!!!");

            PrintWriter scrittore = new PrintWriter(s.getOutputStream(), true);
            BufferedReader ricevi = new BufferedReader(new InputStreamReader(s.getInputStream()));
            risposta = "";
            scrivi = "";
             g.usersStoraging();
            do {

                protocollo = ricevi.readLine();
                System.out.println(protocollo);
                switch (protocollo) {
                    case "signUP":
                        g.CreateFolder();
                        nome = ricevi.readLine();
                        password = ricevi.readLine();
                        Email = ricevi.readLine();
                        risposta = g.salvaUtenti(nome, password, Email);
                        scrittore.println(risposta);
                        g2.mandaMail();  //bisogna aggiungere la parte dell'invio
                        break;
                    case "log":
                        nome = ricevi.readLine();
                        password = ricevi.readLine();
                        risposta = g.autenticazione(nome, password);
                        scrittore.println(risposta);
                        break;
                    case "secure":

                        String codice = ricevi.readLine();
                        scrittore.println(g.verificaAccount(codice));

                        break;

                }
            } while (!protocollo.equals("exit"));

            //aspetta il messaggio del client
            ricevi.close();
            scrittore.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerBase server = new ServerBase(6666);
        server.runServer();
    }

}
