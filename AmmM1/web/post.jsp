<%-- 
    Document   : post
    Created on : 11-mag-2017, 18.03.27
    Author     : archer
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- struttura di un post --%>

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
                <a class="addOn"> >>></a> <a class="toWho" href="Bacheca?groupIdToVisit=${post.toGroup.id}">${post.toGroup.nome}</a><sup><img src="img/256x256_Groups.png" height="24px" width="24px" class="iconAddOn"/></sup>
            </c:if>
            <c:if test="${post.toGroup == null && post.toUser != null}">
                <a class="addOn"> >>></a> <a class="toWho" href="Bacheca?userIdToVisit=${post.toUser.id}">${post.toUser.nome}</a><sup><img src="img/256x256_People.png" height="24px" width="24px" class="iconAddOn"/></sup>
            </c:if>
        </h4>
        <%-- il form per la cancellazione e' visibile solo ai propietari di post o all'utente root --%>
        <c:if test="${post.autore.id == loggedUser.id || loggedUser.id == 1}">
            <form id="deletePost" action="DeletePost?deletePost=true">
                <c:if test="${userIdToVisit != null}">
                    <input type="hidden" hidden readonly name="userIdToVisit" value="${userIdToVisit}"/>
                </c:if>
                <c:if test="${groupIdToVisit != null}">
                    <input type="hidden" hidden readonly name="groupIdToVisit" value="${groupIdToVisit}"/>
                </c:if>
                <input type="hidden" hidden readonly name="postId" value="${post.id}"/>
                <input type="hidden" hidden readonly name="authorId" value="${post.autore.id}"/>
                <button id="deletePostButton" class="redButton" name="deletePost" value="true">Elimina questo post</button>
            </form>
        </c:if>
        
        <c:if test="${post.tipo == 'TEXT'}">
            <p>${post.contenuto}</p>
        </c:if>
        <c:if test="${post.tipo == 'TEXT_AND_IMAGE'}">
            <p>
                ${post.contenuto}
            </p>
            <div id="image">
                <a href="${post.urlAllegato}">
                    <img src="${post.urlAllegato}"
                         title="${post.nomeAllegato}"
                         alt="Immagine postata da ${post.autore.nome}"
                         height="200"/></a>
            </div>
        </c:if>
        <c:if test="${post.tipo == 'TEXT_AND_LINK'}">
            <p>
                ${post.contenuto} 
                <a href="${post.urlAllegato}">${post.nomeAllegato}</a>
            </p>
        </c:if>
    </div>
</c:forEach>