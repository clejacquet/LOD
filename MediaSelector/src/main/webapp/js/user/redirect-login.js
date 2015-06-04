/**
 * Created by Clement on 04/06/2015.
 */

$(document).ready(function() {
    $("#login-link").click(function() {
        var redirection = getUrlParameter("redirection");
        if (redirection != null) {
            window.location.href = "/user/connect?redirection=" + redirection
        } else {
            window.location.href = "/user/connect"
        }
    });
});/**
 * Created by Clement on 04/06/2015.
 */
