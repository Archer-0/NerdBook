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
public class GruppoFactory {
    private static GruppoFactory singleton;
    
    public static GruppoFactory getInstance() {
        if (singleton == null) {
            singleton = new GruppoFactory();
        }
        return singleton;
    }
    
    private ArrayList<Gruppo> listaGruppi = new ArrayList<>();
    
    private GruppoFactory() {
        GruppoFactory gruppoFactory = GruppoFactory.getInstance();
        
        Gruppo g0 = new Gruppo();
        g0.setId(0);
        g0.setNome("The BE SHARPS");
        g0.setUrlImmaggine("img/group0.jpg");
        g0.setnUtenti(1);
        
        listaGruppi.add(g0);
        
        Gruppo g1 = new Gruppo();
        g1.setId(1);
        g1.setNome("Sgt. Pepper Lonely Hearts Club Band");
        g1.setUrlImmaggine("img/group1.jpg");
        g1.setnUtenti(2);
        
        listaGruppi.add(g1);
    }
    
    public Gruppo getGroupById(int id) {
        for (Gruppo gruppo : this.listaGruppi) {
            if (gruppo.getId() == id) {
                return gruppo;
            }
        }
        return null;
    }
    
    public Gruppo getGroupByName(String nomeGruppo) {
        for (Gruppo gruppo : this.listaGruppi) {
            if (gruppo.getNome().equals(nomeGruppo)) {
                return gruppo;
            }
        }
        return null;
    }
    
}
