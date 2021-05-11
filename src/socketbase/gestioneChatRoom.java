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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class gestioneChatRoom implements Runnable {

    private Socket clientSocket;
    Vector<Room> room = new Vector();
    private genera g = new genera();
    private File f;

    public gestioneChatRoom(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        Boolean s = false;
        String protocollo;
        String RoomID;
        String nomeRoom;
        String owner;
        String partecipante = null;
        String risposta = "";
        String scrivi = "";
        genera g = new genera();
        System.out.println("prova1");
        try {
            PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader ricevi = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //andiamo a fare un retrive
            risposta = ricevi.readLine();
            Controlla(risposta);
            //in questa parte serve member
            do {

                protocollo = ricevi.readLine();  //riceve dal client
                System.out.println(protocollo);
                switch (protocollo) {
                    case "create":          //crea una nuova room
                        owner = ricevi.readLine();
                        partecipante = ricevi.readLine();
                        nomeRoom = ricevi.readLine();
                        room.add(new Room(clientSocket, nomeRoom, owner, g.GetRoomID(), partecipante));  //vuole la server socket appartenente nome proprietario con il nome utente
                        scrittore.write(room.lastElement().getRoomID());//solo il proprietario può 
                        writeRoom();//salvo i dati della room all'interno di un file
                        break;
                    case "e2":  //cerca la room
                        RoomID = ricevi.readLine();
                        for (int i = 0; i < room.size(); i++) {

                            if (room.get(i).getRoomID().equals(RoomID)) {
                                s = true;
                                scrittore.write(room.get(i).getNomeRoom());                             //se l'utente si vuole unire entra dentro
                                if (risposta.equals("si")) { //la stringa che il client gli invia prmendo un bottone
                                    partecipante = ricevi.readLine();
                                    room.add(new Room(clientSocket, room.get(i).getNomeRoom(), room.get(i).getOwner(), room.get(i).getRoomID(), partecipante));//aggiunge un partecipante 
                                    scrittore.println(room.get(i).getNomeRoom()); //manda al cliet il nome della room
                                    MandaPartecipante(i);//una volta che l'utente è dentro serve a l'utente sapere tutti i partecipanti di quella room
                                    writeRoom(); //scrivo la room per un eventuale shutdown del server 
                                }
                            } else if (i == room.size() - 1 && s == false) {
                                scrittore.println("Room non trovata");
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

    private void Controlla(String risposta) throws IOException { //serve quando in cliant accede al suo account con già dei progressi fatti 
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);

        for (int i = 0; i < room.size(); i++) {
            if (room.get(i).getPartecipante().equals(risposta)) {
                scrittore.println(room.get(i).getNomeRoom());
                MandaPartecipante(i);

            }
        }

    }

    private void MandaPartecipante(int c) throws IOException {  //serve per mandare tutti i paretecipanti di quella room al client
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
        scrittore.println("member");//sta per ricevere tutti i partecipanti di quella room
        for (int i = 0; i < room.size(); i++) {
            if (room.get(i).getRoomID().equals(room.get(c).getRoomID())) {
                scrittore.println(room.get(i).getPartecipante());
            }
        }
    }
}
