package jp.kde.lod.jacquet.mediaselector.web.service;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.persistence.dao.UserDao;
import jp.kde.lod.jacquet.mediaselector.persistence.domain.User;
import jp.kde.lod.jacquet.mediaselector.util.EncryptionUtils;
import jp.kde.lod.jacquet.mediaselector.web.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Clement on 10/05/2015.
 *
 */
@Path("/user")
public class UserService extends Service {

    @Inject
    private UserDao userDao;

    @Inject
    public UserService() {

    }

    @GET
    @Path("/")
    @Produces("text/html")
    public String userPersoPage(@Context HttpServletRequest request) {
        return super.buildDefaultBootstrapView("Media Selector - User", "user/user.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/{user-id}")
    @Produces("text/html")
    public String userPage(@Context HttpServletRequest request, @PathParam("user-id") String user) {
        return super.buildDefaultBootstrapView("Media Selector - User " + user, "user/user.ftl", super.getAuthenticatedUser(request));
    }

    @GET
    @Path("/connect")
    @Produces("text/html")
    public String connectPageGet(@Context HttpServletRequest request) {
        return super.buildDefaultBootstrapView("Media Selector - User Connection", "user/connection.ftl", super.getAuthenticatedUser(request));
    }

    @POST
    @Path("/connect")
    @Produces("text/html")
    public String connectPagePost(@FormParam("ask") String ask,
                                  @FormParam("login") String login,
                                  @FormParam("password") String password,
                                  @Context HttpServletRequest request,
                                  @Context HttpServletResponse response) {
        if (ask != null) {
            if (ask.equals("connection")) {

                User user = this.userDao.logUser(login, password);
                if (user == null) {
                    try {
                        response.sendRedirect("/error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    try {
                        response.sendRedirect("/home");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }
        return this.connectPageGet(request);
    }

    @GET
    @Path("/register")
    @Produces("text/html")
    public String registerPageGet(@Context HttpServletRequest request) {
        return super.buildDefaultBootstrapView("Media Selector - Register", "user/register.ftl", super.getAuthenticatedUser(request));
    }

    @POST
    @Path("/register")
    @Produces("text/html")
    public String registerPagePost(@FormParam("ask") String ask,
                                   @FormParam("login") String login,
                                   @FormParam("password") String password,
                                   @Context HttpServletRequest request,
                                   @Context HttpServletResponse response) {
        if (ask != null) {
            if (ask.equals("register")) {
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);

                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();

                Set<ConstraintViolation<User>> violations = validator.validate(user);
                if (violations.size() == 0) {
                    user.setPassword(EncryptionUtils.encryptPassword(user.getPassword()));
                    userDao.saveUser(user);
                    return this.connectPagePost("connection", login, password, request, response);
                } else {
                    for (ConstraintViolation<User> violation : violations) {
                        System.err.println(violation);
                    }
                    try {
                        response.sendRedirect("/error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return super.buildDefaultBootstrapView("Media Selector - Register", "user/register.ftl", super.getAuthenticatedUser(request));
            }
        } else {
            return super.buildDefaultBootstrapView("Media Selector - Register", "user/register.ftl", super.getAuthenticatedUser(request));
        }
        return null;
    }

    @GET
    @Path("/logout")
    @Produces("text/html")
    public String logoutPage(@Context HttpServletRequest request,
                             @Context HttpServletResponse response) {
        request.getSession().invalidate();
        try {
            response.sendRedirect("/home");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
