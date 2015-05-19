package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.MainResource;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaEnvSearchCommand extends BaseServletSubject implements JSONCommand {
    private String mediaId;

    @Override
    public JSONObject process() {
        String searchText = super.getHandler().getRequest().getParameter("search-text");
        String language = super.getHandler().getRequest().getParameter("language");

        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();
        List<MainResource> mainResources = mediaDao.searchResource(searchText, language, Long.parseLong(this.mediaId));

        JSONObject resourcesJson = new JSONObject();

        JSONArray resourcesJsonArray = new JSONArray();
        for (MainResource mainResource : mainResources) {
            resourcesJsonArray.put(mainResource.toJSON());
        }
        resourcesJson.put("resources", resourcesJsonArray);

        return resourcesJson;
    }

    public MediaEnvSearchCommand setMediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }
}
