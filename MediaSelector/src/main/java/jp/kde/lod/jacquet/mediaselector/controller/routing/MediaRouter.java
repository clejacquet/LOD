package jp.kde.lod.jacquet.mediaselector.controller.routing;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.controller.CommandFactory;
import jp.kde.lod.jacquet.mediaselector.controller.WebContext;
import jp.kde.lod.jacquet.mediaselector.controller.command.html.media.*;
import jp.kde.lod.jacquet.mediaselector.controller.command.service.media.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

/**
 * Created by Clement on 10/05/2015.
 */
@Path("/media")
public class MediaRouter {

    @Inject
    public MediaRouter() {

    }

    @GET
    @Path("/create")
    @Produces("text/html")
    public String createPage(@Context ServletContext context,
                             @Context HttpServletRequest request,
                             @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(MediaCreatePageCommand.class))
                .toString();
    }

    @GET
    @Path("/search")
    @Produces("text/html")
    public String searchPage(@Context ServletContext context,
                             @Context HttpServletRequest request,
                             @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(MediaSearchPageCommand.class))
                .toString();
    }

    @GET
    @Path("/{media-env}")
    @Produces("text/html")
    public String mediaPage(@Context ServletContext context,
                            @Context HttpServletRequest request,
                            @Context HttpServletResponse response,
                            @PathParam("media-env") String media) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(MediaPageCommand.class)
                        .setMedia(media))
                .toString();
    }

    @POST
    @Path("/create")
    @Produces("application/json")
    public String createValidationPage(@Context ServletContext context,
                                       @Context HttpServletRequest request,
                                       @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processJSON(CommandFactory.buildJSONCommand(MediaCreateCommand.class))
                .toString();
    }



    @POST
    @Path("/search")
    @Produces("application/json")
    public String searchService(@Context ServletContext context,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processJSON(CommandFactory.buildJSONCommand(MediaSearchCommand.class))
                .toString();
    }

    @POST
    @Path("/{media-env}")
    @Produces("application/json")
    public String mediaCommand(@Context ServletContext context,
                               @Context HttpServletRequest request,
                               @Context HttpServletResponse response,
                               @PathParam("media-env") String media) {
        return WebContext.getServletHandler(context, request, response)
                .processJSON(CommandFactory.buildJSONCommand(MediaCommand.class)
                        .setMedia(media))
                .toString();
    }

    @POST
    @Path("/{media-env}/search")
    @Produces("application/json")
    public String mediaSearchService(@Context ServletContext context,
                                     @Context HttpServletRequest request,
                                     @Context HttpServletResponse response,
                                     @PathParam("media-env") String media) {
        return WebContext.getServletHandler(context, request, response)
                .processJSON(CommandFactory.buildJSONCommand(MediaEnvSearchCommand.class)
                        .setMediaId(media))
                .toString();
    }

    @POST
    @Path("/{media-env}/selector")
    @Produces("application/json")
    public String selector(@Context ServletContext context,
                           @Context HttpServletRequest request,
                           @Context HttpServletResponse response,
                           @PathParam("media-env") String media) {
        return WebContext.getServletHandler(context, request, response)
                .processJSON(CommandFactory.buildJSONCommand(MediaSelectorCommand.class)
                        .setMedia(media))
                .toString();
    }

    @POST
    @Path("/{media-env}/res")
    @Produces("application/json")
    public String resourcePrediction(@Context ServletContext context,
                               @Context HttpServletRequest request,
                               @Context HttpServletResponse response,
                               @PathParam("media-env") String media) {
        return WebContext.getServletHandler(context, request, response)
                .processJSON(CommandFactory.buildJSONCommand(MediaResourceCommand.class)
                        .setMedia(media))
                .toString();
    }
}
