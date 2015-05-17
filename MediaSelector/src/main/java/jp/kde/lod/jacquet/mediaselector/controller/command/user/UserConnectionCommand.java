package jp.kde.lod.jacquet.mediaselector.controller.command.user;

import jp.kde.lod.jacquet.mediaselector.model.UserDao;
import jp.kde.lod.jacquet.mediaselector.model.entity.User;
import jp.kde.lod.jacquet.mediaselector.controller.CommandFactory;
import jp.kde.lod.jacquet.mediaselector.controller.command.UnauthenticatedCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Clement on 16/05/2015.
 */
public class UserConnectionCommand extends UnauthenticatedCommand {
    @Override
    public View process() {
        UserDao userDao = super.getHandler().getDaoProvider().getUserDao();

        String ask = super.getHandler().getRequest().getParameter("ask");
        String login = super.getHandler().getRequest().getParameter("login");
        String password = super.getHandler().getRequest().getParameter("password");

        if (ask != null) {
            if (ask.equals("connection") || ask.equals("register")) {

                User user = userDao.logUser(login, password);
                if (user == null) {
                    try {
                        super.getHandler().getResponse().sendRedirect("/error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    HttpSession session = super.getHandler().getRequest().getSession(true);
                    session.setAttribute("user", user);
                    try {
                        super.getHandler().getResponse().sendRedirect("/home");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return ViewFactory.buildEmptyView();
            }
        }
        return super.getHandler().processHTML(CommandFactory.buildHTMLCommand(UserConnectionPageCommand.class));
    }
}
