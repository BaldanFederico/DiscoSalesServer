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

/**
 *
 * @author dell
 */
public class Gestione {
  ArrayList<Utente> utente = new ArrayList();
    private Genera g = new Genera();
    private BufferedWriter bw;
    private BufferedReader br;
    private File f;
    private String entra = null;
    private String userName = System.getProperty("user.name");
    
    public String salvaUtenti(String nome, String password, String Email) throws IOException {
        String codice;
        codice=g.GetcodiceU();
        f = new File("C:\\Users\\" + userName + "\\Desktop\\DiscosalesServer\\utenti.txt");
        for (int i = 0; i < utente.size(); i++) {
            if (utente.get(i).getNome().equals(nome)) {
                return "il nome esiste già";
            }

        }
        utente.add(new Utente(nome, password, Email, codice, false));
        f.createNewFile();
        scrivi(f);
        g.mandaMail(codice,Email);
        return "registrazione avvenuta con successo";
    }

    public String autenticazione(String nome, String password) throws IOException {
        System.out.println("dentro il metodo");

        for (int i = 0; i < utente.size(); i++) {
            if (utente.get(i).getNome().equals(nome) && utente.get(i).getPassword().equals(password)) {
                if (utente.get(i).getStato() == false) {

                    return "questo account non è stato ancora attivato, andare sulla sezione attiva account per l'attivazione";

                } else {
                    setEntra("enterAccount");
//                invio di un protocollo che permette di cambiare scena al cliente
                    return "enterAccount";//protocollo permette di cambiare la scena del client

                }

            }

        }

        return "credenziali sbagliate";
    }

    public String verificaAccount(String codice) throws IOException {
        boolean stato;
        bw = new BufferedWriter(new FileWriter(f, true));
        for (int x = 0; x < utente.size(); x++) {

            if (utente.get(x).getCodice().equals(codice)) {
                if (utente.get(x).getStato() == true) {
                    System.out.println("account già attivato");

                } else {
                    stato = true;
                    utente.get(x).setStato(stato);
                    scrivi(f);
                    return "account attivato";

                }
            } else if (x == utente.size() - 1) {
                return "codice non valido";

            }

        }

        return "nessun utente ancora registrato";
    }

    public void usersStoraging() throws IOException {
        boolean stato;
        String[] salva;
        String s;
        f = new File("C:\\Users\\" + userName + "\\Desktop\\DiscosalesServer\\utenti.txt");

        if (f.exists()) {
            br = new BufferedReader(new FileReader(f));
            s = br.readLine();
            while (s != null) {
                salva = s.split(";");

                stato = Boolean.parseBoolean(salva[4]);

                utente.add(new Utente(salva[0], salva[1], salva[2], salva[3], stato));
                s = br.readLine();
                System.out.println("prova2");
            }

            System.out.println("prova4");
            br.close();
        }

    }

    public void CreateFolder() {
        f = new File("C:\\Users\\" + userName + "\\Desktop\\DiscosalesServer");

        if (!f.exists()) {
            f.mkdir();
        }

    }

    private void scrivi(File f) throws IOException {
        bw = new BufferedWriter(new FileWriter(f));
        
       String codice;
        for (int i = 0; i < utente.size(); i++) {
            bw.write(utente.get(i).getNome() + ";");
            bw.write(utente.get(i).getPassword() + ";");
            bw.write(utente.get(i).getEmail() + ";");
            bw.write(utente.get(i).getCodice() + ";");
            bw.write(utente.get(i).getStato() + ";");
            bw.newLine();
            bw.flush();
         
        
        }
        
        bw.close();
    }

    public void setEntra(String entra) {
        this.entra = entra;
    }

    public String getEntra() {
        return entra;
    }

    //metodo che legge nel file contenente gli id degli utenti con le rispettive room appartenenti
}
