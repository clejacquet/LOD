package jp.kde.lod.jacquet.mediaselector.controller.command.service.media;

import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.MainResource;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Clement on 25/05/2015.
 */
public class MediaSelectorCommand extends BaseServletSubject implements JSONCommand {
    private String mediaId;

    @Override
    public JSONObject process() {
        HttpSession session = super.getHandler().getRequest().getSession(false);
        if (session != null) {
            MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();
            User user = (User) session.getAttribute("user");
            JSONObject result = new JSONObject();

            List<Pair<MainResource, Double>> recommendedItems = super.getHandler().getDaoProvider().getMediaDao().getRecommendedResources(Long.parseLong(mediaId), user.getId());

            result.put("mediaId", this.mediaId);

            JSONArray array = new JSONArray();
            for (Pair<MainResource, Double> recommendedItem : recommendedItems) {
                JSONObject mainResourceJson = recommendedItem.getKey().toJSON();
                mainResourceJson.put("liked", mediaDao.isMainResourceRated(user, recommendedItem.getKey().getUri()));
                array.put(mainResourceJson);
            }

            result.put("resources", array);

            return result;
        }

        return new JSONObject();
    }

    public MediaSelectorCommand setMedia(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }
}
