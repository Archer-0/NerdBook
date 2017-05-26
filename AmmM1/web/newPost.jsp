<%-- 
    Document   : newPost
    Created on : 21-mag-2017, 15.27.33
    Author     : archer
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<c:choose>
    <c:when test="${empty revision || revision == false}">
        <div id="writeStatus">
            <h4>
                <c:if test="${userToVisit != null && userToVisit.id != loggedUserId}">
                    Scrivi sulla bacheca di <strong>${userToVisit.nome}</strong>
                </c:if>
                <c:if test="${groupToVisit != null}">
                    Scrivi sulla bacheca di <strong>${groupToVisit.nome}</strong>
                </c:if>
                <c:if test="${userToVisit.id == loggedUserId}">
                    Scrivi un nuovo post
                </c:if>
            </h4>
            <c:if test="${postContentEmptyError == true}">
                <div class="error" id="postContentEmptyError">
                    <p>Il post deve contenere un testo</p>
                </div>
            </c:if>
            <form action="NewPost" method="get">
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
                <div id="allegatoFields">
                    <input type="url" id="urlAllegato" name="urlAllegato" placeholder="URL allegato">
                    <input type="text" id="nomeAllegato" name="nomeAllegato" title="Dai un nome al tuo allegato" placeholder="Titolo allegato">
                </div>
                <c:choose>
                    <c:when test="${userToVisit != null && groupToVisit == null}">
                        <input type="text" disabled hidden name="userIdToVisit" value="${userToVisit.id}"/>
                    </c:when>
                    <c:when test="${userToVisit == null && groupToVisit != null}">
                        <input type="text" disabled hidden name="groupIdToVisit" value="${groupToVisit.id}"/>
                    </c:when>
                </c:choose>

                        <button class="fullSizeButton" type="submit" name="newPostRequest" value="needsConfirm">Riepilogo e invia</button>

            </form>
        </div>
    </c:when>

        <%-- revisione del post --%>
    <c:when test="${revision == true}">
        <div id="writeStatusFullSize">
            <h4>
                <c:if test="${userToVisit != null && userToVisit.id != loggedUserId}">
                    Riepilogo post per <strong>${userToVisit.nome}</strong>
                </c:if>
                <c:if test="${groupToVisit != null}">
                    Riepilogo post per <strong>${groupToVisit.nome}</strong>
                </c:if>
                <c:if test="${userToVisit.id == loggedUserId}">
                    Riepilogo post personale
                </c:if>
            </h4>
            <form action="NewPost" method="get">
                <textarea rows="4" cols="22"  
                          title="Conferma il post" 
                          name="newPostContent" 
                          disabled=""
                          id="newPostContent">${newPostContent}</textarea>

                <c:if test="${allegatoType eq 'not'}">
                    <p>Nessun allegato</p>
                </c:if>
                <c:if test="${allegatoType eq 'img'}">
                    <img alt="${nomeAllegato}: ${urlAllegato}" src="${urlAllegato}" title="${nomeAllegato}"/>
                </c:if>
                <c:if test="${allegatoType eq 'link'}">
                    <p><a href="${urlAllegato}" title="${nomeAllegato}">${nomeAllegato}</a></p>
                </c:if>
                <c:if test="${userToVisit != null && groupToVisit == null}">
                    <input type="text" hidden name="userIdToVisit" value="${userToVisit.id}">
                </c:if>
                <c:if test="${userToVisit == null && groupToVisit != null}">
                    <input type="text" hidden name="groupIdToVisit" value="${groupToVisit.id}">
                </c:if>
                    
                    <input type="text" hidden name="allegatoType" value="${allegatoType}"/>
                    
                <button type="submit" name="newPostRequest" value="approved">Invia</button>
                <button type="submit" name="newPostRequest" value="refused">Annulla</button>

            </form>
        </div>
    </c:when>
    <c:otherwise>
        <%-- nothing --%>
    </c:otherwise>
</c:choose>