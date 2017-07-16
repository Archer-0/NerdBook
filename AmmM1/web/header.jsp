<%-- 
    Document   : header
    Created on : 11-mag-2017, 17.30.09
    Author     : archer
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<header>
    <h1><dfn title="Oh yeah!">NerdBook</dfn></h1>
    <c:if test="${page != 'descrizione'}">
        <nav id="siteMap">
            <ul>
                <c:if test="${page == 'bacheca'}">
                    <li class="active"><a href="Bacheca?userIdToVisit=${loggedUserId}">Bacheca</a></li>
                    <li><a href="profilo.jsp?user=${loggedUser.id}">Profilo</a></li>
                </c:if>
                <c:if test="${page == 'profilo'}"> 
                    <li class="active"><a href="profilo.jsp?user=${loggedUser.id}">Profilo</a></li>
                    <li><a href="Bacheca?userIdToVisit=${loggedUserId}">Bacheca</a></li>
                </c:if>
            </ul>
        </nav>
        <ul id="logout">
            <li><a href="profilo.jsp?user=${loggedUser.id}">
                    <img src="${loggedUser.urlFotoProfilo}" alt="La tua foto profilo" title="La tua foto profilo" width="40" height="40"/></a></li>
                    <li id="userName"><a href="profilo.jsp?user=${loggedUser.id}">${loggedUser.nome}</a></li>
            <li id="logoutButton"><a href="Login?logout=true">Logout</a></li>
        </ul>
    </c:if>
    <c:if test="${page == 'descrizione'}">
        <h5>Non c'&egrave; nulla da vedere qui!</h5>
    </c:if>
</header>
