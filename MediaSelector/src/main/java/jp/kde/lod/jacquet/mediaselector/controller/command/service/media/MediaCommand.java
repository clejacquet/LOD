package jp.kde.lod.jacquet.mediaselector.controller.command.service.media;

import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.Media;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Created by Clement on 31/05/2015.
 */
public class MediaCommand extends BaseServletSubject implements JSONCommand {
    private String media;

    @Override
    public JSONObject process() {
        JSONObject result = new JSONObject();

        HttpSession session = super.getHandler().getRequest().getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();
                String subscriberCountAsk = super.getHandler().getRequest().getParameter("subscriber_count");
                String isSubscribedAsk = super.getHandler().getRequest().getParameter("is_subscribed");
                String subscription = super.getHandler().getRequest().getParameter("subscription");
                String delete = super.getHandler().getRequest().getParameter("delete");

                if (delete != null) {
                    if (delete.equals("yes")) {
                        Media media = mediaDao.getMedia(Long.parseLong(this.media));
                        if (media != null) {
                            if (media.getAuthor().getId() == user.getId()) {
                                mediaDao.deleteMedia(media.getId());
                                result.put("deleteStatus", "done");
                            } else {
                                result.put("deleteStatus", "error");
                            }
                        } else {
                            result.put("deleteStatus", "error");
                        }
                    }
                }

                if (subscription != null) {
                    if (subscription.equals("do")) {
                        mediaDao.subscribeUserToMedia(user.getId(), Long.parseLong(this.media));
                        result.put("subscriptionStatus", "subscribed");
                    } else if (subscription.equals("undo")) {
                        mediaDao.unsubscribeUserToMedia(user.getId(), Long.parseLong(this.media));
                        result.put("subscriptionStatus", "unsubscribed");
                    }
                }

                if (subscriberCountAsk != null) {
                    if (subscriberCountAsk.equals("get")) {
                        int subscriberCount = mediaDao.getUserSubscribedCount(Long.parseLong(this.media));
                        result.put("subscriberCount", subscriberCount);
                    }
                }

                if (isSubscribedAsk != null) {
                    if (isSubscribedAsk.equals("get")) {
                        boolean isSubscribed = mediaDao.isSubscribed(user.getId(), Long.parseLong(this.media));
                        result.put("isSubscribed", isSubscribed);
                    }
                }
            }
        }

        return result;
    }

    public MediaCommand setMedia(String media) {
        this.media = media;
        return this;
    }
}
