package jp.kde.lod.jacquet.mediaselector.web.service;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.persistence.dao.MediaDao;
import jp.kde.lod.jacquet.mediaselector.rdf.Media;
import jp.kde.lod.jacquet.mediaselector.web.Service;
import jp.kde.lod.jacquet.mediaselector.web.ViewLayout;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 10/05/2015.
 */
@Path("/media")
public class MediaService extends Service {

    @Inject
    private MediaDao mediaDao;

    @Inject
    public MediaService() {

    }

    @GET
    @Path("/")
    @Produces("text/html")
    public String homePage(@Context HttpServletRequest request) {
        return super.buildDefaultBootstrapView("Media Selector - Media Home", "media/media.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/create")
    @Produces("text/html")
    public String createPage(@Context HttpServletRequest request,
                             @Context HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            ViewLayout layout = ViewLayout.buildBootstrapViewLayout("Media Selector - Create");
            layout.addCssLink("/css/media/create/create.css");
            layout.addJsLink("/js/media/create/resource-button-action.js");

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("user", super.getAuthenticatedUser(request));

            String view = this.getView("media/create.ftl", layout, parameters);
            return ((view != null) ? view : "Error!");
        } else {
            try {
                response.sendRedirect("/user/connect");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @POST
    @Path("/create/validate")
    @Produces("application/json")
    public String createValidationPage(@Context HttpServletRequest request,
                                       @FormParam("media") String mediaJsonStr) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            JSONObject mediaJson = new JSONObject(new JSONTokener(mediaJsonStr));
            Media media = new Media(mediaJson, true);
            this.mediaDao.saveMedia(media);

            JSONObject response = new JSONObject();
            response.append("status", "success");
            response.append("redirect", "/media/" + media.getId());
            return response.toString();
        } else {
            JSONObject response = new JSONObject();
            response.append("status", "fail");
            response.append("redirect", "/");
            return response.toString();
        }
    }

    @POST
    @Path("/create/success")
    @Produces("text/html")
    public String createSuccessPage(@Context HttpServletRequest request,
                                    @FormParam("media-id") String mediaId) {
        Media media = this.mediaDao.getMedia(mediaId);
        if (media != null) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("media", media);
            return super.buildDefaultBootstrapView("Media Selector - Create Success", "media/create_success.ftl", parameters);
        }
        return "Error";
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
    public String mediaPage(@Context HttpServletRequest request,
                            @PathParam("media-env") String media) {
        return super.buildDefaultBootstrapView("Media Selector - " + media, "media/home.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/{media-env}/stats")
    @Produces("text/html")
    public String statsPage(@Context HttpServletRequest request,
                            @PathParam("media-env") String media) {
        return super.buildDefaultBootstrapView("Media Selector - " + media, "media/stats.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/{media-env}/selector")
    @Produces("text/html")
    public String selectorPage(@Context HttpServletRequest request,
                               @PathParam("media-env") String media) {
        return super.buildDefaultBootstrapView("Media Selector - " + media, "media/selector.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/{media-env}/{media-res}")
    @Produces("text/html")
    public String resourcePage(@Context HttpServletRequest request,
                               @PathParam("media-env") String media,
                               @PathParam("media-res") String res) {
        return super.buildDefaultBootstrapView("Media Selector - " + media + "/" + res, "media/resource.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/{media-env}/{media-res}/predictor")
    @Produces("text/html")
    public String predictorPage(@Context HttpServletRequest request,
                                @PathParam("media-env") String media,
                                @PathParam("media-res") String res) {
        return super.buildDefaultBootstrapView("Media Selector - " + media + "/" + res, "media/predictor.ftl", super.getAuthenticatedUser(request));
    }
}
