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
    private static ArrayList<Socket> sockets = new ArrayList();
    private static ArrayList<ServerAction> clients = new ArrayList();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(6666);  //stabilisce la porta
            System.out.println("DSServer here ...");
            while (true) {
                Socket client = server.accept();
                System.out.println("New connection from "+client.getInetAddress());
                Thread serverino = new Thread(new ServerAction(client));
                sockets.add(client);
                clients.add(new ServerAction(client));
                serverino.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public  ArrayList<Socket> getSocket2() {
        return sockets;
    }

  

}