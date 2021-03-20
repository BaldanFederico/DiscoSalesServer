/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketbase;

import java.util.Random;

/**
 *
 * @author dell
 */
public class genera {

    private String caratteri = "ABCDEFGHILMNOPQRSTUVZWYKabcdefghilmnopqrstuvzkxwy0123456789";

    
    
     public String codice() {
        Random r = new Random();
        String randomString = "";
        int length = 10;
        char[] text = new char[length];

        for (int x = 0; x < text.length; x++) {
            text[x] = caratteri.charAt(r.nextInt(caratteri.length()));
            randomString += text[x];
        }
        return randomString;

    }

   
     
     public void mandaMail() {

    }

}
