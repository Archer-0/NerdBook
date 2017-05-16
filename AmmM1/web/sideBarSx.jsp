<%-- 
    Document   : searchbar
    Created on : 11-mag-2017, 16.44.32
    Author     : archer
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="sideBarSx">
    <%--
    <div id="searchBar">
        <form action="#" method="get">
            <input type="text"
                   placeholder="Cerca..."
                   title="Cerca su NerdBook"
                   name="searchBox"
                   id="searchBox"/>
        </form>
    </div>
    --%>
    <nav id="people">
        <h3>
            Potresti conoscere: 
        </h3>
        <ul>
            <c:forEach var="utente" items="${utenti}">
                <c:if test="${utente.id != '-1' && utente.id != loggedUserId}">
                    <li><a href="Bacheca?user=${utente.id}">
                            <img src="${utente.urlFotoProfilo}" alt="Foto profilo di ${utente.nome} " title="Foto profilo di ${utente.nome}"/> <h5>${utente.nome}</h5>
                        </a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </nav>
    <nav id="groups">
        <h3>
            Potrebbero interessarti: 
        </h3>
        <ul>
            <c:forEach var="gruppo" items="${gruppi}">
            <li><a href="Bacheca?gruppo=${gruppo.id}">
                    <img src="${gruppo.urlImmagine}" alt="Immagine di ${gruppo.nome}" title="Immagine di ${gruppo.nome}"/> <h5>${gruppo.nome}</h5>
                </a>
            </li>
            </c:forEach>
        </ul>
    </nav>
</div>