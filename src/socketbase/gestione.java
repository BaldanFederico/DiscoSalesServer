/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class gestione {

    ArrayList<utente> utente = new ArrayList();
    private genera g = new genera();
    private BufferedWriter bw;
    private BufferedReader br;
    private File f;
    private String userName = System.getProperty("user.name");

    public String salvaUtenti(String nome, String password, String Email) throws IOException {
        f = new File("C:\\Users\\" + userName + "\\Desktop\\DiscosalesServer\\utenti.txt");
        for (int i = 0; i < utente.size(); i++) {
            if (utente.get(i).getNome().equals(nome)) {
                return "il nome esiste già";
            }

        }
        utente.add(new utente(nome, password, Email, g.codice(), false));
        f.createNewFile();
        scrivi();
        return "registrazione avvenuta con successo";
    }

    public String autenticazione(String nome, String password) {
        for (int i = 0; i < utente.size(); i++) {
            if (utente.get(i).getNome().equals(nome) && utente.get(i).getPassword().equals(password)) {
                if (utente.get(i).getStato() == false) {

                    return "questo account non è stato ancora attivato, andare sulla sezione attiva account per l'attivazione";

                } else {

                    return "autenticazione avvenuta con successo";

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
                    System.out.println("conto già attivato");

                } else {
                    stato = true;
                    utente.get(x).setStato(stato);
                    scrivi();
                    return "conto attivato";

                }
            } else if (x == utente.size() - 1) {
                return "codice non valido";

            }

        }

        return "nessun utente ancora registrato";
    }

    public void usersStoraging() throws IOException {
        String codice, nome, password, Email;
        boolean stato;

        String[] salva;
        String s;
        f = new File("C:\\Users\\" + userName + "\\Desktop\\DiscosalesServer\\utenti.txt");
        if (f.exists()) {
            br = new BufferedReader(new FileReader(f));
            s = br.readLine();
            while (s != null) {
                salva = s.split(";");
                nome = salva[0];
                password = salva[1];
                Email = salva[2];
                codice = salva[3];
                stato = Boolean.parseBoolean(salva[4]);

                utente.add(new utente(nome, password, Email, codice, stato));
                s = br.readLine();
            }

        }

    }

    public void CreateFolder() {
        f = new File("C:\\Users\\" + userName + "\\Desktop\\DiscosalesServer");

        if (!f.exists()) {
            f.mkdir();
        }

    }

    private void scrivi() throws IOException {
        bw = new BufferedWriter(new FileWriter(f));
        for (int i = 0; i < utente.size(); i++) {
            bw.write(utente.get(i).getNome() + " ");
            bw.write(utente.get(i).getPassword() + " ");
            bw.write(utente.get(i).getEmail() + " ");
            bw.write(utente.get(i).getCodice() + " ");
            bw.write(utente.get(i).getStato() + " ");
            bw.newLine();
            bw.flush();

        }
        bw.close();
    }

}
