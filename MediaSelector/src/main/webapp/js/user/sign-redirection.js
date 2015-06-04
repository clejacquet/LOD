/**
 * Created by Clement on 04/06/2015.
 */

$(document).ready(function() {
    var form = $(".form-signin");
    var redirection = getUrlParameter("redirection");

    if (redirection) {
        $(form).submit(function() {
            $(form).append(
                $("<input/>").attr("type", "hidden").attr("name", "redirection").val(redirection)
            )
        });
    }
});