package jp.kde.lod.jacquet.mediaselector.controller.command.html.user;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 17/05/2015.
 */
public class UserPersoPage extends SessionVisibleCommand {
    @Override
    public View process() {
        return ViewFactory.buildBootstrapView("Media Selector - User", "user/user.ftl");
    }
}
