/**
 * Created by Clement on 15/05/2015.
 */


function removeSubscription(sender, mediaId) {
    sendSubscriptionAction(sender, mediaId, "undo");
    $(sender).remove();
}

function addSubscription(node, mediaId) {
    var clonedNode = $(node).clone();
    $(clonedNode).appendTo("#subscription-list").unbind("click").find("button").click(function() {
        removeSubscription($(this).parent(), mediaId);
        sendSubscriptionAction($(node).find("button"), mediaId, "undo");
    });
    $(node).find("button").click(function() {
        $(clonedNode).remove();
    });
}

function refreshSubscribeButton(sender, mediaId, status, subsCount) {
    if (status === "subscribed") {
        $(sender).attr("class", "btn btn-danger pull-right");
        $(sender).empty();
        $(sender).append("Unsubscribe");
        $(sender).unbind("click");
        $(sender).parent().find(".subs-count").empty().append(" " + subsCount + " subscriber(s)");
        addSubscription($(sender).parent(), mediaId);
        $(sender).click(function() {
            sendSubscriptionAction(sender, mediaId, "undo");
        });
    } else if (status === "unsubscribed") {
        $(sender).attr("class", "btn btn-primary pull-right");
        $(sender).empty();
        $(sender).append("Subscribe");
        $(sender).unbind("click");
        $(sender).parent().find(".subs-count").empty().append(" " + subsCount + " subscriber(s)");
        $(sender).click(function() {
            sendSubscriptionAction(sender, mediaId, "do");
        });
    }
}

function sendSubscriptionAction(sender, mediaId, action) {
    $.ajax({
        type: 'POST',
        url: '/media/' + mediaId,
        data: {
            'subscription' : action,
            'subscriber_count' : 'get'
        }
    }).done(function(data) {
        refreshSubscribeButton(sender, mediaId, data.subscriptionStatus, data.subscriberCount);
    });
}

function addLine(mediaId, title, desc, subsCount, isSubs) {
    return $("<li></li>").addClass('list-group-item').append(
        $("<a></a>").attr("href", '/media/' + mediaId).css("font-weight", "bold").css("font-size", "1.2em").append(title)
    ).append(
        $("<span></span>").append(
            $("<span></span>").attr("class", "badge").css("margin-left", "10px").append(
                $("<span></span>").attr("class", "glyphicon glyphicon-heart")
            ).append(
                $("<span></span>").attr("class", "subs-count").append(" " + subsCount + " subscriber(s)")
            )
        )
    ).append(
        ((isSubs) ? (
            $("<button></button>").attr("class", "unsubscribe-button").attr("class", "btn btn-danger pull-right").append("Unsubscribe").click(function() {
                sendSubscriptionAction(this, mediaId, "undo");
            })
        ) : (
            $("<button></button>").attr("class", "subscribe-button").attr("class", "btn btn-primary pull-right").append("Subscribe").click(function() {
                sendSubscriptionAction(this, mediaId, "do");
            })
        ))
    ).append(
        $("<p></p>").append(desc)
    );
}

function process() {
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
            $.ajax({
                type: 'POST',
                url: '/media/' + media.id,
                data: {
                    'subscriber_count' : 'get',
                    'is_subscribed' : 'get'
                }
            }).done(function(subdata) {
                $('#search-results').append(addLine(media.id, media.name, media.description, subdata.subscriberCount, subdata.isSubscribed));
            });
        });
        $("#search-div").hide().slideDown(100);
    });
}

$(document).ready(function() {
    $("#search-div").hide();
    $(".unsubscribe-button").click(function () {
        var mediaId = $(this).parent().find(".media-id").text();
        removeSubscription($(this).parent(), mediaId);
    });

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