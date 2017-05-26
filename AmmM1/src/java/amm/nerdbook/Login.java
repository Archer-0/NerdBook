/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook;

import amm.nerdbook.classi.Gruppo;
import amm.nerdbook.classi.GruppoFactory;
import amm.nerdbook.classi.Utente;
import amm.nerdbook.classi.UtenteFactory;
import java.io.IOException;
import java.util.ArrayList;
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
        
       HttpSession session = request.getSession(true);
        
        if (request.getParameter("logout") != null) {
            session.invalidate();
            request.setAttribute("loggedIn", false);
            request.setAttribute("loggedUserId", "");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // se l'utente e' loggato
        System.out.println("[Login-Servlet]-Checking user...");
        if (session.getAttribute("loggedIn") !=  null && session.getAttribute("loggedIn").equals(true)) {
            int userId = (int)session.getAttribute("loggedUserId");
            Utente loggedUser = UtenteFactory.getInstance().getUtenteById(userId);
            session.setAttribute("loggedUser", loggedUser);
            System.out.println("[Login-Servlet]-User already logged");
            request.getRequestDispatcher("Bacehca").forward(request, response);
        }
        else {
            System.out.println("[Login-Servlet]-User not logged");
            String email = request.getParameter("email");
            String passwd = request.getParameter("pass");
            
            if (email != null && passwd != null) {
                int loggedUserId = UtenteFactory.getInstance().getIdByEmailAndPassword(email, passwd);
                
                // errore e' indicato con -2 perche' -1 e' l'utente root
                if (loggedUserId != -2) {
                    Utente loggedUser = UtenteFactory.getInstance().getUtenteById(loggedUserId);
                    
                    System.out.println("[Login-Servlet]-User login correct");
                    
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("loggedUserId", loggedUserId);
                    session.setAttribute("loggedUser", loggedUser);
                }
                else {
                    System.out.println("[Login-Servlet]-User not registered. Reloading login page");
                    request.setAttribute("invalidAccountData", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
                
                ArrayList<Utente> users = UtenteFactory.getInstance().getListaUtenti();
                session.setAttribute("users", users);
        
                ArrayList<Gruppo> groups = GruppoFactory.getInstance().getListaGruppi();
                session.setAttribute("groups", groups);
                
                if (this.userDetailsComplete(UtenteFactory.getInstance().getUtenteById(loggedUserId)) == false) {
                    request.getRequestDispatcher("profilo.jsp").forward(request, response);
                    System.out.println("[Login-Servlet]-User details not completed. Redirecting user to profile page");
                }
                else {
                    request.getRequestDispatcher("Bacheca?userIdToVisit=" + loggedUserId).forward(request, response);
                    System.out.println("[Login-Servlet]-Redirecting User to Bacheca");
                }
                return;
            }
            else {
                System.out.println("[Login-Servlet]-Login fields not completed");
                request.setAttribute("invalidAccountData", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    
    public boolean userDetailsComplete(Utente loggedUser) {
        if (loggedUser.getNome().equals("") || loggedUser.getCognome().equals("") || loggedUser.getUrlFotoProfilo().equals("img/default.jpg") 
                || loggedUser.getCitazione().equals("") || loggedUser.getDataNascita().equals("")) {
            return false;
        }
        return true;
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
}
