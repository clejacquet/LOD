package jp.kde.lod.jacquet.mediaselector.controller.routing;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.controller.CommandFactory;
import jp.kde.lod.jacquet.mediaselector.controller.WebContext;
import jp.kde.lod.jacquet.mediaselector.controller.command.user.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

/**
 * Created by Clement on 10/05/2015.
 *
 */
@Path("/user")
public class UserRouter {

    @Inject
    public UserRouter() {

    }

    @GET
    @Path("/")
    @Produces("text/html")
    public String userPersoPage(@Context ServletContext context,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(UserPersoPage.class))
                .toString();
    }

    @GET
    @Path("/{user-id}")
    @Produces("text/html")
    public String userPage(@Context ServletContext context,
                           @Context HttpServletRequest request,
                           @Context HttpServletResponse response,
                           @PathParam("user-id") String user) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(UserPageCommand.class)
                        .setUser(user))
                .toString();
    }

    @GET
    @Path("/connect")
    @Produces("text/html")
    public String connectPageGet(@Context ServletContext context,
                                 @Context HttpServletRequest request,
                                 @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(UserConnectionPageCommand.class))
                .toString();
    }

    @POST
    @Path("/connect")
    @Produces("text/html")
    public String connectPagePost(@Context ServletContext context,
                                  @Context HttpServletRequest request,
                                  @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(UserConnectionCommand.class))
                .toString();
    }

    @GET
    @Path("/register")
    @Produces("text/html")
    public String registerPageGet(@Context ServletContext context,
                                  @Context HttpServletRequest request,
                                  @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(UserRegisterPageCommand.class))
                .toString();
    }

    @POST
    @Path("/register")
    @Produces("text/html")
    public String registerPagePost(@FormParam("ask") String ask,
                                   @FormParam("login") String login,
                                   @FormParam("password") String password,
                                   @Context ServletContext context,
                                   @Context HttpServletRequest request,
                                   @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(UserRegisterCommand.class))
                .toString();
    }

    @GET
    @Path("/logout")
    @Produces("text/html")
    public String logoutPage(@Context ServletContext context,
                             @Context HttpServletRequest request,
                             @Context HttpServletResponse response) {
        return WebContext.getServletHandler(context, request, response)
                .processHTML(CommandFactory.buildHTMLCommand(UserLogoutCommand.class))
                .toString();
    }
}
