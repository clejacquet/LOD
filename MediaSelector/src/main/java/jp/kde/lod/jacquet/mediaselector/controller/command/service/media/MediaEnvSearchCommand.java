package jp.kde.lod.jacquet.mediaselector.controller.command.service.media;

import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.MainResource;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

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
        Map<Integer, Collection<MainResource>> mainResources = mediaDao.searchResource(searchText, language, Long.parseLong(this.mediaId));

        JSONObject resourcesJson = new JSONObject();

        User user = (User) super.getHandler().getRequest().getSession(false).getAttribute("user");
        if (user != null) {
            JSONArray resourcesJsonArray = new JSONArray();
            for (Map.Entry<Integer, Collection<MainResource>> subMainResources : mainResources.entrySet()) {
                for (MainResource mainResource : subMainResources.getValue()) {
                    JSONObject mainResourceJson = mainResource.toJSON();
                    mainResourceJson.put("liked", mediaDao.isMainResourceRated(user, mainResource.getUri()));
                    resourcesJsonArray.put(mainResourceJson);
                }
            }
            resourcesJson.put("resources", resourcesJsonArray);
            resourcesJson.put("mediaId", mediaId);
        }

        return resourcesJson;
    }

    public MediaEnvSearchCommand setMediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }
}
