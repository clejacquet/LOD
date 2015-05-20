package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;
import jp.kde.lod.jacquet.pageprocessing.WebView;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaEnvSearchPageCommand extends SessionVisibleCommand {
    private String media;

    @Override
    public View process() {
        WebView view = ViewFactory.buildBootstrapView("Media Selector - " + this.media, "media/media-resource-search.ftl");
        view.addJs("/js/media/resource/search.js");
        return view;
    }

    public MediaEnvSearchPageCommand setMedia(String media) {
        this.media = media;
        return this;
    }
}
