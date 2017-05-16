<%-- 
    Document   : descrizione
    Created on : 11-mag-2017, 19.15.25
    Author     : archer
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>NerdBook: Descrizione</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Mattia Frongia">
        <meta name="keywords" content="descrizione NerdBook">
        <base href="/AmmM1/">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">
    </head>
    <body>
        <c:set var="page" value="descrizione" scope="request"/>
        <jsp:include page="header.jsp"/>
        
        <div id="divBody">
            <div id="descrizione">
                <div id="subtitle"> <!-- sottotioli -->
                    <h2>Il tuo NerdWorld a portata di mano.</h2>
                    <h5>Assumere con cautela. Può contenere tracce di latte.</h5>
                </div>
                <nav id="sommario">
                   <ul>
                    <!-- lista dei link interni -->
                        <li><a href="#signinDescription">Iscriviti subito!</a></li>
                        <li><a href="#loginDescription">Sei gi&agrave; iscritto. Vai al login.</a></li>
                        <li><a href="#chi_siamo">Chi siamo?</a></li>
                        <li><a href="#info_legali">Informazioni legali.</a></li>
                    </ul>
                </nav>
                <div id="signinDescription">
                    <br/>
                    <h2>
                        Iscriviti
                    </h2>
                    <p>
                        Al momento le iscrizioni sono chiuse. Ma non temere, la riapriremo presto.
                    </p>
                </div>
                <div id="loginDescription">
                    <br/>
                    <h2>
                        Login
                    </h2>
                    <!-- link ad una pagina esterna -->
                    <p>Accedendo al tuo profilo potrai accedere al tuo NerdWorld personale.
                        <br/>Potrai vedere i post dei tuoi amici, frugare tra le sue infomazioni personali e stalkerare
                        <br/>tutte le persone che vuoi.
                        <br/>
                        <br/><a title="Accedi al tuo profilo."
                          href="login.jsp">Clicca qui per il login.</a>
                    </p>
                </div>
                <div id="chi_siamo">
                    <br/>
                    <h2>
                        Chi siamo?
                    </h2>
                    <p>
                        <dfn title="Oh yeah!">NerdBook</dfn> &egrave; il primo sito nato per riunire tutti i nerd del mondo, in un unico posto.
                    </p>
                </div>
                <div id="info_legali">
                    <br/>
                    <h2>
                        Informazioni legali
                    </h2>
                    <p>Ogni riferimento a persone, fatti o siti realmente esistenti o esistiti, &egrave; puramente casuale.
                        <br/>Non siamo responsabili di eventuali guerre termo-nucleari, yogurt scaduti o sanguinamenti dal naso insapettati.
                        <br/>
                    </p>
                    <br/>
                    <h5>All rights reserved. <dfn title="Oh yeah">NerdBook</dfn> Corp.</h5>
                </div>
            </div>
        </div>
    </body>
</html>