/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook.classi;

import java.util.ArrayList;

/**
 *
 * @author archer
 */
public class UtenteFactory {
    //Pattern Design Singleton
    private static UtenteFactory singleton;

    public static UtenteFactory getInstance() {
        if (singleton == null) {
            singleton = new UtenteFactory();
        }
        return singleton;
    }
    
    private ArrayList<Utente> listaUtenti = new ArrayList<>();
    
    // creazione utenti
    private UtenteFactory() {
        // amministratore
        Utente admin = new Utente();
        admin.setId(-1);
        admin.setNome("Archer");
        admin.setCognome("Admin");
        admin.setDataNascita("00/00/0000");
        admin.setEmail("admin@nope.com");
        admin.setUrlFotoProfilo("img/profiloUser.jpg");
        admin.setPassword("theCacheIsALie");
        
        listaUtenti.add(admin);
        
        Utente u0 = new Utente();
        u0.setId(0);
        u0.setNome("Nurset");
        u0.setCognome("Gorke");
        u0.setDataNascita("03/06/1983");
        u0.setEmail("saltbae@nope.com");
        u0.setUrlFotoProfilo("img/profilo0");
        u0.setPassword("NoMoreSalt");
        
        listaUtenti.add(u0);
        
        Utente u1 = new Utente();
        u1.setId(1);
        u1.setNome("Donald");
        u1.setCognome("Trump");
        u1.setDataNascita("14/06/1946");
        u1.setEmail("donaldino@nope.com");
        u1.setUrlFotoProfilo("img/profilo1");
        u1.setPassword("LovinMexico");
        
        listaUtenti.add(u1);
        
        Utente u2 = new Utente();
        u2.setId(2);
        u2.setNome("Hilary");
        u2.setCognome("Clinton");
        u2.setDataNascita("26/09/1947");
        u2.setEmail("hilary@nope.com");
        u2.setUrlFotoProfilo("img/profilo2");
        u2.setPassword("LovinNuke");
        
        listaUtenti.add(u2);
        
        Utente u3 = new Utente();
        u3.setId(3);
        u3.setNome("Matt");
        u3.setCognome("Bellamy");
        u3.setDataNascita("09/06/1978");
        u3.setEmail("mattbellamy@nope.com");
        u3.setUrlFotoProfilo("img/people0.jpg");
        u3.setPassword("Drones123");
        
        listaUtenti.add(u3);
        
        Utente u4 = new Utente();
        u4.setId(4);
        u4.setNome("Freddie");
        u4.setCognome("Mercury");
        u4.setDataNascita("05/09/1946");
        u4.setEmail("freddie@nope.com");
        u4.setUrlFotoProfilo("img/people1");
        u4.setPassword("BreakFree");
        
        listaUtenti.add(u4);
        
        Utente u5 = new Utente();
        u5.setId(5);
        u5.setNome("Gabe");
        u5.setCognome("Newell");
        u5.setDataNascita("03/11/1962");
        u5.setEmail("gaben@nope.com");
        u5.setUrlFotoProfilo("img/people2");
        u5.setPassword("hl3");
       
        listaUtenti.add(u5);
    }
    
    public Utente getUtenteById(int id) {
        for (Utente ut : this.listaUtenti) {
            if (ut.getId() == id) {
                return ut;
            }
        }
        return null;
    }
    
    public int getIdByUserAndPassword(String user, String password) {
        for(Utente ut : this.listaUtenti) {
            if (ut.getNome().equals(user) && ut.getPassword().equals(password)) {
                return ut.getId();
            }
        }
        return -2;
    }
}
