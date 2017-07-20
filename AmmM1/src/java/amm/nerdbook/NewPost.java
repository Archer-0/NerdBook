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
import javax.servlet.RequestDispatcher;
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
    
    final String SERVNAME = "[NewPost-Servlet]-";

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
        
            // salvo l'utente loggato
            
            Utente utente = (Utente) session.getAttribute("loggedUser");
            
            System.out.println(SERVNAME + "User already logged");
            
            // variabili
            int idToWho             = -1;       // e' usato per evitare di ripetere controlli 
            Utente toUser           = null;     // id dell'utente visitato (-1 se si visita un gruppo)
            Gruppo toGroup          = null;     // id del gruppo visitato (-1 se si visita un utente)
            String contenuto        = null;     // contiene il testo del post
            Post.Type postType      = null;     // contiene il tipo di post (TEXT, TEXT_AND_IMAGE, TEXT_AND_LINK)
            String urlAllegato      = null;     // contiene il link all'allegato
            String nomeAllegato     = null;     // contiene il nome breve dell'allegato (opzionale)
            String allegatoType     = null;     // contiene il tipo di allegato (not, img, link)
            
            int userIdToVisit = 0;
            int groupIdToVisit = 0;
            
            if (request.getParameter("userIdToVisit") != null) {
                userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
                request.setAttribute("userIdToVisit", userIdToVisit);
                toUser = UtenteFactory.getInstance().getUtenteById(userIdToVisit);
            }
            
            if (request.getParameter("groupIdToVisit") != null) {
                groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
                request.setAttribute("groupIdToVisit", groupIdToVisit);
                toGroup = GruppoFactory.getInstance().getGroupById(groupIdToVisit);
            }
            
            // nuovo post
            if (request.getParameter("newPostRequest") != null) {
	 	
	 	/* Da confermare ( attributo newPostRequest == needsConfirm )
	 	 * preparazione elementi per revisione del post
	 	 */
	 	if (request.getParameter("newPostRequest").equals("needsConfirm")) {
			
                    // log
                    System.out.println(SERVNAME + "New post creation requested...");

                    // controllo sul contenuto obbligatorio del post
                    contenuto = (String) request.getParameter("newPostContent");

                    /* controllo parte testuale con attributo "required" in textarea su "newPost.jsp"*/
                    
                    // log
                    System.out.println(SERVNAME + "Post text content: \"" + contenuto + "\"");

                    /* recupero parametri allegato */
                    allegatoType = request.getParameter("allegatoType");
                    
                    // controllo contenuto allegato

                    if (!allegatoType.equals("not")) {
                   
                        urlAllegato = request.getParameter("urlAllegato");
                        nomeAllegato = request.getParameter("nomeAllegato");
                        
                        // se url dell'allegato e' vuoto si imposta il tipo di allegato a "not"
                        if (urlAllegato.isEmpty()) {
                            allegatoType = "not";
                        }
                        
                        else {                            
                            // se nome allegato non e' stato impostato viene usato l'intero link come nome
                            if (nomeAllegato.isEmpty() && urlAllegato.isEmpty() == false) {
                                nomeAllegato = urlAllegato;
                            }
                        }
                        
                    } // FINE controllo contenuto allegato
                    
                    // settare gli atributi per essere visualizzati
                    
                    request.setAttribute("contenuto", contenuto);
                    request.setAttribute("urlAllegato", urlAllegato);
                    request.setAttribute("nomeAllegato", nomeAllegato);
                    request.setAttribute("allegatoType", allegatoType);
                    
                    if (toUser != null && toUser.getId() != utente.getId()) {   
                        request.setAttribute("idToUser", UtenteFactory.getInstance().getUtenteById(toUser.getId()));
                        System.out.println(SERVNAME + "Value of \"toUser\": " + toUser + " idToUser: " + toUser.getId());
                    }
                    else {
                        request.setAttribute("idToUser", null);
                    } 
                    
                    if (toGroup != null) {
                        request.setAttribute("idToGroup", GruppoFactory.getInstance().getGroupById(toGroup.getId()));
                        System.out.println(SERVNAME + "Value of \"toGroup\": " + toGroup + " idToGroup: " + toGroup.getId());
                        
                    }
                    else
                        request.setAttribute("idToGroup", null);
                    
                    
                    
                    request.setAttribute("revision", true);
                    
                    request.getRequestDispatcher("Bacheca").forward(request, response);
                    
                    return;
	 	} // FINE preparazione alla revisione del post
                
                // post confermato
                if (request.getParameter("newPostRequest").equals("approved")) {
                    
                    // recupero delle informazioni dalla pagina di conferma e uleteriori controlli
                    
                    contenuto = (String) request.getParameter("contenuto");
                    System.out.println(SERVNAME + "Post text content (again): \"" + contenuto + "\"");
                    
                    allegatoType = (String) request.getParameter("allegatoType");
                    
                    postType = Post.Type.TEXT;
                    urlAllegato = null;
                    nomeAllegato = null;
                    
                    if (!allegatoType.equals("not")) {
                        
                        if (allegatoType.equals("img"))
                            postType = Post.Type.TEXT_AND_IMAGE;
                        
                        if (allegatoType.equals("link"))
                            postType = Post.Type.TEXT_AND_LINK;
                        
                        urlAllegato = request.getParameter("urlAllegato");
                        nomeAllegato = request.getParameter("nomeAllegato");
                        
                        if (nomeAllegato.isEmpty() && !urlAllegato.isEmpty()) {
                                
                            if (urlAllegato.length() > 20)
                                nomeAllegato = urlAllegato.substring(0, 16).concat("...");
                                
                            else
                                nomeAllegato = urlAllegato;
                        }
                    }
                    
                    int idToUser = -1;
                    int idToGroup = -1;
                    
                    if (toUser != null && toUser.getId() != utente.getId()) {
                        idToUser = toUser.getId();
                    }
                    
                    if (toGroup != null) 
                        idToGroup = toGroup.getId();
                    
                    // creazione del nuovo post e reindirizzamento dell'utente alla bacheca precedente
                    PostFactory.getInstance().newPost(utente.getId(), idToUser, idToGroup, contenuto, urlAllegato, nomeAllegato, postType);
                    
                    request.setAttribute("revision", false);
                    //request.getRequestDispatcher("Bacheca");
                    
                    if (userIdToVisit != 0)
                        response.sendRedirect("Bacheca?userIdToVisit=" + userIdToVisit);
                    else
                        response.sendRedirect("Bacheca?groupIdToVisit=" + groupIdToVisit);
                    
                    return;
                }

                else {
                    if (request.getParameter("newPostRequest").equals("refused") == true) {
                        // srimuovere tutti i parametri dalla pagina e reindirizzare l'utente alla bacheca

                        request.removeAttribute("groupToVisit");
                        request.removeAttribute("userToVisit");
                        request.removeAttribute("postType");
                        request.removeAttribute("urlAllegato");
                        request.removeAttribute("nomeAllegato");
                        
                        if (userIdToVisit != 0)
                            response.sendRedirect("Bacheca?userIdToVisit=" + userIdToVisit);
                        else
                            response.sendRedirect("Bacheca?groupIdToVisit=" + groupIdToVisit);
                        
                        System.out.println(SERVNAME + "Post refused by user.");
                        
                        return;
                    }
                }
            } // FINE opzioni post
            
            if (userIdToVisit != 0)
                response.sendRedirect("Bacheca?userIdToVisit=" + userIdToVisit);
            else
                response.sendRedirect("Bacheca?groupIdToVisit=" + groupIdToVisit);        
        }
        else {
            request.setAttribute("illegalAccess", true);          
            System.out.println(SERVNAME + "User not logged (illegal access) redirecting user to login");
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


//            String nomeAllegato = "";
//            String newPostContent = "";
//            String allegatoType = "";
//            String urlAllegato = "";
//            int groupIdToVisit = -1;
//            int userIdToVisit = -1;
//            
//            if (request.getParameter("groupIdToVisit") != null) {
//                groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
//                System.out.println("[NewPost-Servlet]-Group ID to post to: " + groupIdToVisit);
//            }
//            if (request.getParameter("userIdToVisit") != null) {
//                userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
//                System.out.println("[NewPost-Servlet]-User ID to post to: " + userIdToVisit);
//            }
//            
//            // se c'e' stata una richiesta per un nuovo post
//            if (request.getParameter("newPostRequest") != null) {
//                
//                if (request.getParameter("newPostRequest").equals("needsConfirm") == true) {
//                    // se la parte testuale del post non e' vuota
//                    System.out.println("[NewPost-Servlet]-New post creation request");
//                        
//                    // il controllo sulla parte testuale vien effettuata sull'html dall'attributo "required"
//                    
//                    newPostContent = request.getParameter("newPostContent");
//                    System.out.println("[NewPost-Servlet]-Variable newPostContent: " + newPostContent);
//                    allegatoType = request.getParameter("allegatoType");
//                    System.out.println("[NewPost-Servlet]-Variable allegatoType: " + allegatoType);
//
//                    // se l'allegato non e' vuoto
//                    if (request.getAttribute("allegatoType") != "not") {
//                        if (request.getParameter("urlAllegato").equals("") == false) {
//                            urlAllegato = request.getParameter("urlAllegato");
//                            System.out.println("[NewPost-Servlet]-Variable urlAllegato: " + urlAllegato);
//                            // se il nome dell'allegato non e' vuoto
//                            if (request.getParameter("nomeAllegato").equals("") == false) {
//                                // viene impostato il nome dell'allegato
//                                nomeAllegato = (String) request.getParameter("nomeAllegato");
//                            }
//                            // se il nome dell'allegato e' vuoto
//                            else {
//                                // viene usato come nome dell'allegato l'url completo
//                                nomeAllegato = (String) request.getParameter("urlAllegato");
//                            }
//                        }
//                        // se l'url dell'allegato e' vuoto
//                        else {
//                            request.setAttribute("allegatoType", "not");
//                        }
//                    }
//                    // se il tipo dell'allegato e' not
//                    else {
//                        request.setAttribute("allegatoType", "not");
//                    }
//                    
//                    if (userIdToVisit >= -1)
//                        request.setAttribute("userToVisit", UtenteFactory.getInstance().getUtenteById(userIdToVisit));
//                    
//                    if (groupIdToVisit >= -1)
//                        request.setAttribute("groupToVisit", GruppoFactory.getInstance().getGroupById(groupIdToVisit));
//                    
//                    request.setAttribute("urlAllegato", urlAllegato);
//                    request.setAttribute("nomeAllegato", nomeAllegato);
//                    request.setAttribute("newPostContent", newPostContent);
//                    request.setAttribute("revision", true);
//                    request.getRequestDispatcher("bacheca.jsp").forward(request, response);
//                }
//
//                if (request.getParameter("newPostRequest").equals("approved") == true) {
//
//                    System.out.println("[NewPost-Servlet]-Attribute postApproved: " + (String)request.getParameter("newpostRequest"));
//                    
//                    newPostContent = request.getParameter("newPostContent");
//                    allegatoType = request.getParameter("allegatoType");
//                    urlAllegato = request.getParameter("urlAllegato");
//                    nomeAllegato = request.getParameter("nomeAllegato");
//
//                    if (request.getParameter("groupIdToVisit") != null) {
//                        groupIdToVisit = Integer.parseInt(request.getParameter("groupIdToVisit"));
//                        System.out.println("[NewPost-Servlet]-Group ID to post to: " + groupIdToVisit);
//                    }
//                    if (request.getParameter("userIdToVisit") != null) {
//                        userIdToVisit = Integer.parseInt(request.getParameter("userIdToVisit"));
//                        System.out.println("[NewPost-Servlet]-User ID to post to: " + userIdToVisit);
//                    }
//                    
//                    System.out.println("[NewPost-Servlet]-Post approved by user: saving post to DB");
//                    // salvataggio del post
//                    if (allegatoType.equals("not")) {
//                        PostFactory.getInstance().newPost(utente.getId(),
//                            userIdToVisit, 
//                            groupIdToVisit, 
//                            newPostContent, 
//                            urlAllegato,
//                            nomeAllegato,
//                            Post.Type.TEXT);
//                        System.out.println("[NewPost-Servlet]-Post type: ONLY TEXT");
//                    }
//                    else{
//                        if (allegatoType.equals("img")) {
//                            PostFactory.getInstance().newPost(utente.getId(), 
//                                userIdToVisit, 
//                                groupIdToVisit, 
//                                newPostContent, 
//                                urlAllegato,
//                                nomeAllegato,
//                                Post.Type.TEXT_AND_IMAGE);
//                            System.out.println("[NewPost-Servlet]-Post type: TEXT AND IMAGE");
//                        }
//                        else{
//                            if (allegatoType.equals("link")) {
//                                PostFactory.getInstance().newPost(utente.getId(),
//                                    userIdToVisit, 
//                                    groupIdToVisit, 
//                                    newPostContent, 
//                                    urlAllegato,
//                                    nomeAllegato,
//                                    Post.Type.TEXT_AND_LINK);
//                                System.out.println("[NewPost-Servlet]-Post type: TEXT AND LINK");
//                            }
//                        }
//                    }
//                    System.out.println("[NewPost-Servlet]-Post saved. Redirecting user to bacheca");
//                    request.setAttribute("revision", false);
//                }
//                else {
//                        System.out.println("[NewPost-Servlet]-Post refused by user. Redirecting user to bacheca");
//                        request.setAttribute("revision", false);
//                }
//            }
//                request.getRequestDispatcher("bacheca.jsp").forward(request, response);
//        }

