/**
 * Created by Clement on 15/05/2015.
 */

function addResult(result) {
    return $("<li></li>").addClass('list-group-item').append(
        $("<a></a>").attr("href", result.uri).append(
            $("<h4></h4>").append(result.name)
        )
    ).append("Author :").append(
        $("<a></a>").attr("href", result.author).append(result.author_name)
    ).append("<br/>").append("<br/>").append(
        $("<p></p>").append(result.abstract)
    )
}

function process() {
    $('#search-results').empty();
    $("#search-div").hide();

    $.ajax({
        type: 'POST',
        url: window.location.pathname,
        data: {
            'search-text': $('#search-input').val(),
            'language': $('#language').val()
        }
    }).done(function(data) {
        $(data.resources).each(function() {
            $('#search-results').append(addResult(this));
        });
        $("#search-div").hide().slideDown(100);
    });
}

$(document).ready(function() {
    $("#search-div").hide();


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
});