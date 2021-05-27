/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author agostinelli.luca
 * @author parrarodriguez.manue
 */
public class Utente {

    private String nome;
    private String password;
    private String Email;
    private String codice;
    private boolean stato;

  

    public Utente(String nome, String password, String Email, String codice, boolean stato) {
        this.nome = nome;
        this.password = password;
        this.Email = Email;
        this.codice = codice;
        this.stato = stato;
    }

    public String getCodice() {
        return codice;
    }

    public boolean getStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {

        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
}