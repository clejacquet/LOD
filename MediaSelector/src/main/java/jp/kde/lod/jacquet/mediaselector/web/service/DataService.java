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

@Path("/data")
public class DataService extends Service {

    @Inject
    public DataService() {

    }

    @GET
    @Path("/")
    @Produces("text/html")
    public String dataPage(@Context HttpServletRequest request) {
        return super.buildDefaultBootstrapView("Media Selector - Data", "data/data.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/voc")
    @Produces("text/html")
    public String vocPage(@Context HttpServletRequest request) {
        return super.buildDefaultBootstrapView("Media Selector - Vocabulary", "data/voc.ftl", super.getAuthenticatedUser(request));
    }
}
