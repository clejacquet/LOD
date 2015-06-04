package jp.kde.lod.jacquet.mediaselector.controller.command.html.user;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 16/05/2015.
 */
public class UserPageCommand extends SessionVisibleCommand {
    private String user;

    @Override
    public View process() {
        return ViewFactory.buildBootstrapView("Media Selector - User " + this.user, "user/user.ftl");
    }

    public UserPageCommand setUser(String user) {
        this.user = user;
        return this;
    }
}
