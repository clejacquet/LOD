package jp.kde.lod.jacquet.mediaselector.controller.command.home;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 16/05/2015.
 */
public class HomePageCommand extends SessionVisibleCommand {
    @Override
    public View process() {
        return ViewFactory.buildBootstrapView("Media Selector - Home", "home.ftl");
    }
}
