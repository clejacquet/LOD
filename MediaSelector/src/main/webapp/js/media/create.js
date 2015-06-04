/**
 * Created by Clement on 11/05/2015.
 */

function fillResource(resourceNode) {
    var resource = {};
    resource.name = $(resourceNode).find(".resource-name").first().val();
    resource.type = $(resourceNode).find(".resource-type").first().val();
    resource.titleProperty = $(resourceNode).find(".resource-title-property").first().val();
    return resource;
}

$(document).ready(function() {
    $("#resources-submit").click(function () {
        var media = {};

        media.resource = fillResource($("#media-resource"));
        media.name = $("#media-name").val();
        media.description = $("#description").val();
        media.sparql = $("#media-sparql").val();

        media.resource.authorProperty = $("#author-property").val();
        media.resource.authorNameProperty = $("#author-name-property").val();
        media.resource.abstractProperty = $("#abstract-property").val();

        var jsonMedia = JSON.stringify(media);

        $.ajax({
            type: 'POST',
            url: '/media/create',
            data: {
                'media': jsonMedia
            }
        }).done(function(data) {
            window.location.href = data.redirect;
        });
    });
});
