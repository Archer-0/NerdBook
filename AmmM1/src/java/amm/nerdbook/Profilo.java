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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author archer
 */
public class Profilo extends HttpServlet {
    
    private final static String SERVNAME = "[Profilo-Servlet]-";

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
        
        if (session != null && session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true)) {
            Utente utente = (Utente)session.getAttribute("loggedUser");
            
            if (utente != null) {
                
                if (request.getParameter("userDetailsUpdated") != null) {
                    
                    if (request.getParameter("usrName").equals("") == false && request.getParameter("usrName").equals(utente.getNome()) == false) {
                        utente.setNome((String)request.getParameter("usrName"));
                        request.setAttribute("userDetailsUpdated", true);
                    }
                    if (request.getParameter("usrSurname").equals("") == false && request.getParameter("usrSurname").equals(utente.getCognome()) == false) {
                        utente.setCognome((String)request.getParameter("usrSurname"));
                        request.setAttribute("userDetailsUpdated", true);
                    }
                    if (request.getParameter("usrBDay").equals("") == false && request.getParameter("usrBDay").equals(utente.getDataNascita()) == false) {
                        utente.setDataNascita((String)request.getParameter("usrBDay"));
                        request.setAttribute("userDetailsUpdated", true);
                    }
                    if (request.getParameter("usrImgURL").equals("") == false && request.getParameter("usrImgURL").equals(utente.getUrlFotoProfilo()) == false) {
                        utente.setUrlFotoProfilo((String)request.getParameter("usrImgURL"));
                        request.setAttribute("userDetailsUpdated", true);
                    }
                    if (request.getParameter("usrPresentation").equals("") == false && request.getParameter("usrPresentation").equals(utente.getCitazione()) == false) {
                        utente.setCitazione((String)request.getParameter("usrPresentation"));
                        request.setAttribute("userDetailsUpdated", true);
                    }
                    
                    String passwd = request.getParameter("usrPass");
                    String passwdConfirm = request.getParameter("usrPassConfirm");
                    
                    if (request.getParameter("usrPass").equals("") == false) {
                        if (passwd.equals(passwdConfirm) == true) {
                            utente.setPassword(passwd);
                            request.setAttribute("userDetailsUpdated", true);
                            System.out.println(SERVNAME + "User details updated");
                        }
                        else {
                            request.setAttribute("passConfirmError", true);
                        }
                    }
                }
                
                // eliminazione di un account
                if (request.getParameter("selfDestruction") != null) {
                    
                    int userId = utente.getId();
                    
                    System.out.println(SERVNAME + "User with id: " + userId + " request to remove");
                    
                    // se la password e' giusta
                    if (utente.getPassword().equals((String)request.getParameter("selfDestructionPass")) == true) {
                        // tenta di cancellare l'utente. Se viene lanciata qualche eccezione verra' intercettata
                        try {
                            // log
                            System.out.println(SERVNAME + "Deleting user with id: " + userId + "...");
                            
                            if (UtenteFactory.getInstance().removeUserCompletely(utente.getId()) == true) {
                                // log
                                System.out.println(SERVNAME + "User with id: " + userId + " and his posts sucesfully removed");
                                // redirect alla pagina di addio
                                response.sendRedirect("goodbye.jsp");
                                
                                return;
                            }
                            
                        } catch (SQLException ex) {
                            // log
                            System.err.println(SERVNAME + "ERROR while deleting user with id: " + userId + " reloading profile page");
                            ex.printStackTrace();
                            
                            request.setAttribute("removeErr", true);
                            
                            return;
                        }
                    } else {
                        request.setAttribute("removeUserPassErr", true);
                        request.getRequestDispatcher("profilo.jsp").forward(request, response);
                        return;
                    }
                }
                
                request.getRequestDispatcher("profilo.jsp").forward(request, response);
                
            }
            else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            request.setAttribute("loggedIn", false);
            request.setAttribute("illegalAccess", true);
            request.getRequestDispatcher("Login?logout=true").forward(request, response);
            return;
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









