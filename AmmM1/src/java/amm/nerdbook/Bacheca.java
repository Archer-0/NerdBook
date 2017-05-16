/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook;

import amm.nerdbook.classi.Gruppo;
import amm.nerdbook.classi.GruppoFactory;
import amm.nerdbook.classi.Post;
import amm.nerdbook.classi.PostFactory;
import amm.nerdbook.classi.Utente;
import amm.nerdbook.classi.UtenteFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author archer
 */
public class Bacheca extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        
        // inizializzazione attributo utenti per essere visualizzati nella sidebar
        ArrayList<Utente> utenti = UtenteFactory.getInstance().getListaUtenti();
        request.setAttribute("utenti", utenti);
        
        ArrayList<Gruppo> gruppi = GruppoFactory.getInstance().getListaGruppi();
        request.setAttribute("gruppi", gruppi);
        
        
        // se la sessione esiste e l'attributo loggedIn == true
        if (session != null && session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true)) {

            // se e' impostato il parametro "utente", viene visualizzata la bacheca di quell'utente
            // controllo se user e' impostato
            String user = request.getParameter("user");
            int loggedUserId;
            
            // se user e' impostato metto nella variabile userId il valore di user
            if (user != null) {
                loggedUserId = (int)Integer.parseInt(user);
            }
            // se user non e' ancora impostato prelevo il valore della sessione
            else {
                loggedUserId = (int)session.getAttribute("loggedUserId");
            }
            
            //salvo in "utente" tutta la struttura utente 
            Utente loggedUser = UtenteFactory.getInstance().getUtenteById(loggedUserId);
            
            // se l'id dell'utente esiste, esiste anche l'utente e posso prelevare i propri post
            if (loggedUser != null) {
                request.setAttribute("loggedUser", loggedUser);
                
                List<Post> posts = PostFactory.getInstance().getPostListByUser(loggedUser);
                request.setAttribute("posts", posts);

                request.getRequestDispatcher("bacheca.jsp").forward(request, response);
            } // altrimenti se l'id dell'utente non e' presente negli id conosciuti viene visualizzata ina pagina di errore
            else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } // se non esiste la sessione o l'utente non e' loggato ( o entrambi)
        else {
            request.setAttribute("illegalAccess", true);
            request.getRequestDispatcher("Login").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
