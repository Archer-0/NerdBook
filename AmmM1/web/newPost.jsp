<%-- 
    Document   : newPost
    Created on : 21-mag-2017, 15.27.33
    Author     : archer
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h4>
    <c:if test="${newPostRequest == null}">
        <c:if test="${userToVisit.id != loggedUserId}">
            Scrivi sulla bacheca di <strong>${userToVisit.nome}</strong>
        </c:if>
        <c:if test="${userToVisit.id == loggedUserId}">
            Scrivi un nuovo post
        </c:if>
    </c:if>
    <c:if test="${newPostRequest == true}">
        <c:if test="${userToVisit.id != loggedUserId}">
            Riepilogo post per <strong>${userToVisit.nome}</strong>
        </c:if>
        <c:if test="${userToVisit.id == loggedUserId}">
            Riepilogo post
        </c:if>
    </c:if>
</h4>
    
    <form action="Bacheca?newPostRequest=true" method="get" name="newPost">
        <c:if test="${newPostRequest == null}">
            <textarea rows="4" cols="22"  
                      title="Scrivi un nuovo post" 
                      placeholder="So già a cosa stai pensando, ma scrivilo comunque..."
                      name="newPostContent" 
                id="newPostContent"></textarea>
                
            <div id="radiusInput">
                <p>Che tipo di allegato vuoi aggiungere?</p>
                <input type="radio" id="radio1" name="allegatoType" value="not" checked="checked"><label for="radio1"> Nessun allegato</label>
                <input type="radio" id="radio2" name="allegatoType" value="img"><label for="radio2"> Immagine</label>
                <input type="radio" id="radio3" name="allegatoType" value="link"><label for="radio3"> Link</label>
            </div>
            <input type="url" id="urlAllegato" name="urlAllegato" placeholder="URL allegato">
            <input type="text" id="nomeAllegato" name="nomeAllegato" title="Dai un nome al tuo allegato" placeholder="Titolo allegato">
            <c:if test="${userToVisit != null && groupToVIsit == null}">
                <button class="fullSizeButton" type="submit" name="userIdToVisit" value="${userToVisit.id}">Riepilogo e invia</button>
            </c:if>
            <c:if test="${userToVisit == null && groupToVIsit != null}">
                <button class="fullSizeButton" type="submit" name="groupIdToVisit" value="${groupToVisit.id}">Riepilogo e invia</button>
            </c:if>
        </c:if>

        <!-- revisione del post -->
        <c:if test="${newPostRequest == true}">
            <textarea rows="4" cols="22"  
                      title="Conferma il post" 
                      placeholder="${newPostContent}"
                      disabled=""
                      name="newPostRevision" 
                      id="newPostRevision"></textarea>
            
            <c:if test="${allegatoType eq 'not'}">
                <p>Nessun allegato</p>
            </c:if>
            <c:if test="${allegatoType eq 'img'}">
                <img alt="${nomeAllegato}: ${urlAllegato}" src="${urlAllegato}" title="${nomeAllegato}"/>
            </c:if>
            <c:if test="${allegatoType eq 'link'}">
                <p><a href="${urlAllegato}" title="${nomeAllegato}">${nomeAllegato}</a></p>
            </c:if>
                
            <button type="submit" name="newPostApproved" value="true">Invia</button>
            <button type="reset" name="newPostApproved" value="false">Annulla</button>
        </c:if>
        
    </form>

<c:if test="${newPostRequest == true}">
    
</c:if>