<%-- 
    Document   : profilo
    Created on : 13-mag-2017, 14.32.20
    Author     : archer
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>NerdBook: Profilo</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author"
          content="Mattia Frongia">
    <meta name="keywords"
          content="profilo NerdBook">
    <link rel="stylesheet"
          type="text/css"
          href="style.css"
          media="screen">
</head>
<body>
    <c:set var="page" value="profilo" scope="request"/>
    <jsp:include page="header.jsp"/>
    <div id="divBody">
        <div id="profilo">
            <jsp:include page="sideBarSx.jsp"/>
            <div id="content">
                <div id="profileImage">
                    <img src="${loggedUser.urlFotoProfilo}"
                         title="La tua foto profilo" 
                         alt="La tua foto profilo" 
                         height="104" 
                         width="104"/>
                </div>
                         
                <div id="profileData">
                    <h4> Inserisci le seguenti informazioni per completare il tuo profilo.</h4>
                    <!--
                        Form per impostare i dati dell'utente:
                        -nome
                        -congome
                        -URL di una immagine per il profilo
                        -Frase di presentazione
                        -Data di nascita
                        -Password
                        -Conferma password
                    -->
                    <form action="Profilo?userDetailsUpdated=true" method="post" name="profileData">
                            <!-- Nome -->
                                <label for="usrName">Nome</label>
                                <input type="text"
                                       name="usrName"
                                       id="usrName"
                                       size="30"
                                       maxlength="50"
                                       title="Nome"
                                           placeholder="${loggedUser.nome}"/>
                            <!-- Cognome -->
                                <label for="usrSurname">Cognome</label>
                                <input type="text"
                                       name="usrSurname"
                                       id="usrSurname"
                                       size="30"
                                       maxlength="50"
                                       title="Cognome"
                                           placeholder="${loggedUser.cognome}">
                            <!-- URL immagine profilo -->
                                <label for="usrImgURL">URL foto profilo</label>
                                <input type="url"
                                       name="usrImgURL"
                                       id="usrImgURL"
                                       size="30"
                                       title="URL foto profilo"
                                       <c:if test="${loggedUser.urlFotoProfilo != 'img/default.jpg'}">
                                           placeholder="${loggedUser.urlFotoProfilo}"/>
                                       </c:if>
                                       <c:if test="${loggedUser.urlFotoProfilo == 'img/default.jpg'}">
                                           placeholder="Inserisci l'URL di un' immagine"/>
                                       </c:if>
                                <!-- Data di nascita -->
                                <label for="usrBDay">Data di nascita</label>
                                <input type="date"
                                       name="usrBDay"
                                       id="usrBDay"
                                       size="30"
                                       title="Data di nascita"/>
                            <!-- Frase di presentazione -->
                                <label for="usrPresentation">Frase di presentazione</label>
                                <textarea rows="4" cols="22"
                                          name="usrPresentation"
                                          id="usrPresentation"
                                          title="Frase di presentazione"
                                           placeholder="${loggedUser.citazione}"></textarea>
                            <!-- Password -->
                                <label for="usrPass">Password</label>
                                <input type="password"
                                       name="usrPass"
                                       id="usrPass"
                                       size="30"
                                       title="Password"
                                           placeholder="Nuova password"/>
                            <!-- Conferma password -->
                                <label for="usrPassConfirm">Conferma password</label>
                                <input type="password"
                                       name="usrPassConfirm"
                                       id="usrPassConfirm"
                                       size="30"
                                       title="Conferma password"
                                           placeholder="Conferma nuova password"/>
                                <c:if test="${passConfirmError == true}">
                                    <div class="error" id="passConfirmError">
                                        <p>Le password inserite non coincidono</p>
                                    </div>
                                </c:if>
                                <c:if test="${userDetailsUpdated == true}">
                                    <div id="userDetailsUpdated">
                                        <p>Dettagli del profilo aggiornati</p>
                                    </div>
                                </c:if>
                        <!-- Tasti invio -->
                        <input type="submit" value="Invia"/>
                    </form>
                </div>
            </div>
            <div id="sideBarDx">
                <!-- Da aggiungere -->
            </div>
        </div>
    </div>
</body>