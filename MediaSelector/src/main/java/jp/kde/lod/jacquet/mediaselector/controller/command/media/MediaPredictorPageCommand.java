package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.controller.command.AuthentificatedCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaPredictorPageCommand extends AuthentificatedCommand {
    private String media;
    private String resource;

    @Override
    public View process() {
        return ViewFactory.buildBootstrapView("Media Selector - " + media + "/" + resource, "media/predictor.ftl");
    }

    public MediaPredictorPageCommand setMedia(String media) {
        this.media = media;
        return this;
    }

    public MediaPredictorPageCommand setResource(String resource) {
        this.resource = resource;
        return this;
    }
}
