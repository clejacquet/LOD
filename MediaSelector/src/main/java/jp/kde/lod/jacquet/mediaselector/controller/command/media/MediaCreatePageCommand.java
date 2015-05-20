package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.controller.command.AuthenticatedCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.LayoutWebView;
import jp.kde.lod.jacquet.pageprocessing.View;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaCreatePageCommand extends AuthenticatedCommand {
    @Override
    public View process() {
        LayoutWebView view = ViewFactory.buildBootstrapView("Media Selector - Create", "media/create.ftl");
        view.addCss("/css/media/create/create.css");
        view.addJs("/js/media/create/resource-button-action.js");
        return view;
    }
}
