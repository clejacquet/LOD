package jp.kde.lod.jacquet.mediaselector.controller.command.html.user;

import jp.kde.lod.jacquet.mediaselector.controller.CommandFactory;
import jp.kde.lod.jacquet.mediaselector.controller.command.AuthenticatedCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 17/05/2015.
 */
public class UserPersoPage extends AuthenticatedCommand {
    @Override
    public View process() {
        User user = (User) super.getHandler().getRequest().getSession(false).getAttribute("user");
        return super.getHandler().processHTML(CommandFactory.buildHTMLCommand(UserPageCommand.class)
                .setUser(Long.toString(user.getId())));
    }

    @Override
    public String getRedirectionURL() {
        return "/user";
    }
}
