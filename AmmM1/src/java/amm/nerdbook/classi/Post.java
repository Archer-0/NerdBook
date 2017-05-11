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
    
    public enum Type {TEXT, IMAGE, LINK, HIBRID, OTHER};
    
    private int id;
    private int idAutore;
    private Utente autore;
    private String contenuto;
    private String urlImmagine;
    private String urlLink;
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
     * @return the idAutore
     */
    public int getIdAutore() {
        return idAutore;
    }

    /**
     * @param idAutore the idAutore to set
     */
    public void setIdAutore(int idAutore) {
        this.idAutore = idAutore;
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
    public String getUrlImmagine() {
        return urlImmagine;
    }

    /**
     * @param urlImmagine the urlImmagine to set
     */
    public void setUrlImmagine(String urlImmagine) {
        this.urlImmagine = urlImmagine;
    }

    /**
     * @return the urlLink
     */
    public String getUrlLink() {
        return urlLink;
    }

    /**
     * @param urlLink the urlLink to set
     */
    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
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
