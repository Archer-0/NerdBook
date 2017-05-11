<%-- 
    Document   : posts
    Created on : 11-mag-2017, 18.03.27
    Author     : archer
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="post">
    <h4>
        <a href="${utente.urlFotoProfilo}"><img src="${utente.urlFotoProfilo}"
                                                                 title="Foto profilo di ${utente.nome}"
                                                                 alt="Foto profilo di ${utente.nome}"
                                                                 height="50"
                                                                 class="accountImage"/>
        </a>
        <a href="https://www.youtube.com/watch?v=2IC2kMHZda8"
           title="The Salt Guy"
           class="accountName">The Salt Guy</a>
    </h4>
    <p>
        Ci siamo stufati di questo meme. Basta. Non ne posso pi&ugrave;.
    </p>
</div>