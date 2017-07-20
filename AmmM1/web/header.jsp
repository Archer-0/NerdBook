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
                <c:if test="${userIdToVisit != null && groupIdToVisit == null}">
                    <c:if test="${page == 'bacheca'}">
                        <li class="active"><a href="Bacheca?userIdToVisit=${userIdToVisit}">Bacheca</a></li>
                        <li><a href="Profilo?userIdToVisit=${userIdToVisit}">Profilo</a></li>
                    </c:if>
                    <c:if test="${page == 'profilo'}"> 
                        <li class="active"><a href="Profilo?userIdToVisit=${userIdToVisit}">Profilo</a></li>
                        <li><a href="Bacheca?userIdToVisit=${userIdToVisit}">Bacheca</a></li>
                    </c:if>
                </c:if>
                <c:if test="${groupIdToVisit != null && userIdToVisit == null}">
                    <c:if test="${page == 'bacheca'}">
                        <li class="active"><a href="Bacheca?groupIdToVisit=${groupIdToVisit}">Bacheca</a></li>
                        <li><a href="Profilo?groupIdToVisit=${groupIdToVisit}">Profilo</a></li>
                    </c:if>
                    <c:if test="${page == 'profilo'}"> 
                        <li class="active"><a href="Profilo?groupIdToVisit=${groupIdToVisit}">Profilo</a></li>
                        <li><a href="Bacheca?groupIdToVisit=${groupIdToVisit}">Bacheca</a></li>
                    </c:if>
                </c:if>
            </ul>
        </nav>
        <ul id="logout">
            <li><a href="Profilo?userIdToVisit=${loggedUser.id}">
                    <img src="${loggedUser.urlFotoProfilo}" alt="La tua foto profilo" title="Vai al tuo profilo" width="40" height="40"/></a></li>
                    <li id="userName"><a href="Bacheca?userIdToVisit=${loggedUser.id}" title="Vai alla tua bacheca">${loggedUser.nome}</a></li>
                    <li id="logoutButton"><a href="Login?logout=true" title="Disconnettiti da NerdBook">Logout</a></li>
        </ul>
    </c:if>
    <c:if test="${page == 'descrizione'}">
        <h5>Non c'&egrave; nulla da vedere qui!</h5>
    </c:if>
</header>
