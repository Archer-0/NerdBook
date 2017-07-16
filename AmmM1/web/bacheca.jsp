<%-- 
    Document   : bacheca
    Created on : 11-mag-2017, 15.47.42
    Author     : archer
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>NerdBook: Bacheca di ${userToVisit.nome} ${userToVisit.cognome}</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author"
              content="Mattia Frongia">
        <meta name="keywords"
              content="bacheca NerdBook">
        <link rel="stylesheet"
              type="text/css"
              href="style.css"
              media="screen">
    </head>
    <body>
        <!-- tag per tornare in altro alla pagina -->
        <div id="pageHeader"></div>
        
        <!-- header -->
        <c:set var="page" value="bacheca" scope="request"/>
        <jsp:include page="header.jsp"/>
        
        <!-- contenuto della pagina -->
        <div id="divBody">    
            <div id="bacheca">
                <!-- sidebar sinistra -->
                <jsp:include page="sideBarSx.jsp"/>
                
                <div id="content">
                    <!-- postmaker -->
                    <jsp:include page="newPost.jsp"/>
                
                    <div id="postsDiv">
                        <!-- sezione post -->
                        <jsp:include page="post.jsp"/>
                    </div>
                </div>

                <div id="sideBarDx">
                    <!-- Da aggiungere -->
                </div>
            </div>
                    
            <div id="footer">
                <!-- footer -->
                <c:set var="tornaSuVar" value="#pageHeader" scope="request"/>
                <jsp:include page="footer.jsp"/>          
            </div>
        </div>    
    </body>
</html>
