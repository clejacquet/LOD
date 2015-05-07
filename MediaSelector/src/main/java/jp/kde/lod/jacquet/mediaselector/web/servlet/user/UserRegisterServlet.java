package jp.kde.lod.jacquet.mediaselector.web.servlet.user;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import jp.kde.lod.jacquet.mediaselector.Context;
import jp.kde.lod.jacquet.mediaselector.persistence.dao.UserDao;
import jp.kde.lod.jacquet.mediaselector.persistence.domain.User;
import jp.kde.lod.jacquet.mediaselector.web.servlet.BaseServlet;
import jp.kde.lod.jacquet.mediaselector.web.util.EncryptionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Clement on 07/05/2015.
 */
@Singleton
public class UserRegisterServlet extends BaseServlet {

    @Inject
    private UserDao userDao;

    public UserRegisterServlet() {
        super("Media Selector - Register", "user/user-register.ftl");
    }

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String askParam = req.getParameter("ask");
        if (askParam != null) {
            if (askParam.equals("register")) {
                String login = req.getParameter("login");
                String password = EncryptionUtils.encryptPassword(req.getParameter("password"));
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);

                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();

                Set<ConstraintViolation<User>> violations = validator.validate(user);
                if (violations.size() == 0) {
                    userDao.saveUser(user);
                } else {
                    for (ConstraintViolation<User> violation : violations) {
                        System.err.println(violation);
                    }
                    resp.sendRedirect(Context.makeCompleteIRI("/error"));
                }
            }
        } else {
            super.process(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }
}
