/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 * La classe gestisce i dati dell'utente 
 * @author DiscoSales
 */
public class Utente {
    private String nome;
    private String password;
    private String email;
    private String codice;
    private boolean stato;

/**
 * Costruttore che instanzia l'utente
 * @param nome Nome utente
 * @param password Pw utente
 * @param Email Mail utente
 * @param codice Codice
 * @param stato Stato dell'utente
 */
    public Utente(String nome, String password, String Email, String codice, boolean stato) {
        this.nome = nome;
        this.password = password;
        this.email = Email;
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

        return email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }
}