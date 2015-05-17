package jp.kde.lod.jacquet.mediaselector.controller.routing;

import jp.kde.lod.jacquet.mediaselector.controller.CommandFactory;
import jp.kde.lod.jacquet.mediaselector.controller.WebContext;
import jp.kde.lod.jacquet.mediaselector.controller.command.ErrorPageCommand;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * Created by Clement on 17/05/2015.
 */
@Path("/error")
public class ErrorRouter {

    @GET
    @Path("/")
    @Produces("text/html")
    public String errorPage(@Context ServletContext context,
                            @Context HttpServletRequest request,
                            @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(ErrorPageCommand.class))
                .toString();
    }
}
