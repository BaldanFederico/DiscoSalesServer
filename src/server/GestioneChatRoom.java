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
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe gestisce i dati delle chatroom registrate nel server e comunica con il client in caso il proprietario vuole modificare i suoi parametri
 * @author DiscoSales
 */
public class GestioneChatRoom {
    private Socket clientSocket;
    public Vector<Room> room = new Vector();
    private Genera g = new Genera();
    private File f;
    private String userName = System.getProperty("user.name");
    private String messaggio;
/**
 * Costruttore 
 * @param clientSocket Il socket che comunica con il client
 */
    public GestioneChatRoom(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
/**
 * Il metodo gestisce le comunicazioni con il client
 */
    public void gestione() {
        try {

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

            PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader ricevi = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Retrive
            System.out.println("prova56");
            do {

                //   scrittore.println("stop");
                protocollo = ricevi.readLine();  //Riceve dal client
                System.out.println(protocollo);
                switch (protocollo) {
                    case "create":          //Crea una nuova room
                        owner = ricevi.readLine();
                        partecipante = ricevi.readLine();
                        nomeRoom = ricevi.readLine();
                        room.add(new Room(clientSocket, nomeRoom, owner, g.codiceRoom(), partecipante));  //Vuole la server socket appartenente nome proprietario con il nome utente
                        scrittore.println(room.lastElement().getRoomID());//Solo il proprietario può sapere l'id
                        writeRoom();//Salvo i dati della room all'interno di un file
                        break;
                    case "search":  //Cerca la room
                        RoomID = ricevi.readLine();
                        for (int i = 0; i < room.size(); i++) {

                            if (room.get(i).getRoomID().equals(RoomID)) {
                                s = true;
                                scrittore.println(room.get(i).getNomeRoom());                             //Se l'utente si vuole unire entra dentro
                                risposta = ricevi.readLine();
                                if (risposta.equals("entr")) { //La stringa che il client gli invia prmendo un bottone
                                    partecipante = ricevi.readLine();//
                                    room.add(new Room(clientSocket, room.get(i).getNomeRoom(), room.get(i).getOwner(), room.get(i).getRoomID(), partecipante));//Aggiunge un partecipante 
                                    scrittore.println(room.get(i).getNomeRoom());
                                    scrittore.println(room.get(i).getOwner());
                                    MandaPartecipante(i);//Una volta che l'utente è dentro serve a l'utente sapere tutti i partecipanti di quella room
                                    writeRoom(); //Scrivo la room per un eventuale shutdown del server 
                                } else {
                                    break;
                                }

                            } else if (i == room.size() - 1 && s == false) {
                                scrittore.println("Room non trovata");
                            }
                        }
                        break;
                    case "delt":
                        partecipante = ricevi.readLine();
                        RoomID = ricevi.readLine();
                        rimuoviUtente(partecipante, RoomID);
                        break;
                    case "chatData":  //Serve per la chat
                        partecipante = ricevi.readLine();//Riceve il nome utente
                        retriveRoomData();
                        Controlla(partecipante);
                        System.out.println("chatdata");
                        break;
                    case "chat":
                        gestioneMessaggi();
                        break;
                    case "remove":
                        partecipante = ricevi.readLine();
                        for (int i = 0; i < room.size(); i++) {
                            if (room.get(i).getPartecipante().equals(partecipante)) {
                                room.remove(i);
                                writeRoom();
                            }
                        }
                        break;

                }

            } while (!protocollo.equals("exit"));

            //Aspetta il messaggio del client
            ricevi.close();
            scrittore.close();
            clientSocket.close();
        } catch (IOException ex) {

        }
    }
/**
 * Il metodo scrive sul file della chatroom modificando i parametri
 * @throws IOException Eccezione che viene gestita tramite ,appunto, il "throws IOException"
 */
    private void writeRoom() throws IOException {  //Serve tenere traccia dei vari delle room

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
/**
 * Il metodo scrive quando in client accede al suo account con già dei progressi fatti 
 * @param partecipante Nome del client
 * @throws IOException Eccezione che viene gestita tramite ,appunto, il "throws IOException"
 */
    private void Controlla(String partecipante) throws IOException {
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
        // System.out.println(room.size());
        System.out.println("passa");
        if (room.size() > 0) {//Nel caso non possiede room
            for (int i = 0; i < room.size(); i++) {

                if (room.get(i).getPartecipante().equals(partecipante)) {

                    scrittore.println(room.get(i).getRoomID());
                    scrittore.println(room.get(i).getOwner());
                    scrittore.println(room.get(i).getNomeRoom());
                    MandaPartecipante(i);

                } else if (i == room.size() - 1) {
                    scrittore.println("stop");

                }
            }
        } else {
            scrittore.println("stop");
        }
        scrittore.println("stop");

    }
/**
 * Il metodo manda le chat ai membri appartenenti al gruppo quando sono attivi
 * @param x Indice dell'utente comunicante con il server
 * @throws IOException Eccezione che viene gestita tramite ,appunto, il "throws IOException"
 */
    private void MandaPartecipante(int x) throws IOException {  //Serve per mandare tutti i paretecipanti di quella room al client
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader ricevi = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        for (int i = 0; i < room.size(); i++) {
            if (room.get(i).getRoomID().equals(room.get(x).getRoomID())) {
                System.out.println("scrivi ");
                scrittore.println(room.get(i).getPartecipante());

            }
        }
        scrittore.println("stop");

    }
/**
 * Il metodo rimuove un utente inserendo l'username e il codice della room appartenente
 * @param partecipante Username Utente
 * @param RoomID ID della room
 * @throws IOException Eccezione che viene gestita tramite ,appunto, il "throws IOException"
 */
    private void rimuoviUtente(String partecipante, String RoomID) throws IOException {
        PrintWriter scrittore = new PrintWriter(clientSocket.getOutputStream(), true);

        for (int i = 0; i < room.size(); i++) {
            if (room.get(i).getPartecipante().equals(partecipante) && room.get(i).getRoomID().equals(RoomID)) {
                room.remove(i);

            }
        }

    }
/**
 * Il metodo gestisce i messaggi nella chatroom
 * @throws IOException Eccezione che viene gestita tramite ,appunto, il "throws IOException"
 */
    private void gestioneMessaggi() throws IOException {
        BufferedReader ricevi = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        ServerBase sb = new ServerBase();
        ArrayList<Socket> cliente = sb.getSocket2();// Riceve il collegamento di tutte le socket dal metodo serverBase 
        System.out.println(cliente.size());
        String RoomID = ricevi.readLine();
        messaggio = ricevi.readLine();//Messaggio inviato da un client
        String partecipante = ricevi.readLine();
        for (int i = 0; i < room.size(); i++) {
            if (room.get(i).getRoomID().equals(RoomID)) {

                for (int x = 0; x < cliente.size(); x++) {
                    System.out.println(cliente.get(x).getInetAddress().toString());
                   
                    if (cliente.get(x).getInetAddress().equals(room.get(i).getClientSocket().getInetAddress())) {
                        System.out.println("scirvi2323234");
                        PrintWriter scrittore = new PrintWriter(cliente.get(i).getOutputStream(), true);
                        scrittore.println("[" + partecipante + "]: " + messaggio);
                        break;
                    }
                }

            }

        }

    }
/**
 * Il metodo legge dal file i dati della chatroom e li salva nell'arraylist
 * @throws IOException Eccezione che viene gestita tramite ,appunto, il "throws IOException"
 */
    private void retriveRoomData() throws IOException {
        Socket s2;
        String[] salva;
        String s;
        f = new File("C:\\Users\\" + userName + "\\Desktop\\DiscosalesServer\\RoomRoute.txt");

        if (f.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            s = br.readLine();
            while (s != null) {
                salva = s.split(";");
                System.out.println("prova3");
                s2=new Socket(salva[0].substring(1),6666);
            

                room.add(new Room(s2, salva[1], salva[2], salva[3], salva[4]));
                s = br.readLine();

                System.out.println("eccezione");

            }

            System.out.println("prova10");
            br.close();
        }

    }


}
