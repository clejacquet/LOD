package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.rdf.Media;
import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaSearchCommand extends BaseServletSubject implements JSONCommand {
    @Override
    public JSONObject process() {
        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();

        String searchText = super.getHandler().getRequest().getParameter("search-text");

        List<Media> medias = mediaDao.searchMedia(searchText);
        JSONObject object = new JSONObject();
        JSONArray mediaArray = new JSONArray();

        object.append("medias", mediaArray);

        return new JSONObject();
    }
}
