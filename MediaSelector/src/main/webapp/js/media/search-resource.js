/**
 * Created by Clement on 15/05/2015.
 */

$("#search-div").hide();

function process() {
    $('#search-results').empty();
    $("#search-div").hide();

    $.ajax({
        type: 'POST',
        url: window.location.pathname + "/search",
        data: {
            'search-text': $('#search-input').val(),
            'language': $('#language').val()
        }
    }).done(function(data) {
        $(data.resources).each(function() {
            $('#search-results').append(addResult(this, data.mediaId));
        });
        updateLikeClickEvent($('#search-results'));
        updateDislikeClickEvent($('#search-results'));
        $("#search-div").hide().slideDown(100);
    });
}

$(document).ready(function() {

    $('#search-input').bind("enterKey",function(e){
        process();
    });
    $('#search-input').keyup(function(e){
        if(e.keyCode == 13)
        {
            $(this).trigger("enterKey");
        }
    });

    $("#search-button").click(function() {
        process();
    });

    $("#hide-search-results").click(function () {
        $("#search-div").hide().find("#search-results").empty();
    });
});