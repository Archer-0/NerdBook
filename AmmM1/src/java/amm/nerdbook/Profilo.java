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
        
        // inizializzazione attributo utenti per essere visualizzati nella sidebar
        ArrayList<Utente> utenti = UtenteFactory.getInstance().getListaUtenti();
        request.setAttribute("utenti", utenti);
        
        ArrayList<Gruppo> gruppi = GruppoFactory.getInstance().getListaGruppi();
        request.setAttribute("gruppi", gruppi);
        
        // se la sessione esiste gia' e l'utente e' loggato
        if (session != null && session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true)) {
            // 
            String user = request.getParameter("user");
            int loggedUserId;
            
            if (user != null) {
                loggedUserId = (int)Integer.parseInt(user);
            }
            else {
                loggedUserId = (int)session.getAttribute("loggedUserId");
            }
            
            Utente loggedUser = UtenteFactory.getInstance().getUtenteById(loggedUserId);
            
            if (loggedUser != null) {
                if (request.getParameter("userDetailsUpdated") == null) {
                    request.setAttribute("loggedUser", loggedUser);
                }
                else {
                    // finche' le password non sono uguali ripeto la richiesta dei parametri
                    while(request.getParameter("passConfirmError").equals("true")) {
                        //request.setAttribute("updatedUsrName", request.getParameter("usrName"));
                        //request.setAttribute("updatedUsrSurname", request.getParameter("usrSurname"));
                        //request.setAttribute("updatedUsrImgURL", request.getParameter("usrImgURL"));
                        //request.setAttribute("updatedUsrBDay", request.getParameter("usrBDay"));
                        //request.setAttribute("updatedUsrPresentation", request.getParameter("usrPresentation")); 
                        //request.setAttribute("updatedUsrPass", request.getParameter("usrPass"));
                        //request.setAttribute("updatedUsrPassConfirm", request.getParameter("usrPassConfirm"));

                        String password = request.getParameter("usrPass");
                        String passwordConfirm = request.getParameter("usrPassConfirm");
                        
                        // se le password coincidono vado a vanti
                        if (password.equals(passwordConfirm) == true) {
                            request.setAttribute("userDetailsUpdated", true);
                            request.setAttribute("passConfirmError", false);
                        }
                        // se non coincidono stampo un avviso e ricarico la pagina
                        else {
                            request.setAttribute("userDetailsUpdated", false);
                            request.setAttribute("passConfirmError", true);
                            request.getRequestDispatcher("profilo.jsp").forward(request, response);
                        }
                    }
                    if (request.getParameter("usrName").equals("") == false) {
                        loggedUser.setNome(request.getParameter("ursName"));
                    }
                    if (request.getParameter("usrSurname").equals("") == false) {
                        loggedUser.setCognome(request.getParameter("usrSurname"));
                    }
                    if (request.getParameter("usrBDay").equals("") == false) {
                        loggedUser.setDataNascita(request.getParameter("usrBDay"));
                    }
                    if (request.getParameter("usrImgURL").equals("") == false) {
                        loggedUser.setUrlFotoProfilo(request.getParameter("usrImgURL"));
                    }
                    if (request.getParameter("usrPresentation").equals("") == false) {
                        loggedUser.setCitazione(request.getParameter("usrPresentation"));
                    }
                    if (request.getParameter("usrPass").equals("") == false) {
                        loggedUser.setPassword(request.getParameter("usrPass"));
                    }
                }
                request.getRequestDispatcher("Login").forward(request, response);
            }
            else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            request.setAttribute("loggedIn", false);
            request.setAttribute("illegalAccess", true);
            request.getRequestDispatcher("Login").forward(request, response);
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
