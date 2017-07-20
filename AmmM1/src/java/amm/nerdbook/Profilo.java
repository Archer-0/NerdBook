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
        
        System.out.println(SERVNAME + "Checking session...");
        
        if (session != null && session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true)) {
            Utente utente = (Utente)session.getAttribute("loggedUser");
            boolean anyChange = false;
            int userIdToVisit = 0;
            int groupIdToVisit = 0;
            
            if (request.getParameter("userIdToVisit") != null) {
                userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
                request.setAttribute("userIdToVisit", userIdToVisit);
            }
            
            if (request.getParameter("groupIdToVisit") != null) {
                groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
                request.setAttribute("groupIdToVisit", groupIdToVisit);
            }
                
                
            
            if (utente != null) {
                
                System.out.println(SERVNAME + "User already logged");
                
                request.setAttribute("loggedUser", utente);
                
                if (userIdToVisit != 0 && utente.getId() == userIdToVisit) {
                    
                    // aggiorna dati utente
                    if (request.getParameter("userDetailsUpdated") != null) {

                        if (request.getParameter("usrName").equals("") == false && !request.getParameter("usrName").equals(utente.getNome())) {
                            utente.setNome((String)request.getParameter("usrName"));
                            anyChange = true;
                        }
                        if (request.getParameter("usrSurname").equals("") == false && !request.getParameter("usrSurname").equals(utente.getCognome())) {
                            utente.setCognome((String)request.getParameter("usrSurname"));
                            anyChange = true;
                        }
                        if (request.getParameter("usrBDay").equals("") == false && !request.getParameter("usrBDay").equals(utente.getDataNascita().toString())) {
                            utente.setDataNascita(java.sql.Date.valueOf(request.getParameter("usrBDay")));
                            anyChange = true;
                        }
                        if (request.getParameter("usrImgURL").equals("") == false && !request.getParameter("usrImgURL").equals(utente.getUrlFotoProfilo())) {
                            utente.setUrlFotoProfilo((String)request.getParameter("usrImgURL"));
                            anyChange = true;
                        }
                        if (request.getParameter("usrPresentation").equals("") == false && !request.getParameter("usrPresentation").equals(utente.getCitazione())) {
                            utente.setCitazione((String)request.getParameter("usrPresentation"));
                            anyChange = true;
                        }

                        String passwd = request.getParameter("usrPass");
                        String passwdConfirm = request.getParameter("usrPassConfirm");

                        if (!request.getParameter("usrPass").equals("")) {
                            if (passwd.equals(passwdConfirm) == true) {
                                utente.setPassword(passwd);
                                anyChange = true;
                            }
                            else {
                                request.setAttribute("passConfirmError", true);
                            }
                        }

                        if (anyChange) {

                            System.out.println(SERVNAME + "Updating user (id: " + utente.getId() + ") details...");

                            try {
                                if(UtenteFactory.getInstance().updateUserInformations(utente)) {
                                    request.setAttribute("userDetailsUpdated", true);
                                    System.out.println(SERVNAME + "User (id: " + utente.getId() + ") details updated");
                                }
                            } catch (SQLException ex) {
                                System.err.println(SERVNAME + "ERROR while updating user (id: " + utente.getId() + ") details");
                                ex.printStackTrace();
                            }
                        }

                        request.getRequestDispatcher("profilo.jsp").forward(request, response);
                    }

                    // eliminazione di un account
                    if (request.getParameter("selfDestructionRequested") != null) {

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
                    if (userIdToVisit != 0) {
                        request.setAttribute("visitedUser", UtenteFactory.getInstance().getUtenteById(userIdToVisit));
                    
                    } else if (groupIdToVisit != 0) {
                        request.setAttribute("visitedGroup", GruppoFactory.getInstance().getGroupById(groupIdToVisit));
                    }
                    
                    request.getRequestDispatcher("profilo.jsp").forward(request, response);
                }
            }
            else {
                System.err.println(SERVNAME + "ERROR user is null");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            System.out.println(SERVNAME + "User not logged: illegal access");
            request.setAttribute("loggedIn", false);
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
        return "Short description";
    }// </editor-fold>
}









