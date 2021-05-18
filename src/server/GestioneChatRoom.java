/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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
public class GestioneChatRoom {

    private Socket clientSocket;
    private Vector<Room> room = new Vector();
    private Genera g = new Genera();
    private File f;

    public GestioneChatRoom(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void gestisci() {
        System.out.println("sei nella gestione account 1");
        Boolean s = false;
        String protocollo;
        String RoomID;
        String nomeRoom;
        String owner;
        String partecipante;
        String risposta = "";
        String scrivi = "";
        Genera g = new Genera();
        System.out.println("prova1");
        try {
            PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader ricevi = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //andiamo a fare un retrive
            risposta = ricevi.readLine();//riceve il nome utente
            System.out.println("sei nella gestione account 1");
            Controlla(risposta);
            //in questa parte serve member
            System.out.println("sei nella gestione account 2");
            do {

                protocollo = ricevi.readLine();  //riceve dal client
                System.out.println(protocollo);
                switch (protocollo) {
                    case "create":          //crea una nuova room
                        owner = ricevi.readLine();
                        partecipante = ricevi.readLine();
                        nomeRoom = ricevi.readLine();
                        room.add(new Room(clientSocket, nomeRoom, owner, g.GetRoomID(), partecipante));  //vuole la server socket appartenente nome proprietario con il nome utente
                        scrittore.println(room.lastElement().getRoomID());//solo il proprietario può sapere l'id
                        writeRoom();//salvo i dati della room all'interno di un file
                        break;
                    case "search":  //cerca la room
                        RoomID = ricevi.readLine();
                        for (int i = 0; i < room.size(); i++) {

                            if (room.get(i).getRoomID().equals(RoomID)) {
                                s = true;
                                scrittore.write(room.get(i).getNomeRoom());                             //se l'utente si vuole unire entra dentro
                                risposta = ricevi.readLine();
                                if (risposta.equals("entr")) { //la stringa che il client gli invia prmendo un bottone
                                    partecipante = ricevi.readLine();//
                                    room.add(new Room(clientSocket, room.get(i).getNomeRoom(), room.get(i).getOwner(), room.get(i).getRoomID(), partecipante));//aggiunge un partecipante 
                                    scrittore.println(room.get(i).getNomeRoom()); //manda al cliet il nome della room
                                    scrittore.println(room.get(i).getOwner());
                                    MandaPartecipante(i);//una volta che l'utente è dentro serve a l'utente sapere tutti i partecipanti di quella room
                                    writeRoom(); //scrivo la room per un eventuale shutdown del server 
                                }
                            } else if (i == room.size() - 1 && s == false) {
                                scrittore.println("Room non trovata");
                            }
                        }
                        break;
                    case "delt":
                        rimuoviUtente();
                    case "":
                        RoomID = ricevi.readLine();
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
        System.out.println("controlla");
        for (int i = 0; i < room.size(); i++) {

            if (room.get(i).getPartecipante().equals(risposta)) {
                scrittore.println(room.get(i).getNomeRoom());//manda prima il nome
                scrittore.println(room.get(i).getOwner());
                MandaPartecipante(i);//poi manda i partecipanti

            }
        }

    }

    private void MandaPartecipante(int c) throws IOException {  //serve per mandare tutti i paretecipanti di quella room al client
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
        for (int i = 0; i < room.size(); i++) {
            if (room.get(i).getRoomID().equals(room.get(c).getRoomID())) {
                scrittore.println(room.get(i).getPartecipante());
            }
        }
    }

    private void rimuoviUtente() throws IOException {
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader ricevi = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String partecipante = ricevi.readLine();
        for (int i = 0; i < room.size(); i++) {
            if (room.get(i).getPartecipante().equals(partecipante)) {
                room.remove(i);

            }
        }

    }

    private void gestioneMessaggi(String risposta) throws IOException {

        for (int i = 0; i < room.size(); i++) {

            if (room.get(i).getRoomID().equals(risposta)) {
                PrintWriter scrittore = new PrintWriter(room.get(i).getClientSocket().getOutputStream(), true);
                scrittore.println(risposta);

            }
        }

    }

}
