package jp.kde.lod.jacquet.mediaselector.controller.command.service.media;

import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.Media;
import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.HttpSession;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaCreateCommand extends BaseServletSubject implements JSONCommand {
    @Override
    public JSONObject process() {
        HttpSession session = super.getHandler().getRequest().getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("user");
            return this.createMedia(user);
        } else {
            String userId = super.getHandler().getRequest().getParameter("user");
            if (userId != null) {
                user = super.getHandler().getDaoProvider().getUserDao().getUser(Long.parseLong(userId));
                return createMedia(user);
            }

            JSONObject response = new JSONObject();
            response.append("status", "fail");
            response.append("redirect", "/");
            return response;
        }
    }

    private JSONObject createMedia(User user) {
        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();
        String mediaJsonStr = super.getHandler().getRequest().getParameter("media");

        JSONObject mediaJson = new JSONObject(new JSONTokener(mediaJsonStr));
        Media media = new Media();
        media.loadJSON(mediaJson);

        long counter = mediaDao.getMediaCounter() + 1L;
        mediaDao.setMediaCounter(counter);
        media.setId(counter);

        media.setAuthor(user);

        mediaDao.saveMedia(media);

        JSONObject response = new JSONObject();
        response.append("status", "success");
        response.append("redirect", "/media/" + media.getId());
        return response;
    }
}
