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
            System.out.println("[NewPost-Servlet]-User already logged");
            
            String nomeAllegato = "";
            String newPostContent = "";
            String allegatoType = "";
            String urlAllegato = "";
            int groupIdToVisit = -1;
            int userIdToVisit = -1;
            
            if (request.getParameter("groupIdToVisit") != null) {
                groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
                System.out.println("[NewPost-Servlet]-Group ID to post to: " + groupIdToVisit);
            }
            if (request.getParameter("userIdToVisit") != null) {
                userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
                System.out.println("[NewPost-Servlet]-User ID to post to: " + userIdToVisit);
            }
            
            // se c'e' stata una richiesta per un nuovo post
            if (request.getParameter("newPostRequest") != null) {
                
                if (request.getParameter("newPostRequest").equals("needsConfirm") == true) {
                    // se la parte testuale del post non e' vuota
                    System.out.println("[NewPost-Servlet]-New post creation request");
                    if (request.getParameter("newPostContent") != null && request.getParameter("newPostContent").equals("") == false) {

                        newPostContent = request.getParameter("newPostContent");
                        System.out.println("[NewPost-Servlet]-Variable newPostContent: " + newPostContent);
                        allegatoType = request.getParameter("allegatoType");
                        System.out.println("[NewPost-Servlet]-Variable allegatoType: " + allegatoType);

                        // se l'allegato non e' vuoto
                        if (request.getAttribute("allegatoType") != "not") {
                            if (request.getParameter("urlAllegato").equals("") == false) {
                                urlAllegato = request.getParameter("urlAllegato");
                                System.out.println("[NewPost-Servlet]-Variable urlAllegato: " + urlAllegato);
                                // se il nome dell'allegato non e' vuoto
                                if (request.getParameter("nomeAllegato").equals("") == false) {
                                    // viene impostato il nome dell'allegato
                                    nomeAllegato = (String) request.getParameter("nomeAllegato");
                                }
                                // se il nome dell'allegato e' vuoto
                                else {
                                    // viene usato come nome dell'allegato l'url completo
                                    nomeAllegato = (String) request.getParameter("urlAllegato");
                                }
                            }
                            // se l'url dell'allegato e' vuoto
                            else {
                                request.setAttribute("allegatoType", "not");
                            }
                        }
                        // se il tipo dell'allegato e' not
                        else {
                            request.setAttribute("allegatoType", "not");
                        }
                        if (userIdToVisit > -1)
                            request.setAttribute("userToVisit", UtenteFactory.getInstance().getUtenteById(userIdToVisit));
                        if (groupIdToVisit > -1)
                            request.setAttribute("groupToVisit", GruppoFactory.getInstance().getGroupById(groupIdToVisit));
                        request.setAttribute("urlAllegato", urlAllegato);
                        request.setAttribute("nomeAllegato", nomeAllegato);
                        request.setAttribute("newPostContent", newPostContent);
                        request.setAttribute("revision", true);
                        request.getRequestDispatcher("bacheca.jsp").forward(request, response);
                    }
                    // la parte testuale del post e' vuota, l'utente viene rimandato alla bacheca
                    else {
                        request.setAttribute("revision", false);
                        if (userIdToVisit > -1)
                            request.setAttribute("userToVisit", UtenteFactory.getInstance().getUtenteById(userIdToVisit));
                        if (groupIdToVisit > -1)
                            request.setAttribute("groupToVisit", GruppoFactory.getInstance().getGroupById(groupIdToVisit));
                        
                        request.getRequestDispatcher("bacheca.jsp").forward(request, response);
                        return;
                    }
                }

                if (request.getParameter("newPostRequest").equals("approved") == true) {

                    System.out.println("[NewPost-Servlet]-Attribute postApproved: " + (String)request.getParameter("newpostRequest"));
                    
                    newPostContent = request.getParameter("newPostContent");
                    allegatoType = request.getParameter("allegatoType");
                    urlAllegato = request.getParameter("urlAllegato");
                    nomeAllegato = request.getParameter("nomeAllegato");

                    if (request.getParameter("groupIdToVisit") != null) {
                        groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
                        System.out.println("[NewPost-Servlet]-Group ID to post to: " + groupIdToVisit);
                    }
                    if (request.getParameter("userIdToVisit") != null) {
                        userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
                        System.out.println("[NewPost-Servlet]-User ID to post to: " + userIdToVisit);
                    }
                    
                    System.out.println("[NewPost-Servlet]-Post approved by user: saving post to DB");
                    // salvataggio del post
                    if (allegatoType.equals("not")) {
                        PostFactory.getInstance().newPost(utente.getId(),
                            userIdToVisit, 
                            groupIdToVisit, 
                            newPostContent, 
                            urlAllegato,
                            nomeAllegato,
                            Post.Type.TEXT);
                        System.out.println("[NewPost-Servlet]-Post type: ONLY TEXT");
                    }
                    else{
                        if (allegatoType.equals("img")) {
                            PostFactory.getInstance().newPost(utente.getId(), 
                                userIdToVisit, 
                                groupIdToVisit, 
                                newPostContent, 
                                urlAllegato,
                                nomeAllegato,
                                Post.Type.TEXT_AND_IMAGE);
                            System.out.println("[NewPost-Servlet]-Post type: TEXT AND IMAGE");
                        }
                        else{
                            if (allegatoType.equals("link")) {
                                PostFactory.getInstance().newPost(utente.getId(),
                                    userIdToVisit, 
                                    groupIdToVisit, 
                                    newPostContent, 
                                    urlAllegato,
                                    nomeAllegato,
                                    Post.Type.TEXT_AND_LINK);
                                System.out.println("[NewPost-Servlet]-Post type: TEXT AND LINK");
                            }
                        }
                    }
                    System.out.println("[NewPost-Servlet]-Post saved. Redirecting user to bacheca");
                    request.setAttribute("revision", false);
                }
                else {
                        System.out.println("[NewPost-Servlet]-Post refused by user. Redirecting user to bacheca");
                        request.setAttribute("revision", false);
                }
            }
                request.getRequestDispatcher("bacheca.jsp").forward(request, response);
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
