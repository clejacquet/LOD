package jp.kde.lod.jacquet.mediaselector.controller.command.user;

import jp.kde.lod.jacquet.mediaselector.controller.command.AuthentificatedCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 16/05/2015.
 */
public class UserPageCommand extends AuthentificatedCommand {
    private String user;

    @Override
    public View process() {
        return ViewFactory.buildBootstrapView("Media Selector - User " + this.user, "entity/entity.ftl");
    }

    public UserPageCommand setUser(String user) {
        this.user = user;
        return this;
    }
}