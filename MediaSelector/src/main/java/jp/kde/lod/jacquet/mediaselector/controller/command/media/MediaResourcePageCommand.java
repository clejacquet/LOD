package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaResourcePageCommand extends SessionVisibleCommand {
    private String media;
    private String resource;

    @Override
    public View process() {
        return ViewFactory.buildBootstrapView("Media Selector - " + this.media + "/" + this.resource, "media/resource.ftl");
    }

    public MediaResourcePageCommand setMedia(String media) {
        this.media = media;
        return this;
    }

    public MediaResourcePageCommand setResource(String resource) {
        this.resource = resource;
        return this;
    }
}
