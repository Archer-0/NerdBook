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
public class Gruppo {
    private int id;
    private int nUtenti;
    private String nome;
    private String urlImmagine;
    /*    private ArrayList<Integer> idUtentiAderenti;*/

    public Gruppo() {
        this.id = 0;
        this.nUtenti = 0;
        this.nome = "";
        this.urlImmagine = "";
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
     * @return the nUtenti
     */
    public int getnUtenti() {
        return nUtenti;
    }

    /**
     * @param nUtenti the nUtenti to set
     */
    public void setnUtenti(int nUtenti) {
        this.nUtenti = nUtenti;
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
     * @return the urlImaggine
     */
    public String getUrlImmagine() {
        return urlImmagine;
    }

    /**
     * @param urlImmagine the urlImmagine to set
     */
    public void setUrlImmaggine(String urlImmagine) {
        this.urlImmagine = urlImmagine;
    }

/*
    public ArrayList<Integer> getIdUtentiAderenti() {
        return idUtentiAderenti;
    }
    
    public void addUtenteAderente(int id) {
        this.idUtentiAderenti.add(id);
    }
*/
}