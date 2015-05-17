/**
 * Created by Clement on 15/05/2015.
 */

function addLine(link, title) {
    $("<li></li>").addClass('list-group-item').append("<a></a>").attr("href", link).append(title);
}

$(document).ready(function() {
    $("#search-div").hide();

    $("#search-button").click(function() {
        $.ajax({
            type: 'POST',
            url: '/media/search',
            data: {
                'search-text': 'MovieLens'
            }
        }).done(function(data) {

        });

        $("#search-div").hide().slideDown(400);
    });
});