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
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class gestioneChatRoom implements Runnable {

    private Socket clientSocket;
    HashMap<String, Room> room = new HashMap();
    private genera g = new genera();

    public gestioneChatRoom(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void creaNuovaRoom(Socket client, String nomeRoom, String owner) {
        room.put(g.GetKeyRoom(), new Room(client, nomeRoom, owner));

    }

    @Override
    public void run() {
        String protocollo;

        String risposta = "";
        String scrivi = "";

        System.out.println("prova1");
        try {
            PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader ricevi = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            do {

                protocollo = ricevi.readLine();  //riceve dal client
                System.out.println(protocollo);
                switch (protocollo) {
                    case "":

                        break;
                    case "e2":

                        break;
                    case "e":

                    case "e7":

                        break;

                }

            } while (!protocollo.equals("exit"));

            //aspetta il messaggio del client
            ricevi.close();
            scrittore.close();
            clientSocket.close();
        } catch (IOException ex) {

        }
    }

}
