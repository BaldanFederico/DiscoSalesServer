/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;


/**
 *
 * @author pogliani.mattia
 */
public class ServerBase {
public static ArrayList<Socket> socket2 = new ArrayList();
  

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
   ArrayList<ServerAction> socket = new ArrayList();

        try {
            ServerSocket server = new ServerSocket(6666);  //stabilisce la porta
            System.out.println("Server è attivo e in ascolto");

            while (true) {
                Socket client = server.accept();
                System.out.println("connessione ricevuta, ora se la smazza il thread");
                Thread Serverino = new Thread(new ServerAction(client));
                socket2.add(client);
                socket.add(new ServerAction(client));
                Serverino.start();
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public  ArrayList<Socket> getSocket2() {
        return socket2;
    }

  

}