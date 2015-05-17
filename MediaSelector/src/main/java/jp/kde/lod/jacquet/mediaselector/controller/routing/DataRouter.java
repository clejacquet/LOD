package jp.kde.lod.jacquet.mediaselector.controller.routing;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.controller.CommandFactory;
import jp.kde.lod.jacquet.mediaselector.controller.WebContext;
import jp.kde.lod.jacquet.mediaselector.controller.command.data.DataPageCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.data.DataVocPageCommand;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * Created by Clement on 10/05/2015.
 */

@Path("/data")
public class DataRouter {

    @Inject
    public DataRouter() {

    }

    @GET
    @Path("/")
    @Produces("text/html")
    public String dataPage(@Context ServletContext context,
                           @Context HttpServletRequest request,
                           @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(DataPageCommand.class))
                .toString();
    }

    @GET
    @Path("/voc")
    @Produces("text/html")
    public String vocPage(@Context ServletContext context,
                          @Context HttpServletRequest request,
                          @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(DataVocPageCommand.class))
                .toString();
    }
}
