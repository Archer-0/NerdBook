/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook.classi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author archer
 */
public class PostFactory {
    private static PostFactory singleton;
    
    public static PostFactory getInstance() {
        if (singleton == null) {
            singleton = new PostFactory();
        }
        return singleton;
    }
    
    private ArrayList<Post> listaPost = new ArrayList<>();
    
    private PostFactory() {
        UtenteFactory uFactory = UtenteFactory.getInstance();
        GruppoFactory gFactory = GruppoFactory.getInstance();
        
        // Creazione dei post
        Post p0 = new Post();
        p0.setId(0);
        p0.setAutore(uFactory.getUtenteById(0));
        p0.setContenuto("Ci siamo stufati di questo meme. Basta. Non ne possiamo piu");
        p0.setUrlAllegato("");
        p0.setPostType(Post.Type.TEXT);
        
        listaPost.add(p0);
        
        Post p1 = new Post();
        p1.setId(1);
        p1.setAutore(uFactory.getUtenteById(1));
        p1.setContenuto("Io suggerirei di cambiarla con un'altra migliore.");
        p1.setUrlAllegato("img/post0.jpg");
        p1.setPostType(Post.Type.TEXT_AND_IMAGE);
        
        listaPost.add(p1);
        
        Post p2 = new Post();
        p2.setId(2);
        p2.setAutore(uFactory.getUtenteById(2));
        p2.setContenuto("Pff, dilettante.");
        p2.setUrlAllegato("https://www.youtube.com/watch?v=WkNL_cfVyWU");
        p2.setPostType(Post.Type.TEXT_AND_LINK);
        
        listaPost.add(p2);
        
        Post p3 = new Post();
        p3.setId(2);
        p3.setAutore(uFactory.getUtenteById(4));
        p3.setContenuto("Vorrei essere come voih!!!11!!1!");
        p3.setUrlAllegato("");
        p3.setPostType(Post.Type.TEXT);
        p3.setToGroup(gFactory.getGroupById(0));
        
        listaPost.add(p3);
        
        Post p4 = new Post();
        p4.setAutore(uFactory.getUtenteById(3));
        p4.setContenuto("DIG DOOOOOOOOWWWWN");
        p4.setUrlAllegato("https://www.youtube.com/watch?v=b4ozdiGys5g");
        p4.setPostType(Post.Type.TEXT_AND_LINK);
        p4.setToGroup(gFactory.getGroupById(0));
        
        listaPost.add(p4);
        
        Post p5 = new Post();
        p5.setAutore(uFactory.getUtenteById(5));
        p5.setContenuto("Gaben");
        p5.setUrlAllegato("");
        p5.setPostType(Post.Type.TEXT);
        p5.setToUser(uFactory.getUtenteById(0));
        
        listaPost.add(p5);
    }
    
    public Post getPostById(int id) {
        for(Post post : this.listaPost) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }
    
    public List<Post> getPostListByUser(Utente user) {
        List<Post> listaPostUser = new ArrayList<>();
        
        for(Post post : this.listaPost) {
            if (post.getAutore().equals(user)) {
                listaPostUser.add(post);
            }
        }
        return listaPostUser;
    }
    
    public List<Post> getPostListByGroup(Gruppo group) {
        List<Post> listaPostGroup = new ArrayList<>();
        
        for(Post post: this.listaPost) {
            if (post.getToGroup().equals(group)) {
                listaPostGroup.add(post);
            }
        }
        return listaPostGroup;
    }    
}
