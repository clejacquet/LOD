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
@Path("/doc")
public class DocService extends Service {

    @Inject
    public DocService() {

    }

    @GET
    @Path("/")
    @Produces("text/html")
    public String docPage(@Context HttpServletRequest request) {
        return super.buildDefaultBootstrapView("Media Selector - Javadoc", "doc/javadoc.ftl", super.getAuthenticatedUser(request));
    }
}
