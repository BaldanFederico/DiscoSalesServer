/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketbase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class serverAction implements Runnable {

    private Socket clientSocket;

    public serverAction(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        String nome, password, Email;
        String scrivi;
        Scanner sc = new Scanner(System.in);
        String protocollo;
        String risposta;
        gestione g = new gestione();
        genera g2 = new genera();
        try {
           
            System.out.println("Server creato con successo");
            System.out.println("in attesa di richieste");
            
            System.out.println("Un client connesso!!!");

            PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
            
            BufferedReader ricevi = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          
            risposta = "";
            scrivi = "";
           
            g.usersStoraging();
            System.out.println("prova1");
            do {

                protocollo = ricevi.readLine();  //riceve dal client 
                System.out.println(protocollo);
                switch (protocollo) {
                    case "signUP":
                        g.CreateFolder();
                        nome = ricevi.readLine();
                        password = ricevi.readLine();
                        Email = ricevi.readLine();
                        risposta = g.salvaUtenti(nome, password, Email); //salva tutto nell'oggetto
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
                    case "enterAccount":
                        
                        break;

                }
            } while (!protocollo.equals("exit"));

            //aspetta il messaggio del client
            ricevi.close();
            scrittore.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
