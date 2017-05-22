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
        
        System.out.print("[Bacheca-Servlet]-Checking session...");
        HttpSession session = request.getSession(false);
        
        if (session != null && session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true)) {
            // salvo l'utente per un utilizzo futuro
            Utente utente = (Utente)session.getAttribute("loggedUser");
            System.out.println("(user_already_logged)");
            
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
            
            // quando viene richiesta la creazione di un nuvo post
            // viene mostrato un riepilogo con i dati inseriti dall'utente
            // vengono perciò impostati gli attributi e successivamente vengono salvati permanentemente
            if (request.getAttribute("newPostRequest") != null && request.getAttribute("newPostRequest").equals(true)) {
                if (request.getAttribute("newPostContent") != null && request.getAttribute("newPostContent").equals("") == false) {
                    request.setAttribute("newPostContent", request.getParameter("newPostContent"));
                    /* TODO: 
                     * - impostare tutti gli attributi per la revisione del post prima di inviarlo 
                     * - 
                     */
                }
                else {
                    // pulire gli attributi impostati in precedenza per la revisione
                    System.out.println("[Bacheca-Servlet]-New post content is empty: refused");
                    request.getRequestDispatcher("bacheca.jsp").forward(request, response);
                }
            }
            // quando viene richiesto che il post venga approvato
            else {
                if (request.getAttribute("newPostApproved") != null && request.getAttribute("newPostApproved").equals(true)) {
                    int userIdToVisit = -1;
                    int groupIdToVisit = -1;
                    if (request.getParameter("userIdToVisit") != null && request.getParameter("groupIdToVisit") == null) {
                        userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
                        System.out.println("[Bacheca-Servlet]-Post to person (" + userIdToVisit + ")");
                    }
                    if (request.getParameter("groupIdToVisit") == null && request.getParameter("groupIdToVisit") != null) {
                        groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
                        System.out.println("[Bacheca-Servlet]-Post to group (" + groupIdToVisit + ")");
                    }
                        
                    if (request.getAttribute("allegatoType").equals("not")) {
                        PostFactory.getInstance().newPost((int)session.getAttribute("loggedUserId"), 
                            userIdToVisit, 
                            groupIdToVisit, 
                            request.getParameter("newPostContent"), 
                            request.getParameter("urlAllegato"),
                            Post.Type.TEXT);
                    }
                    else{
                        if (request.getAttribute("allegatoType").equals("img")) {
                            PostFactory.getInstance().newPost((int)session.getAttribute("loggedUserId"), 
                                userIdToVisit, 
                                groupIdToVisit, 
                                request.getParameter("newPostContent"), 
                                request.getParameter("urlAllegato"),
                                Post.Type.TEXT_AND_IMAGE);
                        }
                        else{
                            if (request.getAttribute("allegatoType").equals("link")) {
                            PostFactory.getInstance().newPost((int)session.getAttribute("loggedUserId"), 
                                userIdToVisit, 
                                groupIdToVisit, 
                                request.getParameter("newPostContent"), 
                                request.getParameter("urlAllegato"),
                                Post.Type.TEXT_AND_LINK);
                            }
                        }
                    }
                }
                else {
                    if (request.getAttribute("newPostApproved") == null) {
                        System.out.println("[Bacheca-Servlet]-Internal error: Attribute(newPostApproved) == null");
                        request.getRequestDispatcher("bacheca.jsp").forward(request, response);
                    }
                }
            }
        }
        else {
            System.out.println("(user not logged: illegal access)");
            request.setAttribute("illegalAccess", true);
            request.getRequestDispatcher("login.jsp?logout=true").forward(request, response);
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
