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
    <!-- tag per tornare in altro alla pagina -->
        <div id="pageHeader"></div>
        
    <c:set var="page" value="profilo" scope="request"/>
    <jsp:include page="header.jsp"/>
    
    <div id="divBody">
        <div id="profilo">
            <jsp:include page="sideBarSx.jsp"/>
            <div id="content">

                <c:if test="${loggedUser.id == userIdToVisit}">
                    
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
                        <form action="Profilo?userDetailsUpdated=true&userIdToVisit=${loggedUser.id}" method="post" name="profileData">
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
                                               placeholder="Cambia la foto profilo"
                                           </c:if>
                                           <c:if test="${loggedUser.urlFotoProfilo == 'img/default.jpg'}">
                                               placeholder="Imposta una foto profilo"
                                           </c:if>/>
                                    <!-- Data di nascita -->
                                    <label for="usrBDay">Data di nascita</label>
                                    <input type="date"
                                           name="usrBDay"
                                           id="usrBDay"
                                           size="30"
                                           <c:if test="${loggedUser.dataNascita != null}">
                                               value="${loggedUser.dataNascita}"
                                           </c:if>
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
                            <input type="submit" value="Accetta"/>
                        </form>
                    </div>
                    <div id="selfDestruction">
                        <form name="selfDestrunctionForm" 
                              action="Profilo?selfDestructionRequested=true&userIdToVisit=${loggedUser.id}" 
                              method="post">
                            <strong>Attenzione</strong><br/>
                            <c:if test="${removeUserPassErr != true && removeErr != true }">
                                <p>Per eliminare il tuo profilo NerdBook digita la tua password personale e premi il <strong>TASTO ROSSO</strong></p>
                            </c:if>
                            <c:if test="${removeUserPassErr == true}">
                                <strong>La password inserita non &egrave; esatta.</strong>
                                <br/>
                                <p>Hai avuto un'altra possibilit&agrave; per ripensarci.</p>
                            </c:if>
                            <c:if test="${removeErr == true}">
                                <strong>Si &egrave; verificato un problema.</strong>
                                <p>Probabilmente non era destino.</p><br/>
                                <p>PS. Stiamo lavorando per risolvere il problema (credo)</p>
                            </c:if>
                            <input type="password"
                                   name="selfDestructionPass" 
                                   id="selfDestructionPass"
                                   size="30"
                                   required
                                   placeholder="Password" 
                                   title="Pensaci bene, non potrai pi&ugrave; tornare indietro dopo aver premuto il TASTO ROSSO"/>
                            <button class="redButton" type="submit" name="selfDestruction" value="ON">Elimina il tuo account NerdBook :(</button>
                        </form>
                    </div>
                </c:if>
                
                <c:if test="${loggedUser.id != userIdToVisit || groupIdToVisit != null}">
                    <div id="otherProfiles">
                        
                        <c:if test="${userIdToVisit != null}">                            
                            <div id="profileImage">
                                <img src="${visitedUser.urlFotoProfilo}"
                                     title="La foto profilo di ${visitedUser.nome}" 
                                     alt="La foto profilo di ${visitedUser.nome}" 
                                     height="104" 
                                     width="104"/>
                            </div>
                            
                            <div class="userDetails">
                                
                                <div class="detail">
                                    <div class="title">
                                        <h5>Nome: </h5>
                                    </div>
                                    <div class="content">
                                        <p>${visitedUser.nome}</p>
                                    </div>
                                </div>
                                    
                                <div class="detail">    
                                    <div class="title">
                                        <h5>Cognome: </h5>
                                    </div>
                                    <div class="content">
                                        <p>${visitedUser.cognome}</p>
                                    </div>
                                    </div>
                                    
                                <div class="detail">    
                                    <div class="title">
                                        <h5>Data di Nascita: </h5>
                                    </div>
                                    <div class="content">
                                        <p>${visitedUser.dataNascita}</p>
                                    </div>
                                </div>
                                
                                <div class="detail">
                                    <div class="title">
                                        <h5>Email: </h5>
                                    </div>
                                    <div class="content">
                                        <p>${visitedUser.email}</p>
                                    </div>
                                </div>
                                    
                                <div class="detail">
                                    <div class="title">
                                        <h5>Citazione: </h5>
                                    </div>
                                    <div class="content">
                                        <p>"${visitedUser.citazione}"</p>
                                    </div>
                                </div>
                            </div>
                            
                        </c:if>
                        <c:if test="${groupIdToVisit != null}">
                            
                            <c:if test="${groupIdToVisit != null}">                            
                                <div id="profileImage">
                                    <img src="${visitedGroup.urlImmagine}"
                                         title="La foto profilo di ${visitedGroup.nome}" 
                                         alt="La foto profilo di ${visitedGroup.nome}" 
                                         height="104" 
                                         width="104"/>
                                </div>

                                <div class="userDetails">

                                    <div class="detail">
                                        <div class="title">
                                            <h5>Nome: </h5>
                                        </div>
                                        <div class="content">
                                            <p>${visitedGroup.nome}</p>
                                        </div>
                                    </div>

                                    <div class="detail">    
                                        <div class="title">
                                            <h5>Utenti Aderenti: </h5>
                                        </div>
                                        <div class="content">
                                            <p>${visitedGroup.nUtenti}</p>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </div>
                </c:if>
            </div>
            <div id="sideBarDx">
                
            </div>
        </div>
            
        <div id="footer">
            <!-- footer -->
            <c:set var="tornaSuVar" value="#pageHeader" scope="request"/>
            <jsp:include page="footer.jsp"/>          
        </div>
    </div>
</body>