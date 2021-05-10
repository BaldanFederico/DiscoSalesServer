/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class gestioneChatRoom implements Runnable {
    
    private Socket clientSocket;
    ArrayList<Room> room = new ArrayList();
    private genera g = new genera();
    private File f;
    
    public gestioneChatRoom(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run() {
        String protocollo;
        String RoomID;
        String nomeRoom;
        String owner;
        String partecipante;
        String risposta = "";
        String scrivi = "";
        genera g=new genera();
        System.out.println("prova1");
        try {
            PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
            
            BufferedReader ricevi = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //andiamo a fare un retrive
            risposta = ricevi.readLine();
            Controlla(risposta);
            do {
                
                protocollo = ricevi.readLine();  //riceve dal client
                System.out.println(protocollo);
                switch (protocollo) {
                    case "create":          //crea una nuova room
                        owner = ricevi.readLine(); //vuole l'id univoco del client
                        partecipante = owner;
                        nomeRoom = ricevi.readLine();
                        room.add(new Room(clientSocket, nomeRoom, owner,g.GetRoomID() , partecipante));  //vuole la server socket appartenente nome proprietario con il nome utente
                        writeRoom();//salvo i dati della room all'interno di un file
                        break;
                    case "e2":  //cerca la room
                        risposta = ricevi.readLine();
                        for (int i = 0; i < room.size(); i++) {
                            if (room.get(i).getRoomID().equals(risposta)) {
                                partecipante = ricevi.readLine();
                                room.add(new Room(clientSocket, room.get(i).getNomeRoom(), room.get(i).getOwner(), room.get(i).getRoomID(), partecipante));//aggiunge un partecipante 
                                scrittore.println(room.get(i).getNomeRoom()); //manda al cliet il nome della room
                                
                            }
                        }
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
    
    private void writeRoom() throws IOException {  //serve tenere traccia dei vari delle room
        String userName = System.getProperty("user.name");
        f = new File("C:\\Users\\" + userName + "\\Desktop\\DiscosalesServer\\RoomRoute.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        f.createNewFile();
        for (int i = 0; i < room.size(); i++) {
            bw.write(room.get(i).getNomeRoom() + ";");
            bw.write(room.get(i).getOwner() + ";");
            bw.write(room.get(i).getRoomID() + ";");
            bw.write(room.get(i).getPartecipante() + ";");
            bw.newLine();
            bw.flush();
            
        }
        bw.close();
    }
    
    private void Controlla(String risposta) throws IOException {
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
        
        for (int i = 0; i < room.size(); i++) {
            if (room.get(i).getPartecipante().equals(risposta)) {
                scrittore.println(room.get(i).getNomeRoom());
                
            }
        }
        
    }
    
    private void MandaPartecipante() throws IOException {  //serve per mandare tutti i paretecipanti di quella room al client
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
        
    }
}
