/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
    private String userName = System.getProperty("user.name");

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
            retriveRoomData();
             System.out.println("prova56");
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
                                scrittore.println(room.get(i).getNomeRoom());                             //se l'utente si vuole unire entra dentro
                                risposta = ricevi.readLine();
                                if (risposta.equals("entr")) { //la stringa che il client gli invia prmendo un bottone
                                    partecipante = ricevi.readLine();//
                                    room.add(new Room(clientSocket, room.get(i).getNomeRoom(), room.get(i).getOwner(), room.get(i).getRoomID(), partecipante));//aggiunge un partecipante 
                                    scrittore.println(room.get(i).getNomeRoom());
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
                     break;
                    case "chatData":  //serve per la chat
                        partecipante = ricevi.readLine();//riceve il nome utente
                        Controlla(partecipante);
                        System.out.println("chatdata");
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

        f = new File("C:\\Users\\" + userName + "\\Desktop\\DiscosalesServer\\RoomRoute.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        f.createNewFile();
        for (int i = 0; i < room.size(); i++) {
            bw.write(room.get(i).getClientSocket().getInetAddress() + ";");
            bw.write(room.get(i).getNomeRoom() + ";");
            bw.write(room.get(i).getOwner() + ";");
            bw.write(room.get(i).getRoomID() + ";");
            bw.write(room.get(i).getPartecipante() + ";");
            bw.newLine();
            bw.flush();

        }
        bw.close();
    }

    private void Controlla(String partecipante) throws IOException { //serve quando in cliant accede al suo account con già dei progressi fatti 
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
        System.out.println(room.size());
        if (room.size() > 0) {//nel caso non possiede room
            for (int i = 0; i < room.size(); i++) {

                if (room.get(i).getPartecipante().equals(partecipante)) {
                    scrittore.println(room.get(i).getNomeRoom());//manda prima il nome
                    scrittore.println(room.get(i).getOwner());
                   
                    MandaPartecipante(i);//poi manda i partecipanti

                } else if (i == room.size() - 1) { //serve room>0 ma l'utente non possiede room 
                    scrittore.println("stop"); //uno per nome room e owner
                    scrittore.println("stop"); //uno per i partecipanti
                }

            }
        } else {
            scrittore.println("stop");
            scrittore.println("stop");
        }
    }

    private void MandaPartecipante(int c) throws IOException {  //serve per mandare tutti i paretecipanti di quella room al client
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
        for (int i = 0; i < room.size(); i++) {
            if (room.get(i).getRoomID().equals(room.get(c).getRoomID())) {
                scrittore.println(room.get(i).getPartecipante());
                   if(room.get(i).getPartecipante().equals(room.get(c).getOwner())){
                       scrittore.println(room.get(i).getRoomID());
                   }
            }
        }
        scrittore.println("stop");
        
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

    private void retriveRoomData() throws IOException {

        Socket s2;

        String[] salva;
        String s;
        f = new File("C:\\Users\\" + userName + "\\Desktop\\DiscosalesServer\\RoomRoute.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        if (f.exists()) {

            s = br.readLine();
            while (s != null) {
                salva = s.split(";");
                System.out.println("prova3");
                s2 = new Socket(salva[0], 6666);
                System.out.println(s2.getInetAddress());

                room.add(new Room(s2, salva[1], salva[2], salva[3], salva[4]));
                s = br.readLine();

            }

            System.out.println("prova4");
            br.close();
        }
    }
}
