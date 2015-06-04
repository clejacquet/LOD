/**
 * Created by Clement on 04/06/2015.
 */

$(document).ready(function() {
    $("#register-link").click(function() {
        var redirection = getUrlParameter("redirection");
        if (redirection != null) {
            window.location.href = "/user/register?redirection=" + redirection
        } else {
            window.location.href = "/user/register"
        }
    });
});