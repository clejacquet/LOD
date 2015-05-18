package jp.kde.lod.jacquet.mediaselector.controller.routing;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.controller.CommandFactory;
import jp.kde.lod.jacquet.mediaselector.controller.WebContext;
import jp.kde.lod.jacquet.mediaselector.controller.command.media.*;

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
    @Path("/")
    @Produces("text/html")
    public String homePage(@Context ServletContext context,
                           @Context HttpServletRequest request,
                           @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(MediaHomePageCommand.class))
                .toString();
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

    @POST
    @Path("/create/validate")
    @Produces("application/json")
    public String createValidationPage(@Context ServletContext context,
                                       @Context HttpServletRequest request,
                                       @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processJSON(CommandFactory.buildJSONCommand(MediaValidationCommand.class))
                .toString();
    }

    @POST
    @Path("/create/success")
    @Produces("text/html")
    public String createSuccessPage(@Context ServletContext context,
                                    @Context HttpServletRequest request,
                                    @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(MediaSuccessPageCommand.class))
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

    @POST
    @Path("/search")
    @Produces("application/json")
    public String searchService(@Context ServletContext context,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processJSON(CommandFactory.buildJSONCommand(MediaEnvSearchCommand.class))
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

    @GET
    @Path("/{media-env}/search")
    @Produces("text/html")
    public String mediaSearchPage(@Context ServletContext context,
                                  @Context HttpServletRequest request,
                                  @Context HttpServletResponse response,
                                  @PathParam("media-env") String media) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(MediaEnvSearchPageCommand.class)
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
                        .setMedia(media))
                .toString();
    }

    @GET
    @Path("/{media-env}/stats")
    @Produces("text/html")
    public String statsPage(@Context ServletContext context,
                            @Context HttpServletRequest request,
                            @Context HttpServletResponse response,
                            @PathParam("media-env") String media) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(MediaStatsPageCommand.class)
                        .setMedia(media))
                .toString();
    }

    @GET
    @Path("/{media-env}/selector")
    @Produces("text/html")
    public String selectorPage(@Context ServletContext context,
                               @Context HttpServletRequest request,
                               @Context HttpServletResponse response,
                               @PathParam("media-env") String media) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(MediaSelectorPageCommand.class)
                        .setMedia(media))
                .toString();
    }

    @GET
    @Path("/{media-env}/{media-res}")
    @Produces("text/html")
    public String resourcePage(@Context ServletContext context,
                               @Context HttpServletRequest request,
                               @Context HttpServletResponse response,
                               @PathParam("media-env") String media,
                               @PathParam("media-res") String resource) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(MediaResourcePageCommand.class)
                        .setMedia(media)
                        .setResource(resource))
                .toString();
    }

    @GET
    @Path("/{media-env}/{media-res}/predictor")
    @Produces("text/html")
    public String predictorPage(@Context ServletContext context,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,
                                @PathParam("media-env") String media,
                                @PathParam("media-res") String resource) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(MediaPredictorPageCommand.class)
                        .setMedia(media)
                        .setResource(resource))
                .toString();
    }
}
