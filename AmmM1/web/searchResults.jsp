<%-- 
    Document   : searchResults
    Created on : 20-lug-2017, 6.00.11
    Author     : archer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<json:array>
    <%-- elenco utenti --%>
    <c:forEach var="user" items="${userList}">
        <json:object>
            <json:property name="id" value="${user.id}"/>
            <json:property name="nome" value="${user.nome}"/>
            <json:property name="cognome" value="${user.cognome}"/>
            <json:property name="urlFotoProfilo" value="${user.urlFotoProfilo}"/>
        </json:object>
    </c:forEach>
    
    <%-- elenco gruppi --%>
    <c:forEach var="group" items="${groupList}">
        <json:object>
            <json:property name="id" value="${group.id}"/>
            <json:property name="nome" value="${group.nome}"/>
            <json:property name="urlImmagine" value="${group.urlImmagine}"/>
        </json:object>
    </c:forEach>
</json:array>