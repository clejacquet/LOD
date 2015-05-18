package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import org.json.JSONObject;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaEnvSearchCommand extends BaseServletSubject implements JSONCommand {
    private String media;

    @Override
    public JSONObject process() {
        String searchText = super.getHandler().getRequest().getParameter("search-text");

        return new JSONObject();
    }

    public MediaEnvSearchCommand setMedia(String media) {
        this.media = media;
        return this;
    }
}
