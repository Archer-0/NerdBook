/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook.classi;

/**
 *
 * @author archer
 */
public class Post {
    
    public enum Type {TEXT, TEXT_AND_IMAGE, TEXT_AND_LINK};
    
    private int id;
    private Utente autore;
    private Utente toUser;
    private Gruppo toGroup;
    private String contenuto;
    private String urlAllegato;
    private String nomeAllegato;
    private Post.Type tipo;
    private String oraPubblicazione;
    private String dataPubblicazione;
    
    public Post() {
        this.id = -1;
        this.autore = null;
        this.toUser = null;
        this.toGroup = null;
        this.contenuto = "";
        this.urlAllegato = "";
        this.nomeAllegato = "";
        this.tipo = Type.TEXT;
        this.oraPubblicazione = "hh:mm:ss";
        this.dataPubblicazione = "yyyy:mm:dd";
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
     * @return the autore
     */
    public Utente getAutore() {
        return autore;
    }

    /**
     * @param autore the autore to set
     */
    public void setAutore(Utente autore) {
        this.autore = autore;
    }

    /**
     * @return the toUser
     */
    public Utente getToUser() {
        return toUser;
    }

    /**
     * @param toUser the toUser to set
     */
    public void setToUser(Utente toUser) {
        this.toUser = toUser;
    }

    /**
     * @return the gruppo
     */
    public Gruppo getToGroup() {
        return toGroup;
    }

    /**
     * @param toGroup the gruppo to set
     */
    public void setToGroup(Gruppo toGroup) {
        this.toGroup = toGroup;
    }

    /**
     * @return the contenuto
     */
    public String getContenuto() {
        return contenuto;
    }

    /**
     * @param contenuto the contenuto to set
     */
    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    /**
     * @return the urlImmagine
     */
    public String getUrlAllegato() {
        return urlAllegato;
    }

    /**
     * @param urlAllegato the urlImmagine to set
     */
    public void setUrlAllegato(String urlAllegato) {
        this.urlAllegato = urlAllegato;
    }

    /**
     * @return the nomeAllegato
     */
    public String getNomeAllegato() {
        return nomeAllegato;
    }

    /**
     * @param nomeAllegato the nomeAllegato to set
     */
    public void setNomeAllegato(String nomeAllegato) {
        this.nomeAllegato = nomeAllegato;
    }

    /**
     * @return the tipo
     */
    public Type getTipo() {
        return tipo;
    }

    /**
     * @param tipo the postType to set
     */
    public void setTipo(Type tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the oraPubblicazione
     */
    public String getOraPubblicazione() {
        return oraPubblicazione;
    }

    /**
     * @param oraPubblicazione the oraPubblicazione to set
     */
    public void setOraPubblicazione(String oraPubblicazione) {
        this.oraPubblicazione = oraPubblicazione;
    }

    /**
     * @return the dataPubblicazione
     */
    public String getDataPubblicazione() {
        return dataPubblicazione;
    }

    /**
     * @param dataPubblicazione the dataPubblicazione to set
     */
    public void setDataPubblicazione(String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }
    
}
