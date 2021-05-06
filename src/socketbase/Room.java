/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketbase;

import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author dell
 */
public class Room {

    private Socket clientSocket;
    private String nomeRoom;
    private String owner;

    public Room(Socket clientSocket, String nomeRoom, String owner) {

        this.clientSocket = clientSocket;
        this.nomeRoom = nomeRoom;
        this.owner = owner;
    }

}
