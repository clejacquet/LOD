package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.controller.command.AuthentificatedCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;
import jp.kde.lod.jacquet.pageprocessing.WebView;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaSearchPageCommand extends AuthentificatedCommand {
    @Override
    public View process() {
        WebView view = ViewFactory.buildBootstrapView("Media Selector - Media Search", "media/search.ftl");
        view.addJs("/js/media/search.js");
        return view;
    }
}
