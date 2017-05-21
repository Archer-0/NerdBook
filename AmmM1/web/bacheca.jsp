<%-- 
    Document   : bacheca
    Created on : 11-mag-2017, 15.47.42
    Author     : archer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>NerdBook: Bacheca di ${loggedUser.nome} ${loggedUser.cognome}</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author"
              content="Mattia Frongia">
        <meta name="keywords"
              content="bacheca NerdBook">
        <link rel="stylesheet"
              type="text/css"
              href="style.css"
              media="screen">
    </head>
    <body>
        <!-- header -->
        <c:set var="page" value="bacheca" scope="request"/>
        <jsp:include page="header.jsp"/>
        
        <!-- contenuto della pagina -->
        <div id="divBody">
            <div id="bacheca">
                <jsp:include page="sideBarSx.jsp"/>
                <div id="content">
                    <div id="writeStatus">
                        <!-- da aggiungere -->
                    </div>
                    <div id="postsDiv">
                        <c:forEach var="post" items="${posts}">
                            <div class="post">
                                <h4 id='img'>
                                    <a href="${post.autore.urlFotoProfilo}">
                                        <img src="${post.autore.urlFotoProfilo}"
                                             title="Foto profilo di ${post.autore.nome}"
                                             alt="Foto profilo di ${post.autore.nome}"
                                             height="50"
                                             class="accountImage"/>
                                    </a>
                                             <a href="bacheca.jsp?userIdToVisit=${post.autore.id}">${post.autore.nome}</a>
                                             <c:if test="${not empty post.toGroup && post.toUser == null}"> >>>
                                                 <a href='bacheca.jsp?groupIdToVisit=${post.toGroup.id}'>${post.toGroup.nome}</a> (Gruppo)
                                             </c:if>
                                             <c:if test="${post.toGroup == null && not empty post.toUser}"> >>> 
                                                 <a href='bacheca.jsp?userIdToVisit=${post.toUser.id}'>${post.toUser.nome}</a> (Utente)
                                             </c:if>
                                </h4>
                                <c:if test="${post.postType == 'TEXT'}">
                                    <p>${post.contenuto}</p>
                                </c:if>
                                <c:if test="${post.postType == 'TEXT_AND_IMAGE'}">
                                    <p>
                                        ${post.contenuto}
                                        <a href="${post.urlAllegato}">
                                            <img src="${post.urlAllegato}"
                                                 title="Immagine postata da ${post.autore.nome}"
                                                 alt="Immagine postata da ${post.autore.nome}"
                                                 height="200"/>
                                        </a>
                                    </p>
                                </c:if>
                                <c:if test="${post.postType == 'TEXT_AND_LINK'}">
                                    <p>
                                        ${post.contenuto} 
                                        <a href="${post.urlAllegato}">${post.urlAllegato}</a>
                                    </p>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div id="sideBarDx">
                    <!-- Da aggiungere -->
                </div>
            </div>
        </div>    
    </body>
</html>
