/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.*;
import java.util.*;

/**
 *La classe gestisce i dati della room 
 * @author DiscoSales
 */
public class Room {
    private Socket clientSocket;
    private String nomeRoom;
    private String owner;//Un id univoco per ogni utente
    private String RoomID; //Un id univoco per la room
    private String partecipante;
/**
 * Costruttore che instanzia l'utente
 * @param clientSocket ClientSocket
 * @param nomeRoom Nome della room
 * @param owner Proprietario della room
 * @param RoomID Codice della room
 * @param partecipante Client
 */
    public Room(Socket clientSocket, String nomeRoom, String owner, String RoomID, String partecipante) {
        this.clientSocket = clientSocket;
        this.nomeRoom = nomeRoom;
        this.owner = owner;
        this.RoomID = RoomID;
        this.partecipante = partecipante;
    }
  

    public String getOwner() {
        return owner;
    }

    public String getNomeRoom() {
        return nomeRoom;
    }

    public String getRoomID() {
        return RoomID;
    }

    public String getPartecipante() {
        return partecipante;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
/**
 * Il metodo ritorna una stringa con i dati della chatroom
 * @return Dati della chatroom
 */
    @Override
    public String toString() {
        return "Room{" + "clientSocket=" + clientSocket + ", nomeRoom=" + nomeRoom + ", owner=" + owner + ", RoomID=" + RoomID + ", partecipante=" + partecipante + '}';
    }

}
