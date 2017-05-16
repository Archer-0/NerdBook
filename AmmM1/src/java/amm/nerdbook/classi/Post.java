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
    private Gruppo gruppo;
    private String contenuto;
    private String urlAllegato;
    private Type postType;
    
    public Post() {
        this.id = 0;
        this.autore = null;
        this.contenuto = "";
        this.postType = Type.TEXT;
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
     * @return the gruppo
     */
    public Gruppo getGruppo() {
        return gruppo;
    }

    /**
     * @param gruppo the gruppo to set
     */
    public void setGruppo(Gruppo gruppo) {
        this.gruppo = gruppo;
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
     * @return the postType
     */
    public Type getPostType() {
        return postType;
    }

    /**
     * @param postType the postType to set
     */
    public void setPostType(Type postType) {
        this.postType = postType;
    }
    
}
