/**
 * Created by Clement on 04/06/2015.
 */

function removeSubscription(sender, mediaId) {
    $.ajax({
        type: 'POST',
        url: '/media/' + mediaId,
        data: {
            'subscription' : 'undo'
        }
    }).done(function(data) {
        if (data.subscriptionStatus === "unsubscribed") {
            $(sender).remove();
        }
    });
}

$(document).ready(function() {
    $(".unsubscribe-button").click(function() {
        var mediaId = $(this).parent().find(".media-id").text();
        removeSubscription($(this).parent(), mediaId);
    });
});