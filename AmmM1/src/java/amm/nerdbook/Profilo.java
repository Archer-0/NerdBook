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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author archer
 */
public class Profilo extends HttpServlet {

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
        
//        ArrayList<Utente> users = UtenteFactory.getInstance().getListaUtenti();
//        request.setAttribute("users", users);
//        
//        ArrayList<Gruppo> groups = GruppoFactory.getInstance().getListaGruppi();
//        request.setAttribute("groups", groups);
        
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
                        }
                        else {
                            request.setAttribute("passConfirmError", true);
                        }
                    }
                }
                else {
                    request.getRequestDispatcher("profilo.jsp?userDetailsUpdated=false").forward(request, response);
                    return;
                }
//                request.setAttribute("loggedUser", utente);
                request.getRequestDispatcher("profilo.jsp").forward(request, response);
                
            }
            else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            request.setAttribute("loggedIn", false);
            request.setAttribute("illegalAccess", true);
            request.getRequestDispatcher("Login").forward(request, response);
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









