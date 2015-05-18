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
            url: '/media/search',
            data: {
                'search-text': $('#search-input').val()
            }
        }).done(function(data) {
            $(data.medias).each(function(i, media) {
                $('#search-results').append(addLine('/media/' + media.id, media.name));
            });
            $("#search-div").hide().slideDown(100);
        });
    });
});