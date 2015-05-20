package jp.kde.lod.jacquet.mediaselector.controller.command.user;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;
import jp.kde.lod.jacquet.pageprocessing.WebView;

/**
 * Created by Clement on 16/05/2015.
 */
public class UserConnectionPageCommand extends SessionVisibleCommand {
    @Override
    public View process() {
        WebView view = ViewFactory.buildBootstrapView("Media Selector - User Connection", "user/connection.ftl");
        view.addCss("/css/user/connect/connect.css");
        return view;
    }
}
