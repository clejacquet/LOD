package jp.kde.lod.jacquet.mediaselector.controller.routing;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.controller.CommandFactory;
import jp.kde.lod.jacquet.mediaselector.controller.WebContext;
import jp.kde.lod.jacquet.mediaselector.controller.command.doc.DocPageCommand;

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
@Path("/doc")
public class DocRouter {

    @Inject
    public DocRouter() {

    }

    @GET
    @Path("/")
    @Produces("text/html")
    public String docPage(@Context ServletContext context,
                          @Context HttpServletRequest request,
                          @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(DocPageCommand.class))
                .toString();
    }
}
