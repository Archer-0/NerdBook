/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook;

import amm.nerdbook.classi.Utente;
import amm.nerdbook.classi.UtenteFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author archer
 */
@WebServlet(loadOnStartup = 0)
public class Login extends HttpServlet {

    // variabile "loggedUser" = attributo sessione dell'utente che ha effettuato il  login
    
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
        
        // apertura sessione
        HttpSession session = request.getSession();
        
        // Se e' gia stato richiesto un logout distruggo la sessione
        if (request.getParameter("logout") != null) {
            session.invalidate();
            request.setAttribute("loggedIn", false);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // se l'utente e' loggato e "loggedIn" vale true
        if (session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true)) {
            Utente loggedUser;
            int usrId;

            usrId = (int)session.getAttribute("loggedUserId");
            loggedUser = UtenteFactory.getInstance().getUtenteById(usrId);
            
            session.setAttribute("loggedUser", loggedUser);
            
            request.getRequestDispatcher("Bacheca").forward(request, response);
            return;
        }
        // se l'utente non e' loggato, si carica la pagina di login e si effettuano i controlli 
        else {
            // si salvano i dati inseriti nel form in variabili
            String email = request.getParameter("email");
            String password = request.getParameter("pass");
            
            // se sono stati inseriti tutti i campi nel form di login si verificano
            if (email != null && password != null) {
                // cerco l'utente tramite email e password
                int loggedUserId = UtenteFactory.getInstance().getIdByEmailAndPassword(email, password);
                
                // se la funzioutentene restituisce un valore != -2 vuol dire che l'utente esiste ed e' valido
                if (loggedUserId != -2) {
                    // cerco l'utente tramite il suo id
                    Utente loggedUser = UtenteFactory.getInstance().getUtenteById(loggedUserId);
                    // imposto il flag di avvenuto login a true
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("loggedUserId", loggedUserId);
                    // imposo la variabile di sessione di loggedUser in modo da poter recuperare qualsiasi attributo direttamente dalle jsp
                    session.setAttribute("loggedUser", loggedUser);
                    
                    // se si rileva che i dettagli dell'account non sono completi si rimanda l'utente alla pagina di login
                    if (userDetailsCompleted(loggedUser) == false) {
                        request.getRequestDispatcher("Profilo").forward(request, response);
                    }
                    // altrimenti si rimanda l'utente alla propria bacheca
                    else {
                        request.getRequestDispatcher("Bacheca").forward(request, response);
                    }
                    return;
                }
                // se la coppia email e password non e' valida
                else {
                    // imposto il flag di per stampare l'avviso sulla pagina
                    request.setAttribute("invalidAccountData", true);
                    // ricarico la pagina di login
                    request.getRequestDispatcher("Login").forward(request, response);
                    return;
                }
            }
        }
        // se l'attributo loggedIn e' null o non e' true ricarico la pagina di login
        request.getRequestDispatcher("Login").forward(request, response);
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

    /**
     * Controlla se tutti i dettagli di un account sono completi
     * @param utente un oggetto di tipo Utente
     * @return boolean true se tutti i dettagli dell'oggetto utente sono completi o false se anche solo uno e' vuoto
     */
    public boolean userDetailsCompleted (Utente utente) {
        if (utente.getNome().equals("") || utente.getCognome().equals("") || utente.getUrlFotoProfilo().equals("") 
                || utente.getCitazione().equals("") || utente.getDataNascita().equals("")) {
            return false;
        }
        return true;
    }
    
}
