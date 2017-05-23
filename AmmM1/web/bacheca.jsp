<%-- 
    Document   : bacheca
    Created on : 11-mag-2017, 15.47.42
    Author     : archer
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <!-- postmaker -->
                        <jsp:include page="newPost.jsp"/>
                    </div>
                    <div id="postsDiv">
                        <c:forEach var="post" items="${posts}">
                            <div class="post">
                                <h4 id='img'>
                                    <a id="userImg" href="${post.autore.urlFotoProfilo}">
                                        <img src="${post.autore.urlFotoProfilo}"
                                             title="Foto profilo di ${post.autore.nome}"
                                             alt="Foto profilo di ${post.autore.nome}"
                                             height="50"
                                             class="accountImage"/></a>
                                             <a id="userName" href="Bacheca?userIdToVisit=${post.autore.id}">${post.autore.nome}</a>
                                             <!-- se nel post sono taggate persone o gruppi -->
                                             <c:if test="${post.toGroup != null && post.toUser == null}">
                                                 <a class="addOn"> >>></a> <a class="toWho" href="Bacheca?groupIdToVisit=${post.toGroup.id}">${post.toGroup.nome}</a> <a class="addOn">(Gruppo)</a>
                                             </c:if>
                                             <c:if test="${post.toGroup == null && post.toUser != null}">
                                                 <a class="addOn"> >>></a> <a class="toWho" href="Bacheca?userIdToVisit=${post.toUser.id}">${post.toUser.nome}</a> <a class="addOn">(Utente)</a>
                                             </c:if>
                                </h4>
                                <c:if test="${post.postType == 'TEXT' || empty post.urlAllegato}">
                                    <p>${post.contenuto}</p>
                                </c:if>
                                <c:if test="${post.postType == 'TEXT_AND_IMAGE' && not empty post.urlAllegato}">
                                    <p>
                                        ${post.contenuto}
                                        <a href="${post.urlAllegato}">
                                            <img src="${post.urlAllegato}"
                                                 title="${post.nomeAllegato}"
                                                 alt="Immagine postata da ${post.autore.nome}"
                                                 height="200"/></a>
                                    </p>
                                </c:if>
                                <c:if test="${post.postType == 'TEXT_AND_LINK' && not empty post.urlAllegato}">
                                    <p>
                                        ${post.contenuto} 
                                        <a href="${post.urlAllegato}">${post.nomeAllegato}</a>
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
