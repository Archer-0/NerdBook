
function createElement(utente) {
    
    var img = $("<img>")
            .attr("alt", "Foto profilo di " + utente.nome)
            .attr("src", utente.urlFotoProfilo);
    var link = $("<a>").attr("href", "bacheca.html?userIdToVisit=" + utente.id)
            .html(utente.nome + " " + utente.cognome);
    return $("<li>")
            .append(img)
            .append(link);
}

function stateSuccess(data){
    var userListPage = $("#resultList");
    
    $(userListPage).empty();
    
    if (jQuery.isEmptyObject(data)) {
        $(userListPage).append();
    }
    
    for(var instance in data){
        $(userListPage).append(createElement(data[instance]));
    }
}

function ricercaFallita() {
    return $("<p>").html("Nessun risultato :/");
}

function stateFailure(data, state){
    $("#resultList").empty();
    $("#resultList").append(ricercaFallita());
    console.log(state);
}

function updateResults() {
    var result = $("#searchBox")[0].value;
    
    $.ajax({
        url: "Filter", 
        data:{
            cmd: "search", 
            q: result
        },
        dataType: json, 
        success: function(data, state) {
            stateSuccess(data);
        },
        error: function (data, state) {
            stateFailure(data, state);
        }
    });
}

$(document).ready(function(){
    $("comeOn").click(function(){
        
        var wantedUser = $("#searchBox")[0].value;
        
        $.ajax({
            url: "Filter",
            data:{
                cmd:"search",
                nomeGattoCercato: wantedUser
            },
            dataType:"json",
            success: function(data, state){
                stateSuccess(data);
            },
            error: function(data, state){
                stateFailure(data, state);
            }
        });
    });
});