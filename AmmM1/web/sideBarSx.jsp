<%-- 
    Document   : searchbar
    Created on : 11-mag-2017, 16.44.32
    Author     : archer
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script language="JavaScript" type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script src="js/javascript.js"></script>

<div id="sideBarSx">
    <div id="searchBar">
        <input type="text"
               onkeyup="updateResults()"
               placeholder="Cerca su NerdBook"
               title="Digita per cercare su NerdBook"
               name="searchBox"
               id="searchBox"/>
        <button id="comeOn" style="margin-top: 7px;">Cerca</button>
    </div>
    <div id="resultList">
        
        <h3>
            Potresti conoscere: 
        </h3>
        <nav id="people">
            <ul>
                <c:forEach var="utente" items="${users}">
                    <c:if test="${utente.id > 1 && utente.id != loggedUserId}">
                        <li><a href="Bacheca?userIdToVisit=${utente.id}">
                                <img src="${utente.urlFotoProfilo}" alt="Foto profilo di ${utente.nome} " title="Foto profilo di ${utente.nome}"/>
                            </a>
                            <a href="Bacheca?userIdToVisit=${utente.id}">
                                <h5>${utente.nome}</h5>
                            </a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </nav>
        <h3>
            Potrebbero interessarti: 
        </h3>
        <nav id="groups">
            <ul>
                <c:forEach var="gruppo" items="${groups}">
                    <c:if test="${gruppo.id > 0}">   
                        <li><a href="Bacheca?groupIdToVisit=${gruppo.id}">
                                <img src="${gruppo.urlImmagine}" alt="Immagine di ${gruppo.nome}" title="Immagine di ${gruppo.nome}"/> 
                            </a>
                            <a href="Bacheca?groupIdToVisit=${gruppo.id}">
                                <h5>${gruppo.nome}</h5>
                            </a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>