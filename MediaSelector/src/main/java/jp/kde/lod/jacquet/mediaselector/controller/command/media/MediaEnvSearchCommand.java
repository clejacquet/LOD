package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.Media;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaEnvSearchCommand extends BaseServletSubject implements JSONCommand {
    private String media;

    @Override
    public JSONObject process() {
        String searchText = super.getHandler().getRequest().getParameter("search-text");

        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();
        List<Media> medias = mediaDao.searchMedia(searchText);

        JSONObject mediasJson = new JSONObject();

        JSONArray mediasJsonArray = new JSONArray();
        for (Media media : medias) {
            mediasJsonArray.put(media.toJSON());
        }
        mediasJson.put("medias", mediasJsonArray);

        return mediasJson;
    }

    public MediaEnvSearchCommand setMedia(String media) {
        this.media = media;
        return this;
    }
}
