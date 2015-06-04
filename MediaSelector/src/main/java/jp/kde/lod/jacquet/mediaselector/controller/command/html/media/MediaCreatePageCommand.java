package jp.kde.lod.jacquet.mediaselector.controller.command.html.media;

import jp.kde.lod.jacquet.mediaselector.controller.command.AuthenticatedCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.LayoutWebView;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaCreatePageCommand extends AuthenticatedCommand {
    @Override
    public View process() {
        LayoutWebView view = ViewFactory.buildBootstrapView("Media Selector - Create", "media/create.ftl");
        view.addCss("/css/media/create/create.css");
        view.addJs("/js/media/create.js");
        return view;
    }

    @Override
    public String getRedirectionURL() {
        return "/media/create";
    }
}
