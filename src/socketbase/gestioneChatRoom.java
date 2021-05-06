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
public class gestioneChatRoom {

    HashMap<String, Room> room = new HashMap();
    private genera g = new genera();

    
    
    
    public void creaNuovaRoom(Socket client, String nomeRoom, String owner) {
        room.put(g.GetKeyRoom(), new Room(client, nomeRoom, owner));

    }

}
