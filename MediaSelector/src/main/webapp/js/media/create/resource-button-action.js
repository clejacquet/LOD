/**
 * Created by Clement on 11/05/2015.
 */

function fillResource(resourceNode) {
    var resource = {};
    resource.name = $(resourceNode).find(".resource-name").first().val();
    resource.type = $(resourceNode).find(".resource-type").first().val();
    return resource;
}

function fillRelatedResource(resourceNode) {
    var resource = fillResource(resourceNode);
    resource.property = $(resourceNode).find(".resource-property").first().val();
    return resource;
}

$(document).ready(function() {
    $(".add-resource").click(function() {
        $("#resources-row-template").clone().removeAttr("id").attr("class", "resource").hide().appendTo("#related-resources").slideDown(200).find(".remove-resource").click(function() {
            $(this).parent().parent().parent().slideUp(200, function() { this.remove();});
        })
    });

    $("#resources-submit").click(function () {
        var media = {};

        var mediaResource = $("#media-resource");
        media.resource = fillResource(mediaResource);
        media.name = $("#media-name").val();
        media.sparql = $("#media-sparql").val();

        media.relatedResources = [];
        $("#related-resources").find(".resource").each(function () {
            media.relatedResources.push(fillRelatedResource(this));
        });

        var jsonMedia = JSON.stringify(media);

        $.ajax({
            type: 'POST',
            url: '/media/create/validate',
            data: {
                'media': jsonMedia
            }
        }).done(function(data) {
            window.location.href = data.redirect;
        })
    });
});
