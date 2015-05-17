package jp.kde.lod.jacquet.mediaselector.controller.command.user;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.model.UserDao;
import jp.kde.lod.jacquet.mediaselector.model.entity.User;
import jp.kde.lod.jacquet.mediaselector.util.EncryptionUtils;
import jp.kde.lod.jacquet.mediaselector.controller.CommandFactory;
import jp.kde.lod.jacquet.mediaselector.controller.command.UnauthenticatedCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Clement on 17/05/2015.
 */
public class UserRegisterCommand extends UnauthenticatedCommand {
    @Inject
    private UserDao userDao;

    @Override
    public View process() {
        String ask = super.getHandler().getRequest().getParameter("ask");
        String login = super.getHandler().getRequest().getParameter("login");
        String password = super.getHandler().getRequest().getParameter("password");

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
                    return super.getHandler().processHTML(CommandFactory.buildHTMLCommand(UserConnectionCommand.class));
                } else {
                    for (ConstraintViolation<User> violation : violations) {
                        System.err.println(violation);
                    }
                    try {
                        super.getHandler().getResponse().sendRedirect("/error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return ViewFactory.buildBootstrapView("Media Selector - Register", "entity/register.ftl");
            }
        } else {
            return ViewFactory.buildBootstrapView("Media Selector - Register", "entity/register.ftl");
        }
        return ViewFactory.buildEmptyView();
    }
}
