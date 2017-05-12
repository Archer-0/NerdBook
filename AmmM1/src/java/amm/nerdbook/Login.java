/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook;

import amm.nerdbook.classi.Utente;
import amm.nerdbook.classi.UtenteFactory;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "Login1", urlPatterns = {"/Login1"})
public class Login extends HttpServlet {

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
        
        // Se il parametro Get == "logout", la sessione viene distrutta
        if (request.getParameter("logout") != null) {
            session.invalidate();
            request.setAttribute("loggedIn", false);
            request.setAttribute("loggedUser", "");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        
        // se l'utente e' loggato e "LoggedIn" vale true
        if (session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true)) {
            int loggedUserId = (int) session.getAttribute("loggedUserId");
            Utente utente = UtenteFactory.getInstance().getUtenteById(loggedUserId);
            if (utente.getNome().equals("") || utente.getCognome().equals("") || utente.getUrlFotoProfilo().equals("") || utente.getCitazione().equals("")) {
                request.getRequestDispatcher("Profilo").forward(request, response);
            }
            else {
                request.getRequestDispatcher("Bacheca").forward(request, response);
            }
            
            return;
        }
        // se l'utente non e' loggato si carica la pagina di login e si effettuano i controlli 
        else {
            // si salvano i dati inseriti nel form in variabili
            String email = request.getParameter("email");
            String password = request.getParameter("pass");
            
            if (email != null && password != null) {
                // cerco l'utente tramite email e password
                int loggedUserId = UtenteFactory.getInstance().getIdByUserAndPassword(email, password);
                // se la funzione restituisce un valore != -2 vuol dire che l'utente esiste ed e' valido
                if (loggedUserId != -2) {
                    Utente utente = UtenteFactory.getInstance().getUtenteById(loggedUserId);
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("loggedUser", utente.getNome() + " " + utente.getCognome());
                    
                    if (utente.getNome().equals("") || utente.getCognome().equals("") || utente.getUrlFotoProfilo().equals("") || utente.getCitazione().equals("")) {
                        request.getRequestDispatcher("Profilo").forward(request, response);
                    }
                    else {
                        request.getRequestDispatcher("Bacheca").forward(request, response);
                    }
                    return;
                }
                // se la coppia email e password non e' valida
                else {
                    request.setAttribute("invalidAccountData", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            } 
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
