<%-- 
    Document   : index
    Created on : 11-mag-2017, 19.04.09
    Author     : archer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Nerdbook: Start Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author"
              content="Mattia Frongia">
        <meta name="keywords"
              content="index NerdBook">
        <link rel="stylesheet"
              type="text/css"
              href="M2/style.css"
              media="screen">
    </head>
    <body>
        <div id="index">
            <c:set var="page" value="bacheca" scope="request"/>
            <jsp:include page="header.jsp"/>
            <div id="divBody">
                <h2>
                    Pagina di prova. Clicca sui link in alto per cominciare la navigazione.
                    <br/><br/>
                    h2 Benvenuto nella pagina principale di NerdBook.<br/>
                </h2>
                <h3>
                    h3 Per sapere di pi&ugrave; su questo bellissimo sito clicca Descrizione, in alto a destra.
                </h3>
                <h4>
                    h4 Buon divertimento!
                </h4>
                <p>
                    p ciao
                </p>
            </div>
        </div>
    </body>
</html>


