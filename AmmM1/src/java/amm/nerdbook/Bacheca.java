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
    
    final String SERVNAME = "[Bacheca-Servlet]-";
    
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
        
        System.out.print(SERVNAME + "Checking session...");
        HttpSession session = request.getSession(false);
        
        // utente gia' loggato
        if (session != null && session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true)) {
            // salvo l'utente per un utilizzo futuro
            Utente utente = (Utente)session.getAttribute("loggedUser");
            System.out.println(SERVNAME + "User already logged");
            
            // visualizzazione dei post di un utente
            if (request.getParameter("userIdToVisit") != null && request.getParameter("groupIdToVisit") == null) {
                int userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
                Utente userToVisit = UtenteFactory.getInstance().getUtenteById(userIdToVisit);
                
                if (userToVisit != null && userToVisit.getId() != -2) {
                    // l'utente root puo vedere tutti i post
                    if (utente.getId() == -1 && utente.getId() == userIdToVisit) {
                        List<Post>posts = PostFactory.getInstance().getPostList();
                        request.setAttribute("posts", posts);
                        request.getRequestDispatcher("bacheca.jsp").forward(request, response);
                        return;
                    }
                    request.setAttribute("userToVisit", userToVisit);
                    List<Post>posts = PostFactory.getInstance().getPostListByUser(userToVisit);
                    request.setAttribute("posts", posts);
                    request.getRequestDispatcher("bacheca.jsp").forward(request, response);
                }
                else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } // visualizzazione dei post di un gruppo
            else if (request.getParameter("groupIdToVisit") != null && request.getParameter("userIdToVisit") == null) {
                int groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
                Gruppo groupToVisit = GruppoFactory.getInstance().getGroupById(groupIdToVisit);
            
                if (groupToVisit != null) {
                    request.setAttribute("groupToVisit", groupToVisit);
                    List<Post>posts = PostFactory.getInstance().getPostListByGroup(groupToVisit);
                    request.setAttribute("posts", posts);
                    request.getRequestDispatcher("bacheca.jsp").forward(request, response);
                }
                else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
            else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            System.out.println("(user not logged: illegal access)");
            request.setAttribute("illegalAccess", true);
            request.getRequestDispatcher("Login?logout=true").forward(request, response);
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
        return "This is a Servlet. Yeah!";
    }// </editor-fold>

}
