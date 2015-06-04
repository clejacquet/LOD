/**
 * Created by Clement on 22/05/2015.
 */

function updateLikeClickEvent(node) {
    $(node).find(".like").click(function() {
        sendFeedback($(this).parent().parent(), "like");
    });
    updatePredictClickEvent(node);
}

function updateDislikeClickEvent(node) {
    $(node).find(".dislike").click(function() {
        sendFeedback($(this).parent().parent(), "dislike");
    });
}

function updatePredictClickEvent(node) {
    $(node).find(".predict").click(function() {
        makePrediction($(this).parent().parent());
    });
}

function makePrediction(node) {
    $.ajax({
        type: 'POST',
        url: window.location.href + "/res",
        data: {
            'prediction' : 'true',
            'resource_uri' : $(node).find(".resource-uri").text()
        }
    }).done(function(data) {
        switch (data.prediction) {
            case 'love': showPrediction('success', "<strong>Prediction: </strong> You would love it!", node); break;
            case 'like': showPrediction('info', "<strong>Prediction: </strong> You should like it", node); break;
            case 'dislike': showPrediction('warning', "<strong>Prediction: </strong> We think that you're going to not like it", node); break;
            case 'hate': showPrediction('danger', "<strong>Prediction: </strong> Don't approach it, it will be a time loss for you!", node); break;
            default : showPrediction('warning', "<strong>No Prediction</strong>", node);
        }
    });
}

function showPrediction(type, message, node) {
    var predictionDiv = $(node).find(".prediction-div");
    $(predictionDiv).hide().empty();
    $(predictionDiv).append(
        $("<div></div>").attr("class", "alert alert-" + type + " alert-dismissible").attr("role", "alert").append(
            $("<button></button>").attr("class", "close").attr("type", "button").attr("data-dismiss", "alert").attr("aria-label", "Close").append(
                $("<span></span>").attr("aria-hidden", "true").append("&times;")
            )
        ).append(message)
    );
    $(predictionDiv).slideDown(100);
}

function displayLikeButton(node, resourceUri) {
    $(node).find(".dislike").remove();
    $(node).find(".rate").append(
        $("<button></button>").attr("class", "btn btn-default predict").append(
            $("<span></span>").attr("class", "glyphicon glyphicon-eye-open")
        ).append(" Predict")
    ).append(
        $("<button></button>").attr("class", "btn btn-primary like").append(
            $("<span></span>").attr("class", "glyphicon glyphicon-heart")
        ).append(" Like").append(
            $("<span></span>").attr("class", "resource-uri").css("display", "none").append(resourceUri)
        )
    );
}

function displayDislikeButton(node, resourceUri) {
    $(node).find(".predict").remove();
    $(node).find(".like").remove();
    $(node).find(".rate").append(
        $("<button></button>").attr("class", "btn btn-danger dislike").append(
            $("<span></span>").attr("class", "glyphicon glyphicon-heart")
        ).append(" Remove Like").append(
            $("<span></span>").attr("class", "resource-uri").css("display", "none").append(resourceUri)
        )
    );
}

function updateLikeCount(node, likeCount) {
    $(node).find(".like-count").empty().append(" " + likeCount);
}

function sendFeedback(node, feedback) {
    var resourceUri = $(node).find(".resource-uri").text();
    $.ajax({
        type: 'POST',
        url: window.location.href + "/res",
        data: {
            'feedback': feedback,
            'resource_uri': resourceUri
        }
    }).done(function(data) {
        if (data.status === "like") {
            displayDislikeButton(node, resourceUri);
            updateDislikeClickEvent(node);
            updateLikeCount(node, data.likeCount);
        } else if (data.status === "dislike") {
            displayLikeButton(node, resourceUri);
            updateLikeClickEvent(node);
            updateLikeCount(node, data.likeCount);
        } else {
            window.location.href = '/error';
        }
    });
}