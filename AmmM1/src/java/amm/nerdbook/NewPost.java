/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook;

import amm.nerdbook.classi.Post;
import amm.nerdbook.classi.PostFactory;
import amm.nerdbook.classi.Utente;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author archer
 */
public class NewPost extends HttpServlet {

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
        
        System.out.print("[NewPost-Servlet]-Checking session...");
        HttpSession session = request.getSession(false);
        
        if (session != null && session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true)) {
            // salvo l'utente per un utilizzo futuro
            Utente utente = (Utente)session.getAttribute("loggedUser");
            System.out.println("[NewPost-Servlet]-(user_already_logged)");
            
            
        
//            // quando viene richiesta la creazione di un nuvo post
//            // viene mostrato un riepilogo con i dati inseriti dall'utente
//            // vengono perciò impostati gli attributi e successivamente vengono salvati permanentemente
//            
//            if (request.getAttribute("newPostRequest") != null && request.getAttribute("newPostRequest").equals(true)) {
//                if (request.getAttribute("newPostContent") != null && request.getAttribute("newPostContent").equals("") == false) {
//                    request.setAttribute("newPostContent", request.getParameter("newPostContent"));
//                    request.setAttribute("allegatoType", request.getParameter("allegatoType"));
//                    System.out.println("[Bacheca-Servlet]-Here");
//                    // se l'allegato ha un allegato
//                    if (request.getAttribute("allegatoType") != "not") {
//                        // se l'url dell'allegato non e' vuoto
//                        if (request.getParameter("urlAllegato").equals("") == false) {
//                            request.setAttribute("urlAllegato", request.getParameter("urlAllegato"));
//                            // se il nome dell'allegato non e' vuoto
//                            if (request.getAttribute("nomeAllegato").equals("") == false) {
//                                // viene impostato il nome dell'allegato
//                                request.setAttribute("nomeAllegato", request.getParameter("nomeAllegato"));
//                            }
//                            // se il nome dell'allegato e' vuoto
//                            else {
//                                // viene usato come nome dell'allegato l'url completo
//                                request.setAttribute("nomeAllegato", request.getAttribute("urlAllegato"));
//                            }
//                        }
//                        // se l'url dell'allegato e' vuoto
//                        else {
//                            request.setAttribute("allegatoType", "not");
//                        }
//                    }
//                    int userIdToVisit;
//                    int groupIdToVisit;
//                    if (request.getParameter("userIdToVisit") != null && request.getParameter("groupIdToVisit") == null) {
//                        userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
//                        request.setAttribute("userIdToVisit", userIdToVisit);
//                    }
//                    if (request.getParameter("groupIdToVisit") == null && request.getParameter("groupIdToVisit") != null) {
//                        groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
//                        request.setAttribute("groupIdToVisit", groupIdToVisit);
//                    }
//
//                    request.setAttribute("newPostRequest", true);
//                    request.getRequestDispatcher("bacheca.jsp").forward(request, response);
//                }
//                else {
//                    // pulire gli attributi impostati in precedenza per la revisione
//                    request.setAttribute("newPostContent", null);
//                    request.setAttribute("allegatoType", null);
//                    request.setAttribute("urlAllegato", null);
//                    request.setAttribute("nomeAllegato", null);
//                    System.out.println("[Bacheca-Servlet]-New post content is empty: refused");
//                    request.getRequestDispatcher("bacheca.jsp").forward(request, response);
//                }
//            }
//            // quando il post viene approvato e viene richiesto il salvataggio
//            else {
//                if (request.getAttribute("newPostApproved") != null && request.getAttribute("newPostApproved").equals(true)) {
//                    int userIdToVisit = -1;
//                    int groupIdToVisit = -1;
//                    if (request.getParameter("userIdToVisit") != null && request.getParameter("groupIdToVisit") == null) {
//                        userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
//                        request.setAttribute("userIdToVisit", userIdToVisit);
//                    }
//                    if (request.getParameter("groupIdToVisit") == null && request.getParameter("groupIdToVisit") != null) {
//                        groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
//                        request.setAttribute("groupIdToVisit", groupIdToVisit);
//                    }
//
//                    System.out.println("[Bacheca-Servlet]-AllegatoType: " + request.getAttribute("allegatoType"));
//                    if (request.getAttribute("allegatoType").equals("not")) {
//                        PostFactory.getInstance().newPost((int)session.getAttribute("loggedUserId"), 
//                            userIdToVisit, 
//                            groupIdToVisit, 
//                            request.getParameter("newPostContent"), 
//                            request.getParameter("urlAllegato"),
//                            Post.Type.TEXT);
//                    }
//                    else{
//                        if (request.getAttribute("allegatoType").equals("img")) {
//                            PostFactory.getInstance().newPost((int)session.getAttribute("loggedUserId"), 
//                                userIdToVisit, 
//                                groupIdToVisit, 
//                                request.getParameter("newPostContent"), 
//                                request.getParameter("urlAllegato"),
//                                Post.Type.TEXT_AND_IMAGE);
//                        }
//                        else{
//                            if (request.getAttribute("allegatoType").equals("link")) {
//                                PostFactory.getInstance().newPost((int)session.getAttribute("loggedUserId"), 
//                                userIdToVisit, 
//                                groupIdToVisit, 
//                                request.getParameter("newPostContent"), 
//                                request.getParameter("urlAllegato"),
//                                Post.Type.TEXT_AND_LINK);
//                            }
//                        }
//                    }
//
//                    request.setAttribute("newPostRequest", null);
//                    request.getRequestDispatcher("bacheca.jsp").forward(request, response);
//                }
//                else {
//                    if (request.getAttribute("newPostApproved") == null) {
//                        System.out.println("[Bacheca-Servlet]-Internal error: Attribute(newPostApproved): " + request.getAttribute("newPostApproved"));
//                        int userIdToVisit;
//                        int groupIdToVisit;
//                        if (request.getParameter("userIdToVisit") != null && request.getParameter("groupIdToVisit") == null) {
//                            userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
//                            request.setAttribute("userIdToVisit", userIdToVisit);
//                            request.getRequestDispatcher("bacheca.jsp").forward(request, response);
//                        }
//                        if (request.getParameter("groupIdToVisit") == null && request.getParameter("groupIdToVisit") != null) {
//                            groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
//                            request.setAttribute("groupIdToVisit", groupIdToVisit);
//                            request.getRequestDispatcher("bacheca.jsp").forward(request, response);
//                        } 
//                    }
//                }   
//            }
        }
        else {
                System.out.println("[NewPost-Servlet]-User not logged (illegal access) redirecting user to login");
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
        return "Short description";
    }// </editor-fold>

}
