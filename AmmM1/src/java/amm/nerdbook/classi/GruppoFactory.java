/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook.classi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author archer
 */
public class GruppoFactory {
    
    private static GruppoFactory singleton;
    private String connectionString;
    
    private static final String CLASSNAME = "[GruppoFactory-class]-";
    
    private static final String USERNAME = "matt";
    private static final String PASSWORD = "eggman";
    
    public static GruppoFactory getInstance() {
        if (singleton == null) {
            singleton = new GruppoFactory();
        }
        return singleton;
    }
    
    
    private GruppoFactory() {
        /* Ci siamo spostati, venite a trovarci nel database.*/
    }
    
    public Gruppo getGroupById(int id) {
            
        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            // comando SQL
            String query = "SELECT * FROM gruppo WHERE id = ?";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            // si associa al/ai "?" della query i parametri inserire nella query
            prepStmt.setInt(1, id);
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            // restituzione delle righe ottenute alla funzione chiamante
            if (res.next()) {
                
                Gruppo group = new Gruppo();
                
                group.setId(res.getInt("id"));
                group.setNome(res.getString("nome"));
                group.setUrlImmaggine(res.getString("url_immagine"));
                group.setnUtenti(res.getInt("n_utenti"));
                
                // rilascio delle risorse
                prepStmt.close();
                conn.close();
                
                // restituisco l'oggetto richiesto
                return group;
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations (Function: getGroupById(...))");
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Gruppo> searchGroups(String nomeGruppo) {
        
        List<Gruppo> listaGruppi = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            // comando SQL
            String query = "SELECT * FROM gruppo WHERE nome LIKE ?";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            // si associa al/ai "?" della query i parametri inserire nella query
            prepStmt.setString(1, nomeGruppo);
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            // restituzione delle righe ottenute alla funzione chiamante
            while (res.next()) {
                
                Gruppo group = new Gruppo();
                
                group.setId(res.getInt("id"));
                group.setNome(res.getString("nome"));
                group.setUrlImmaggine(res.getString("url_immagine"));
                group.setnUtenti(res.getInt("n_utenti"));
                
                listaGruppi.add(group);
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations (Function: searchGroups(...))");
            ex.printStackTrace();
        }
        
        return listaGruppi;
    }
    
     /**
     *  @param n_groups numero di utenti da mettere nella lista
     * - se n_groups <= 0 restituisce una lista con tutti i gruppi presenti nella lista
     * - se n_groupss > 0 resitituisce una lista di n_groups oggetti di tipo Gruppo ordinati in 
     *      modo casuale
     *  @return List di Gruppo
     */

    public List<Gruppo> getListaGruppi(int n_groups) {        
        
        List<Gruppo> listaGruppi = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            // comando SQL
            String query = "SELECT * FROM gruppo";
            
            // Prepered Statement (contro SQLInjections)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            // restituzione delle righe ottenute alla funzione chiamante
            while (res.next()) {
                
                Gruppo group = new Gruppo();
                
                group.setId(res.getInt("id"));
                group.setNome(res.getString("nome"));
                group.setUrlImmaggine(res.getString("url_immagine"));
                group.setnUtenti(res.getInt("n_utenti"));
                
                listaGruppi.add(group);
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations (Function: getListaGruppi(...))");
            ex.printStackTrace();
        }
        
        // restituisce lista di n_groups elementi casuali
        if (n_groups > 0) {
            List<Gruppo> listaGruppiRidotta = new ArrayList<>();
            int counter = 0;
            int n_groups_to_add = n_groups;
            
            /* finche' "n_groups" non raggiunge lo 0 o "listaGruppi" si svuota:
             * - si aggiunge a "listaGruppiRidotta" un gruppo casuale ad ogni ciclo, rimuovendolo da "listaGruppi"
             * - si utilizza come limite superiore della funzione "random()" la dimensione di "listaGruppi"
             *      (in quanto i llimite superiore della funzione e' esclusivo, quindi l'intervallo sara' sempre compreso tra
             *       0 e (size() - 1) )
             */
            while (n_groups > 0 && !listaGruppi.isEmpty()) {
                listaGruppiRidotta.add(listaGruppi.remove((int) (0+Math.random()*(listaGruppi.size()))));
                
                n_groups --;
                counter ++;
            }
            
            // log
            System.out.println(CLASSNAME + "Groups added in listaGruppiRidotta: " + counter + "/" + n_groups_to_add + " [added_groups/groups_requested]");
            
            return listaGruppiRidotta;
        }
        
        // restituisce la lista completa
        // log
        System.out.println(CLASSNAME + "Groups added in listaGruppi: " + listaGruppi.size());

        return listaGruppi;
    }
    
    public boolean addUserToGroup(int groupId, int userId) {
        
        
        
        return true;
    }
    
    public void setConnectionString(String s){
	this.connectionString = s;
    }
    
    public String getConnectionString(){
	return this.connectionString;
    }

}