package jp.kde.lod.jacquet.mediaselector.web.service;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.web.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * Created by Clement on 10/05/2015.
 */
@Path("/media")
public class MediaService extends Service {

    @Inject
    public MediaService() {

    }

    @GET
    @Path("/")
    @Produces("text/html")
    public String createPage(@Context HttpServletRequest request) {
        return super.buildDefaultBootstrapView("Media Selector - Create", "media/create.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/search")
    @Produces("text/html")
    public String searchPage(@Context HttpServletRequest request) {
        return super.buildDefaultBootstrapView("Media Selector - Create", "media/search.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/{media-env}")
    @Produces("text/html")
    public String mediaPage(@Context HttpServletRequest request, @PathParam("media-env") String media) {
        return super.buildDefaultBootstrapView("Media Selector - " + media, "media/home.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/{media-env}/stats")
    @Produces("text/html")
    public String statsPage(@Context HttpServletRequest request, @PathParam("media-env") String media) {
        return super.buildDefaultBootstrapView("Media Selector - " + media, "media/stats.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/{media-env}/selector")
    @Produces("text/html")
    public String selectorPage(@Context HttpServletRequest request, @PathParam("media-env") String media) {
        return super.buildDefaultBootstrapView("Media Selector - " + media, "media/selector.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/{media-env}/{media-res}")
    @Produces("text/html")
    public String resourcePage(@Context HttpServletRequest request, @PathParam("media-env") String media, @PathParam("media-res") String res) {
        return super.buildDefaultBootstrapView("Media Selector - " + media + "/" + res, "media/resource.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/{media-env}/{media-res}/predictor")
    @Produces("text/html")
    public String predictorPage(@Context HttpServletRequest request, @PathParam("media-env") String media, @PathParam("media-res") String res) {
        return super.buildDefaultBootstrapView("Media Selector - " + media + "/" + res, "media/predictor.ftl", super.getAuthenticatedUser(request));
    }
}
