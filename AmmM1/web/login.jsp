<%-- 
    Document   : login
    Created on : 11-mag-2017, 20.34.57
    Author     : archer
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>NerdBook: Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author"
              content="Mattia Frongia">
        <meta name="keywords"
              content="login NerdBook">
        <link rel="stylesheet" 
              type="text/css" 
              href="style.css"
              media="screen">
    </head>
    <body>
        <div id="title">
                <h1><img src="img/logo_sfondo_verde_108x88.jpg" alt="Logo NerdBook" width="54" height="44" title="Logo NerdBook"/> NerdBook</h1>
                <h4>Accedi al tuo account NerdBook.</h4>
        </div>
        
        <div id="divBody">
            <c:if test="${invalidAccountData==true}">
                <div id="invalidAccountData">
                    Username e/o password non sono corretti
                </div>
            </c:if>
            <c:if test="${IllegalAccess==true}">
                <div id="illegalAccess">
                    Devi prima effettuare il login
                </div>
            </c:if>
            <div id="login">
                <!-- form per inserimento dati -->
                <form name="user_data" action="profilo.html" method="post">
                    <!-- spazio di inserimento username -->
                    <input name="user" placeholder="Nome utente o e-mail" id="user" type="text" size="30" maxlength="30" title="Username"/>
                    <!-- spazio inserimento password -->
                    <input name="pass" placeholder="Password" id="pass" type="password" size="30" maxlength="20" title="Password"/>
                    <!-- Tasto per invio dati -->
                    <input type="submit" value="Login"/>
                </form>
                <p>
                    Non conosci NerdBook?
                </p>
                <a href="descrizione.jsp">Clicca Qu&igrave;.</a>
            </div>
        </div>
    </body>
</html>