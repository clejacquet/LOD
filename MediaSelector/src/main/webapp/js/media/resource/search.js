/**
 * Created by Clement on 15/05/2015.
 */

function addLine(link, title) {
    return $("<li></li>").addClass('list-group-item').append(
        $("<a></a>").attr("href", link).append(title)
    );
}

$(document).ready(function() {
    $("#search-div").hide();

    $("#search-button").click(function() {
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
                $('#search-results').append(addLine(this.uri, this.name));
            });
            $("#search-div").hide().slideDown(100);
        });
    });
});