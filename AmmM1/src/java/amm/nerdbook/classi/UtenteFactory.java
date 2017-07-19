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
public class UtenteFactory {
    
    //Pattern Design Singleton
    private static UtenteFactory singleton;
    // connString
    private String connectionString;
    // className (for logs)
    private static final String CLASSNAME = "[UtenteFactory-class]-";
    
    private static final String USERNAME = "matt";
    private static final String PASSWORD = "eggman";
    
    public static UtenteFactory getInstance() {
        if (singleton == null) {
            singleton = new UtenteFactory();
        }
        return singleton;
    }
    
    // creazione utenti
    private UtenteFactory() {
        /* Ci siamo spostati, venite a trovarci nel database.*/
    }
    
    public Utente getUtenteById(int id) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            // comando SQL
            String query = "SELECT * FROM utente WHERE id = ?";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            // si associa al/ai "?" della query i parametri inserire nella query
            prepStmt.setInt(1, id);
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            // restituzione delle righe ottenute alla funzione chiamante
            if (res.next()) {
                
                Utente user = new Utente();
                
                user.setId(res.getInt("id"));
                user.setNome(res.getString("nome"));
                user.setCognome(res.getString("cognome"));
                user.setDataNascita(res.getDate("data_nascita").toString());
                user.setEmail(res.getString("email"));
                user.setPassword(res.getString("passwd"));
                user.setUrlFotoProfilo(res.getString("url_foto_profilo"));
                user.setCitazione(res.getString("citazione"));
                
                // rilascio delle risorse
                prepStmt.close();
                conn.close();
                
                // restituisco l'oggetto richiesto
                return user;
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations [Function: getUtenteById(...)]");
            ex.printStackTrace();
        }
        return null;
    }
    
    public int getIdByEmailAndPassword(String email, String password) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            // comando SQL
            String query = "SELECT id FROM utente WHERE email = ? AND passwd = ?";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            // si associa al/ai "?" della query i parametri inserire nella query
            prepStmt.setString(1, email);
            prepStmt.setString(2, password);
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            if (res.next()) {
                int id = res.getInt("id");
                
                prepStmt.close();
                conn.close();
                
                System.out.println(CLASSNAME + "User id: " + id);
                
                return id;
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations [Function: getIdByEmailAndPassword(...)]");
            ex.printStackTrace();
        }
        // ritorno -1 in caso di errore perche' 0 e' assegnato come utente root
        return -1;
    }
    
    /**
     *  @param n_users numero di utenti da mettere nella lista
     * - se n_users <= 0 restituisce una lista con tutti gli utenti presenti nella lista
     * - se n_users > 0 resitituisce una lista di n_users oggetti di tipo Utente ordinati in 
     *      modo casuale
     *  @return List di Utente
     */

    public List<Utente> getListaUtenti(int n_users) {
        
        List<Utente> listaUtenti = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            // comando SQL
            String query = "SELECT * FROM utente ";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            while (res.next()) {
                // creazione nuovo utente da mettere nella lista
                Utente user = new Utente();
                
                // popolamento informazioni utente
                user.setId(res.getInt("id"));
                user.setNome(res.getString("nome"));
                user.setCognome(res.getString("cognome"));
                user.setDataNascita(res.getDate("data_nascita").toString());
                user.setEmail(res.getString("email"));
                user.setPassword(res.getString("passwd"));
                user.setUrlFotoProfilo(res.getString("url_foto_profilo"));
                user.setCitazione(res.getString("citazione"));
                
                // aggiunta dell'utente alla lista
                listaUtenti.add(user);
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations [Function: getListaUtenti(...)]");
            ex.printStackTrace();
        }
        
        // restituisce lista di n_users elementi casuali
        if (n_users > 0) {
            List<Utente> listaUtentiRidotta = new ArrayList<>();
            int counter = 0;
            int n_users_to_add = n_users;
            
            /* finche' "n_users" non raggiunge lo 0 o "listaUtenti" si svuota:
             * - si aggiunge a "listaUtentiRidotta" un utente casuale ad ogni ciclo, rimuovendolo da "listaUtenti"
             * - si utilizza come limite superiore della funzione "random()" la dimensione di "listaUtenti"
             *      (in quanto i llimite superiore della funzione e' esclusivo, quindi l'intervallo sara' sempre compreso tra
             *       0 e (size() - 1) )
             */
            while (n_users > 0 && !listaUtenti.isEmpty()) {
                listaUtentiRidotta.add(listaUtenti.remove((int) (0+Math.random()*(listaUtenti.size()))));
                
                n_users --;
                counter ++;
            }
            
            // log
            System.out.println(CLASSNAME + "Users added in listaUtentiRidotta: " + counter + "/" + n_users_to_add + " [added_users/users_requested]");
            
            return listaUtentiRidotta;
        }
        
        // restituisce la lista completa
        // log
        System.out.println(CLASSNAME + "Users added in listaUtenti: " + listaUtenti.size());

        return listaUtenti;
    }
    
    public List searchUsers(String searchQuery) {
        
        List<Utente> listaUtenti = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            // comando SQL
            String query = "SELECT * FROM utente WHERE nome LIKE ? OR cognome LIKE ?";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            prepStmt.setString(1, "%" + searchQuery + "%");
            
            prepStmt.setString(2, "%" + searchQuery + "%");
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            while (res.next()) {
                // creazione nuovo utente da mettere nella lista
                Utente user = new Utente();
                
                // popolamento informazioni utente
                user.setId(res.getInt("id"));
                user.setNome(res.getString("nome"));
                user.setCognome(res.getString("cognome"));
                user.setDataNascita(res.getDate("data_nascita").toString());
                user.setEmail(res.getString("email"));
                user.setPassword(res.getString("passwd"));
                user.setUrlFotoProfilo(res.getString("url_foto_profilo"));
                user.setCitazione(res.getString("citazione"));
                
                // aggiunta dell'utente alla lista
                listaUtenti.add(user);
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            
            // log
            System.out.println(CLASSNAME + "ERROR while executing SQL operations [Function: searchUsers(...)]");
            ex.printStackTrace();
        }
        
        return listaUtenti;
    }
    
    public boolean addUtente() {
        
        System.out.println(CLASSNAME + "ERROR while executing SQL operations [Function: addUtente(...)]");
        return false;
    }
    
    public boolean removeUserCompletely(int userId) throws SQLException {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            String removeUserPosts = "DELETE FROM post WHERE autore = ?";
            String removeUserFromGroups = "DELETE FROM gruppo_utente WHERE utente_id = ?";
            String removeUser = "DELETE FROM utente WHERE id = ?";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement posts = null;
            PreparedStatement groups = null;
            PreparedStatement user = null;
            
            boolean flag = true;
        
        try {
            conn.setAutoCommit(false);
            
            posts = conn.prepareStatement(removeUserPosts);
            groups = conn.prepareStatement(removeUserFromGroups);
            user = conn.prepareStatement(removeUser);
            
            posts.setInt(1, userId);
            posts.executeUpdate();
            
            conn.commit();
            
            groups.setInt(1, userId);
            groups.executeUpdate();
            
            conn.commit();
            
            user.setInt(1, userId);
            user.executeUpdate();
            
            conn.commit();
            
        } catch (SQLException ex) {
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations, trying transaction rollback [Function: searchUsers(...)]");
            ex.printStackTrace();
            flag = false;
            
            try {
            
                conn.rollback();
                System.out.println(CLASSNAME + "Transaction rollback successfully executed [Function: searchUsers(...)]");
                
            } catch (SQLException excep) {
            
                excep.printStackTrace();
                System.err.println(CLASSNAME + "ERROR transaction rollback failed [Function: searchUsers(...)]");
            
            }
        } finally {
            if (posts != null )
                posts.close();
            if (groups != null)
                groups.close();
            if (user != null)
                user.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        
        return flag;
    }

    public void setConnectionString(String s){
	this.connectionString = s;
    }
    
    public String getConnectionString(){
	return this.connectionString;
    }

}

