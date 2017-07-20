/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook;

import amm.nerdbook.classi.PostFactory;
import amm.nerdbook.classi.Utente;
import amm.nerdbook.classi.UtenteFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author archer
 */
public class DeletePost extends HttpServlet {
    
    private final static String SERVNAME = "[DeletePost-Servlet]";
    
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
        
        if (session != null && session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true)) {
            Utente utente = (Utente) session.getAttribute("loggedUser");

            if (request.getParameter("deletePost") != null && request.getParameter("deletePost").equals("true")) {
                int userIdToVisit = 0;
                int groupIdToVisit = 0;

                if (utente.getId() == 1 || utente.getId() == Integer.parseInt(request.getParameter("authorId"))) {
                    if (PostFactory.getInstance().removePost(Integer.parseInt(request.getParameter("postId")))) {
                        request.removeAttribute("postId");
                        request.removeAttribute("authorId");
                        request.removeAttribute("deletePost");
                    }
                }

                if (request.getParameter("userIdToVisit") != null) {
                    userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
                    request.setAttribute("userIdToVisit", userIdToVisit);
                }

                if (request.getParameter("groupIdToVisit") != null) {
                    groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
                    request.setAttribute("groupIdToVisit", groupIdToVisit);
                }  

                //request.getRequestDispatcher("Bacheca");
                
               /*   
                if (userIdToVisit != 0)
                        request.getRequestDispatcher("Bacheca?userIdToVisit=" + userIdToVisit).forward(request, response);
                else if (groupIdToVisit != 0)
                        request.getRequestDispatcher("bacheca.jsp?groupIdToVisit=" + groupIdToVisit).forward(request, response);
               */ 

                if (userIdToVisit != 0)
                    response.sendRedirect("Bacheca?userIdToVisit=" + userIdToVisit);
                else if (groupIdToVisit != 0)
                    response.sendRedirect("Bacheca?groupIdToVisit=" + groupIdToVisit);
            }
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
