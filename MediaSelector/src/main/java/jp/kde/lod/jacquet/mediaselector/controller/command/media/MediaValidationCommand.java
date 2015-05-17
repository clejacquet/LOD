package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.rdf.Media;
import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.HttpSession;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaValidationCommand extends BaseServletSubject implements JSONCommand {
    @Override
    public JSONObject process() {
        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();

        HttpSession session = super.getHandler().getRequest().getSession(false);
        if (session != null) {
            String mediaJsonStr = super.getHandler().getRequest().getParameter("media");

            JSONObject mediaJson = new JSONObject(new JSONTokener(mediaJsonStr));
            Media media = new Media();
            media.loadJson(mediaJson);

            long counter = mediaDao.getMediaCounter() + 1L;
            mediaDao.setMediaCounter(counter);
            media.setId(counter);

            mediaDao.saveMedia(media);

            JSONObject response = new JSONObject();
            response.append("status", "success");
            response.append("redirect", "/media/" + media.getId());
            return response;
        } else {
            JSONObject response = new JSONObject();
            response.append("status", "fail");
            response.append("redirect", "/");
            return response;
        }
    }
}
