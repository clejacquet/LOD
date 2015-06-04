$("#results-div").hide();

function addResult(result, mediaId) {
    return $("<li></li>").addClass('list-group-item').append(
        ((result.liked) ? (
            $("<div></div>").attr("class", "rate pull-right").css("display", "inline").append(
                $("<button></button>").attr("type", "button").attr("class", "btn btn-danger dislike").append(
                    $("<span></span>").attr("class", "glyphicon glyphicon-heart")
                ).append(
                    $("<span></span>").attr("class", "rate-text").append(" Dislike")
                ).append(
                    $("<span></span>").attr("class", "resource-uri").css("display", "none").append(result.uri)
                )
            )
        ) : (
            $("<div></div>").attr("class", "rate pull-right").css("display", "inline").append(
                $("<button></button>").attr("type", "button").attr("class", "btn btn-default predict").append(
                    $("<span></span>").attr("class", "glyphicon glyphicon-eye-open")
                ).append(
                    $("<span></span>").attr("class", "rate-text").append(" Predict")
                )
            ).append(
                $("<button></button>").attr("type", "button").attr("class", "btn btn-primary like").append(
                    $("<span></span>").attr("class", "glyphicon glyphicon-heart")
                ).append(
                    $("<span></span>").attr("class", "rate-text").append(" Like")
                ).append(
                    $("<span></span>").attr("class", "resource-uri").css("display", "none").append(result.uri)
                )
            )
        ))
    ).append(
        $("<a></a>").attr("href", result.uri).append(
            $("<h4></h4>").append(result.name).append(
                $("<span></span>").attr("class", "badge").append(
                    $("<span></span>").attr("class", "glyphicon glyphicon-heart")
                ).append(
                    $("<span></span>").attr("class", "like-count").append(" " + result.like_count)
                ).css("margin-left", "10px")
            )
        )
    ).append("Author :").append(
        $("<a></a>").attr("href", result.author).append(result.author_name)
    ).append(
        $("<div></div>").attr("class", "prediction-div").hide()
    ).append("<br/>").append(
        $("<p></p>").append(result.abstract)
    )
}

$(document).ready(function() {
    $("#selector-button").click(function() {
        $.ajax({
            type: 'POST',
            url: window.location.href + "/selector"
        }).done(function(data) {
            $("#results").empty();
            $(data.resources).each(function(i, resource) {
                $('#results').append(addResult(resource, data.mediaId));
            });

            updateLikeClickEvent($('#results'));
            updateDislikeClickEvent($('#results'));
            $("#results-div").hide().slideDown(100);
        });
    });

    $("#hide-selector-results").click(function () {
        $("#results-div").hide().find("#results").empty();
    });
});