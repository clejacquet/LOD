package jp.kde.lod.jacquet.mediaselector.web.service;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.web.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * Created by Clement on 10/05/2015.
 */
@Path("/")
public class HomeService extends Service {

    @Inject
    public HomeService() {

    }

    @GET
    @Path("/")
    @Produces("text/html")
    public String defaultPage(@Context HttpServletRequest request) {
        return this.homePage(request);
    }

    @GET
    @Path("/home")
    @Produces("text/html")
    public String homePage(@Context HttpServletRequest request) {
        return super.buildDefaultBootstrapView("Media Selector - Home", "home.ftl", super.getAuthenticatedUser(request));
    }
}
