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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author archer
 */
public class PostFactory {
    private static PostFactory singleton;
    private String connectionString;
   
    private static final String CLASSNAME = "[PostFactory-class]-";

    private static final String USERNAME = "matt";
    private static final String PASSWORD = "eggman";

    
    public static PostFactory getInstance() {
        if (singleton == null) {
            singleton = new PostFactory();
        }
        return singleton;
    }
        
    private PostFactory() {
        /* Ci siamo spostati, venite a trovarci nel database.*/
    }
    
    public Post getPostById(int id) {
        UtenteFactory uFact = UtenteFactory.getInstance();
        GruppoFactory gFact = GruppoFactory.getInstance();
        
        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            // comando SQL
            String query = "SELECT * FROM post JOIN post_type ON post.tipo = post_type.id" + 
                           " WHERE id = ?";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            // si associa al/ai "?" della query i parametri inserire nella query
            prepStmt.setInt(1, id);
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            // restituzione delle righe ottenute alla funzione chiamante
            if (res.next()) {
                
                Post post = new Post();
                
                post.setId(res.getInt("id"));
                post.setAutore(uFact.getUtenteById(res.getInt("autore")));
                post.setToUser(uFact.getUtenteById(res.getInt("to_user")));
                post.setToGroup(gFact.getGroupById(res.getInt("to_group")));
                post.setContenuto(res.getString("contenuto"));
                post.setUrlAllegato(res.getString("url_allegato"));
                post.setNomeAllegato(res.getString("nome_allegato"));
                post.setTipo(this.setType(res.getString("type_name")));
                post.setOraPubblicazione(res.getTime("ora_pubblicazione").toString());
                post.setDataPubblicazione(res.getDate("data_pubblicazione").toString());
                
                // rilascio delle risorse
                prepStmt.close();
                conn.close();
                
                // restituisco l'oggetto richiesto
                return post;
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations (Function: getPostById(...))");
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Post> getPostList() {
        UtenteFactory uFact = UtenteFactory.getInstance();
        GruppoFactory gFact = GruppoFactory.getInstance();
        
        List<Post> listaPost = new ArrayList<>();
        
        try { 
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            // comando SQL
            String query = "SELECT * FROM post JOIN post_type ON post.tipo = post_type.id" +
                           " ORDER BY data_pubblicazione, ora_pubblicazione DESC";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            // restituzione delle righe ottenute alla funzione chiamante
            while (res.next()) {
                
                Post post = new Post();
                
                post.setId(res.getInt("id"));
                post.setAutore(uFact.getUtenteById(res.getInt("autore")));
                post.setToUser(uFact.getUtenteById(res.getInt("to_user")));
                post.setToGroup(gFact.getGroupById(res.getInt("to_group")));
                post.setContenuto(res.getString("contenuto"));
                post.setUrlAllegato(res.getString("url_allegato"));
                post.setNomeAllegato(res.getString("nome_allegato"));
                post.setTipo(this.setType(res.getString("type_name")));
                post.setOraPubblicazione(res.getTime("ora_pubblicazione").toString());
                post.setDataPubblicazione(res.getDate("data_pubblicazione").toString());
                
                listaPost.add(post);
                
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations (Function: getPostList(...))");
            ex.printStackTrace();
        }
        
        return listaPost;
    }
    
    public List<Post> getPostListByUser(Utente user) {
        UtenteFactory uFact = UtenteFactory.getInstance();
        GruppoFactory gFact = GruppoFactory.getInstance();
        List<Post> listaPostUser = new ArrayList<>(); 
               
        try { 
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            // comando SQL
            String query = "SELECT * FROM post JOIN post_type ON post.tipo = post_type.id" + 
                           " WHERE to_user = ? OR autore = ?" +
                           " ORDER BY data_pubblicazione, ora_pubblicazione DESC";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            prepStmt.setInt(1, user.getId());
            
            prepStmt.setInt(2, user.getId());
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            // restituzione delle righe ottenute alla funzione chiamante
            while (res.next()) {
                
                Post post = new Post();
                
                post.setId(res.getInt("id"));
                post.setAutore(uFact.getUtenteById(res.getInt("autore")));
                post.setToUser(uFact.getUtenteById(res.getInt("to_user")));
                post.setToGroup(gFact.getGroupById(res.getInt("to_group")));
                post.setContenuto(res.getString("contenuto"));
                post.setUrlAllegato(res.getString("url_allegato"));
                post.setNomeAllegato(res.getString("nome_allegato"));
                post.setTipo(this.setType(res.getString("type_name")));
                post.setOraPubblicazione(res.getTime("ora_pubblicazione").toString());
                post.setDataPubblicazione(res.getDate("data_pubblicazione").toString());
                
                listaPostUser.add(post);
                
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations (Function: getPostByUser(...))");
            ex.printStackTrace();
        }
        
        return listaPostUser;
    }
    
    public List<Post> getPostListByGroup(Gruppo group) {
        UtenteFactory uFact = UtenteFactory.getInstance();
        GruppoFactory gFact = GruppoFactory.getInstance();
        
        List<Post> listaPostGroup = new ArrayList<>();
        
        
        try { 
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            // comando SQL
            String query = "SELECT * FROM post JOIN post_type ON post.tipo = post_type.id" + 
                           " WHERE to_group = ?" +
                           " ORDER BY data_pubblicazione, ora_pubblicazione DESC";
            
            // Prepered Statement (giusto per evitare problemi)
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            prepStmt.setInt(1, group.getId());
            
            // esecuzione della query
            ResultSet res = prepStmt.executeQuery();
            
            // restituzione delle righe ottenute alla funzione chiamante
            while (res.next()) {
                
                Post post = new Post();
                
                post.setId(res.getInt("id"));
                post.setAutore(uFact.getUtenteById(res.getInt("autore")));
                post.setToUser(uFact.getUtenteById(res.getInt("to_user")));
                post.setToGroup(gFact.getGroupById(res.getInt("to_group")));
                post.setContenuto(res.getString("contenuto"));
                post.setUrlAllegato(res.getString("url_allegato"));
                post.setNomeAllegato(res.getString("nome_allegato"));
                post.setTipo(this.setType(res.getString("type_name")));
                post.setOraPubblicazione(res.getTime("ora_pubblicazione").toString());
                post.setDataPubblicazione(res.getDate("data_pubblicazione").toString());
                
                listaPostGroup.add(post);
                
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            // log
            System.err.println(CLASSNAME + "ERROR while executing SQL operations (Function: getPostByGroup(...))");
            ex.printStackTrace();
        }
        
        return listaPostGroup;
    }
    
    public boolean newPost(int idAutore, int idToUser, int idToGroup, String content, String urlAllegato, String nomeAllegato, Post.Type postType) {
    //                             1            2               3               4                   5                   6                   7
        
        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            //                                      1       2         3         4           5               6         7             8               9
            String query = "INSERT INTO post (id, autore, to_user, to_group, contenuto, url_allegato, nome_allegato, tipo, ora_pubblicazione, data_pubblicazione)" + 
                           " VALUES (default, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIME, CURRENT_DATE)";
            //                                1  2  3  4  5  6  7     8 (auto)      9 (auto)
            
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            prepStmt.setInt(1, idAutore);
            
            // controlli addizionali
            if (idToUser <= 1 || idToUser == idAutore) {
                prepStmt.setNull(2, Types.INTEGER);
            } else {
                prepStmt.setInt(2, idToUser);
            }
            
            if (idToGroup <= 1) {
                prepStmt.setNull(3, Types.INTEGER);
            } else {
                prepStmt.setInt(3, idToGroup);
            }
            
            prepStmt.setString(4, content);
            
            // controlli addizionali
            if (urlAllegato == null || urlAllegato.isEmpty()) {
                prepStmt.setNull(5, Types.VARCHAR);
                prepStmt.setNull(6, Types.VARCHAR);
                prepStmt.setInt(7, this.getIntType(Post.Type.TEXT));
            } else {
                prepStmt.setInt(7, this.getIntType(postType));
                prepStmt.setString(5, urlAllegato);
                prepStmt.setString(6, nomeAllegato);
            }
            
            
            if (prepStmt.executeUpdate() == 1) 
                System.out.println(CLASSNAME + "New post added to DB (Function: newPost(...))");
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            //log 
            System.err.println(CLASSNAME + "ERROR while executing SQL operations (Function: newPost(...))");
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    public boolean removePost(int postId) {

        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            String query = "DELETE FROM post WHERE id = ?";
            
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            prepStmt.setInt(1, postId);
            
            prepStmt.executeUpdate();
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(CLASSNAME + "ERROR while executing SQL operations (Function: removePost(...))");
            ex.printStackTrace();
            return false;
        }

        return true;
    }
    
    // da utilizzare solo per l'eliminazione di un utente
    public boolean removePostListOfUser(int userId) {
        
        try {
            Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
            String query = "DELETE FROM post WHERE autore = ?";
            
            PreparedStatement prepStmt = conn.prepareStatement(query);
            
            prepStmt.setInt(1, userId);
            
            prepStmt.executeUpdate();
            
            prepStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println(CLASSNAME + "ERROR while executing SQL operations (Function: removePostListOfUser(...))");
            return false;
        }

        return true;
    }
    
    /* v2
    
    public boolean removePostListOfUser(int userId) throws SQLException {
            
        Connection conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            
        String query = "DELETE FROM post WHERE autore = ?";

        PreparedStatement prepStmt = null;
            
        boolean flag = true;
        
        try {
            conn.setAutoCommit(false);
            
            prepStmt = conn.prepareStatement(query);
            
            prepStmt.setInt(1, userId);
            
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println(CLASSNAME + "ERROR while executing SQL operations, trying transaction rollback (Function: removePostListOfUser(...))");
            try {
                conn.rollback();
            } catch (SQLException exc) {
                exc.printStackTrace();
                System.err.println(CLASSNAME + "ERROR transaction rollback (Function: removePostListOfUser(...))");
            }
            
            flag = false;
        } finally {
            
            if (prepStmt != null)
                prepStmt.close();
            
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        return flag;
    }
    */
    
    public Post.Type setType (String tipo) {
        
        switch (tipo) {
            case "TEXT_AND_IMAGE":
                return Post.Type.TEXT_AND_IMAGE;
            case "TEXT_AND_LINK":
                return Post.Type.TEXT_AND_LINK;
            default:
                return Post.Type.TEXT;
        }
        
    }
    
    public int getIntType(Post.Type tipo) {
        if (tipo == Post.Type.TEXT_AND_IMAGE) 
            return 2;
        else if (tipo == Post.Type.TEXT_AND_LINK) 
            return 3;
        else
            return 1;
    }
    
    public void setConnectionString(String s){
	this.connectionString = s;
    }
    
    public String getConnectionString(){
	return this.connectionString;
    }
    
}
