package jp.kde.lod.jacquet.mediaselector.controller.command.user;

import jp.kde.lod.jacquet.mediaselector.controller.command.AuthentificatedCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 17/05/2015.
 */
public class UserRegisterPageCommand extends AuthentificatedCommand {
    @Override
    public View process() {
        return ViewFactory.buildBootstrapView("Media Selector - Register", "entity/register.ftl");
    }
}