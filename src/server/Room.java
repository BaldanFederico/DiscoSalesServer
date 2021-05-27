/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.*;
import java.util.*;

/**
 *
 * @author dell
 */
public class Room {
    private Socket clientSocket;
    private String nomeRoom;
    private String owner;//un id univoco per ogni utente
    private String RoomID; //un id univoco per la room
    private String partecipante;

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

    @Override
    public String toString() {
        return "Room{" + "clientSocket=" + clientSocket + ", nomeRoom=" + nomeRoom + ", owner=" + owner + ", RoomID=" + RoomID + ", partecipante=" + partecipante + '}';
    }

}
