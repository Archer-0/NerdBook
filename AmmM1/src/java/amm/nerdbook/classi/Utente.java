/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook.classi;

import java.io.Serializable;
import java.sql.Date;


/**
 *
 * @author archer
 */
public class Utente implements Serializable{
    private int id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String email;
    private String password;
    private String urlFotoProfilo;
    private String citazione;
    
    public Utente() {
        this.id = -2;
        this.nome = "";
        this.cognome = "";
        this.dataNascita = new Date(0);
        this.email = "";
        this.password = "";
        this.urlFotoProfilo = "img/default.png";
        this.citazione = "NerdBook does it better!";
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome the cognome to set
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return the dataNascita
     */
    public Date getDataNascita() {
        return dataNascita;
    }

    /**
     * @param dataNascita the dataNascita to set
     */
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the urlFotoProfilo
     */
    public String getUrlFotoProfilo() {
        return urlFotoProfilo;
    }

    /**
     * @param urlFotoProfilo the urlFotoProfilo to set
     */
    public void setUrlFotoProfilo(String urlFotoProfilo) {
        this.urlFotoProfilo = urlFotoProfilo;
    }
    
    /*
     * Override metodo equals
     */
    @Override
    public boolean equals(Object altroUtenteRegistrato) {
        if (altroUtenteRegistrato instanceof Utente) {
            if (this.getId() == ((Utente)altroUtenteRegistrato).getId())
                return true;
        }
        return false;
    }

    /**
     * @return the citazione
     */
    public String getCitazione() {
        return citazione;
    }

    /**
     * @param citazione the citazione to set
     */
    public void setCitazione(String citazione) {
        this.citazione = citazione;
    }
    
}
