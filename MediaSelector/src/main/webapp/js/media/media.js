function refreshSubscribeButton(subscription, mediaId, status, subsCount) {
    if (status === "subscribed") {
        $(subscription).attr("class", "btn btn-danger pull-right");
        $(subscription).empty();
        $(subscription).append("Unsubscribe");
        $(subscription).unbind("click");
        $(subscription).parent().find(".subs-count").empty().append(" " + subsCount + " subscriber(s)");
        $(subscription).click(function() {
            sendSubscriptionAction(subscription, mediaId, "undo");
        });
    } else if (status === "unsubscribed") {
        $(subscription).attr("class", "btn btn-primary pull-right");
        $(subscription).empty();
        $(subscription).append("Subscribe");
        $(subscription).unbind("click");
        $(subscription).parent().find(".subs-count").empty().append(" " + subsCount + " subscriber(s)");
        $(subscription).click(function() {
            sendSubscriptionAction(subscription, mediaId, "do");
        });
    }
}

function sendSubscriptionAction(subscription, mediaId, action) {
    $.ajax({
        type: 'POST',
        url: '/media/' + mediaId,
        data: {
            'subscription' : action,
            'subscriber_count' : 'get'
        }
    }).done(function(data) {
        refreshSubscribeButton(subscription, mediaId, data.subscriptionStatus, data.subscriberCount);
    });
}

$(document).ready(function() {
    $("#subscribe-button").click(function() {
        sendSubscriptionAction(this, $("#media-id").text(), "do");
    });
    $("#unsubscribe-button").click(function() {
        sendSubscriptionAction(this, $("#media-id").text(), "undo");
    });
    $("#delete-button").click(function() {
        if (!confirm("Are you sure that you want to delete this media rater?")) {
            return;
        }
        $.ajax({
            type: 'POST',
            url: '/media/' + $("#media-id").text(),
            data: {
                'delete' : 'yes'
            }
        }).done(function(data) {
            if (data.deleteStatus === "done") {
                window.location.href = "/home";
            }
        });
    });
});


