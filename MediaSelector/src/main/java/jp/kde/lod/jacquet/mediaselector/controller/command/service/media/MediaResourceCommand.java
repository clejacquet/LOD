package jp.kde.lod.jacquet.mediaselector.controller.command.service.media;

import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.jenamedialite.itemrec.Prediction;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;

/**
 * Created by Clement on 27/05/2015.
 */
public class MediaResourceCommand extends BaseServletSubject implements JSONCommand {
    private String media;

    @Override
    public JSONObject process() {
        HttpSession session = super.getHandler().getRequest().getSession(false);
        User user = null;

        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (user != null) {
            String prediction = super.getHandler().getRequest().getParameter("prediction");
            if (prediction != null) {
                if (prediction.equals("true")) {
                    return getPrediction(user);
                }
            }
            return sendFeedback(user);
        }

        JSONObject errorResult = new JSONObject();
        errorResult.put("status", "error");
        return errorResult;
    }

    private JSONObject getPrediction(User user) {
        String resourceUri = super.getHandler().getRequest().getParameter("resource_uri");
        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();
        Prediction prediction = mediaDao.predictUserRating(Long.parseLong(this.media), user.getId(), resourceUri);

        JSONObject predictionResult = new JSONObject();
        predictionResult.put("prediction", prediction.getName());

        return predictionResult;
    }

    private JSONObject sendFeedback(User user) {
        String feedback = super.getHandler().getRequest().getParameter("feedback");
        String resourceUri = super.getHandler().getRequest().getParameter("resource_uri");
        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();

        JSONObject object = new JSONObject();
        if (feedback.equals("like")) {
            mediaDao.rateMainResource(user, Long.parseLong(this.media), resourceUri);
        } else {
            mediaDao.unrateMainResource(user, resourceUri);
        }

        if (mediaDao.isMainResourceRated(user, resourceUri)) {
            object.put("status", "like");
        } else {
            object.put("status", "dislike");
        }

        object.put("likeCount", mediaDao.getLikeCount(resourceUri));
        return object;
    }

    public MediaResourceCommand setMedia(String media) {
        this.media = media;
        return this;
    }
}
