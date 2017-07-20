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
            
            <form <c:if test="${userIdToVisit != null}">
                      action="NewPost?userIdToVisit=${userToVisit.id}" 
                  </c:if>
                  <c:if test="${groupIdToVisit != null}">
                      action="NewPost?groupIdToVisit=${groupToVisit.id}"
                  </c:if>
                method="post">    
                <textarea rows="4" cols="22"
                          title="Scrivi un nuovo post"
                          placeholder="So già a cosa stai pensando, ma scrivilo comunque..."
                          name="newPostContent"
                          required
                    id="newPostContent"></textarea>

                <div id="radiusInput">
                    <p>Che tipo di allegato vuoi aggiungere?</p>
                    <ul>
                        <li>
                            <input type="radio" id="radio1" name="allegatoType" value="not" checked="checked"><label for="radio1"> Nessun allegato</label>
                        </li>
                        <li>
                            <input type="radio" id="radio2" name="allegatoType" value="img"><label for="radio2"> Immagine</label>
                        </li>
                        <li>
                            <input type="radio" id="radio3" name="allegatoType" value="link"><label for="radio3"> Link</label>
                        </li>
                    </ul>
                </div>
                <div id="allegatoFields">
                    <input type="url" id="urlAllegato" name="urlAllegato" placeholder="URL allegato">
                    <input type="text" id="nomeAllegato" name="nomeAllegato" title="Dai un nome al tuo allegato" placeholder="Titolo allegato">
                </div>
                <c:choose>
                    <c:when test="${userToVisit != null && groupToVisit == null}">
                        <input class="hidden" type="hidden" readonly hidden name="userIdToVisit" value="${userToVisit.id}"/>
                    </c:when>
                    <c:when test="${userToVisit == null && groupToVisit != null}">
                        <input class="hidden" type="hidden" readonly hidden name="groupIdToVisit" value="${groupToVisit.id}"/>
                    </c:when>
                </c:choose>

                <button class="fullSizeButton" type="submit" name="newPostRequest" value="needsConfirm">Riepilogo e invia</button>

            </form>
        </div>
    </c:when>

    <%-- revisione del post: stesso modello dei post normali con aggiunta dei pulsanti --%>
    <c:when test="${revision == true}">
        
        <div class="post" id="revisionPost">
            <h4 id='img'>
                <a id="userImg" href="${loggedUser.urlFotoProfilo}">
                    <img src="${loggedUser.urlFotoProfilo}"
                         title="Foto profilo di ${loggedUser.nome}"
                         alt="Foto profilo di ${loggedUser.nome}"
                         height="50"
                         class="accountImage"/></a>
                         
                <a id="userName" href="Bacheca?userIdToVisit=${loggedUser.id}">${loggedUser.nome}</a>
                
                <!-- se nel post sono taggate persone o gruppi -->

                   <c:if test="${groupToVisit == null && userToVisit != null && userToVisit.id != loggedUserId}">
                       <a class="addOn"> >>></a> <a class="toWho" href="Bacheca?userIdToVisit=${userToVisit.id}" title="Utente">${userToVisit.nome}</a><sup><img src="img/256x256_People.png" height="24px" width="24px" class="iconAddOn"/></sup>
                   </c:if>
                   <c:if test="${groupToVIsit != null && userToVisit == null}">
                       <a class="addOn"> >>></a> <a class="toWho" href="Bacheca?groupIdToVisit=${groupToVisit.id}" title="Gruppo">${groupToVisit.nome}</a><sup><img src="img/256x256_Groups.png" height="24px" width="24px" class="iconAddOn"/></sup>
                   </c:if>

                
            </h4>
            <c:if test="${allegatoType == 'not'}">
                <p>${contenuto}</p>
            </c:if>
            <c:if test="${allegatoType == 'img'}">
                <p>
                    ${contenuto}
                    <a href="${urlAllegato}">
                        <img src="${urlAllegato}"
                             title="${nomeAllegato}"
                             alt="Immagine postata da ${autore.nome}"
                             height="200"/></a>
                </p>
            </c:if>
            <c:if test="${allegatoType == 'link'}">
                <p>
                    ${contenuto} 
                    <a href="${urlAllegato}">${nomeAllegato}</a>
                </p>
            </c:if>

            <div id="postSaveDecision">
                <strong>Confermare l'attuale post?</strong>

                <form <c:if test="${userIdToVisit != null}">
                      action="NewPost?userIdToVisit=${userIdToVisit}" 
                  </c:if>
                  <c:if test="${groupIdToVisit != null}">
                      action="NewPost?groupIdToVisit=${groupIdToVisit}"
                  </c:if> method="post">

                    <input type="hidden" hidden readonly name="contenuto" value="${contenuto}"/>
                    <input type="hidden" hidden readonly name="urlAllegato" value="${urlAllegato}"/>
                    <input type="hidden" hidden readonly name="nomeAllegato" value="${nomeAllegato}"/>
                    <input type="hidden" hidden readonly name="allegatoType" value="${allegatoType}"/>
                    

                    <button class="redButton" id="refuse" class="middleSizeButton" type="submit" name="newPostRequest" value="refused">Annulla</button>
                    <button id="approve" class="middleSizeButton" type="submit" name="newPostRequest" value="approved">Conferma</button>
                </form>  
            </div>
        </div>

        
        
        
        
        <%-- anteprima del post con lo stile finale 
        <jsp:include page="post.jsp">
            <jsp:param name="revision" value="true"/>
        </jsp:include>
        --%>
    <%--
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
                          readonly
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
    --%>
    </c:when>
    <c:otherwise>
        <%-- nothing --%>
    </c:otherwise>
</c:choose>