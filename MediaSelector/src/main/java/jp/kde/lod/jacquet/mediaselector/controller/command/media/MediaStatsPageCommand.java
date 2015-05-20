package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaStatsPageCommand extends SessionVisibleCommand {
    private String media;

    @Override
    public View process() {
        return ViewFactory.buildBootstrapView("Media Selector - " + this.media, "media/stats.ftl");
    }

    public MediaStatsPageCommand setMedia(String media) {
        this.media = media;
        return this;
    }
}
