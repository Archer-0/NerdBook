/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook;

import amm.nerdbook.classi.Gruppo;
import amm.nerdbook.classi.GruppoFactory;
import amm.nerdbook.classi.PostFactory;
import amm.nerdbook.classi.Utente;
import amm.nerdbook.classi.UtenteFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_CLEAN_PATH = "../../web/WEB-INF/db/ammdb";
    private static final String DB_BUILD_PATH = "WEB-INF/db/ammdb";
    
    @Override
    public void init(){
       //String dbConnection = "jdbc:derby:" + this.getServletContext().getRealPath("/") + DB_BUILD_PATH;
       String dbConnection = "jdbc:derby://localhost:1527/ammdb";
       
       try {
           Class.forName(JDBC_DRIVER);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
       }
       UtenteFactory.getInstance().setConnectionString(dbConnection);
       GruppoFactory.getInstance().setConnectionString(dbConnection);
       PostFactory.getInstance().setConnectionString(dbConnection);
   }

    private static final String SERVNAME = "[Login-Servlet]-";
    
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
            // log
            System.out.print(SERVNAME + "User with id: " + request.getParameter("loggedUserId"));
            
            session.invalidate();
            request.setAttribute("loggedIn", false);
            request.setAttribute("loggedUserId", "");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
            // log
            System.out.println("logged out.");
            
            return;
        }
        
        // log
        System.out.println(SERVNAME + "Checking user... ");
        
        // se l'utente e' loggato
        if (session.getAttribute("loggedIn") !=  null && session.getAttribute("loggedIn").equals(true)) {
            int userId = (int)session.getAttribute("loggedUserId");
            Utente loggedUser = UtenteFactory.getInstance().getUtenteById(userId);
            
            if (session.getAttribute("loggedUser") == null)
                session.setAttribute("loggedUser", loggedUser);
            
            System.out.println(SERVNAME + "User already logged");
            
            request.getRequestDispatcher("Bacehca").forward(request, response);
        }
        else {
            //log
            System.out.println(SERVNAME + "User not logged");
            
            //recupero dati di accesso
            String email = request.getParameter("email").toString();
            String passwd = request.getParameter("pass");
            
            // se i campi di autenticazione non sono vuoti
            if (email != null && passwd != null) {
                int loggedUserId = UtenteFactory.getInstance().getIdByEmailAndPassword(email, passwd);
                
                // errore e' indicato con 0 perche' 1 e' l'utente root
                if (loggedUserId >= 1) {
                    Utente loggedUser = UtenteFactory.getInstance().getUtenteById(loggedUserId);
                    
                    System.out.println(SERVNAME + "User login correct");
                    
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("loggedUserId", loggedUserId);
                    session.setAttribute("loggedUser", loggedUser);
                    
                    List<Utente> users = UtenteFactory.getInstance().getListaUtenti(0);
                    session.setAttribute("users", users);
                    
                    System.out.println(SERVNAME + "Users list loaded");

                    List<Gruppo> groups = GruppoFactory.getInstance().getListaGruppi(0);
                    session.setAttribute("groups", groups);
                    System.out.println(SERVNAME + "Group list loaded");

                    if (!this.userDetailsComplete(UtenteFactory.getInstance().getUtenteById(loggedUserId))) {
                        response.sendRedirect("Profilo?userIdToVisit=" + loggedUserId);
                        System.out.println(SERVNAME + "User details not completed. Redirecting user to profile page");
                    }
                    else {
                        response.sendRedirect("Bacheca?userIdToVisit=" + loggedUserId);
                        System.out.println(SERVNAME + "Redirecting User to Bacheca");
                    }
                }
                else {
                    System.out.println(SERVNAME + "User not registered. Reloading login page");
                    request.setAttribute("invalidAccountData", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } 
            }
            else {
                System.out.println(SERVNAME + "Login fields not completed");
                request.setAttribute("invalidAccountData", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
        //request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    
    
    /**
     * Controlla se tutti i dettagli di un account sono completi
     * @param loggedUser un oggetto di tipo Utente
     * @return boolean true se tutti i dettagli dell'oggetto utente sono completi o false se anche solo uno e' vuoto
     */
    public boolean userDetailsComplete(Utente loggedUser) {
        if (loggedUser != null) {
            return !(loggedUser.getNome() == null ||
                     loggedUser.getCognome() == null ||
                     loggedUser.getUrlFotoProfilo() == null ||
                     loggedUser.getCitazione() == null ||
                     loggedUser.getDataNascita() == null ||
                     loggedUser.getNome().equals("") || 
                     loggedUser.getCognome().equals("") || 
                     loggedUser.getUrlFotoProfilo().equals("img/default.png") || 
                     loggedUser.getUrlFotoProfilo().equals("") ||
                     loggedUser.getCitazione().equals(""));
        }
        else {
            System.out.println(SERVNAME + "Logged user == null");
            return false;
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
