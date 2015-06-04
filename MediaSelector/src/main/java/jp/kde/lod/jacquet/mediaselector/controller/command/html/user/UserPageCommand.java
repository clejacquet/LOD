package jp.kde.lod.jacquet.mediaselector.controller.command.html.user;

import jp.kde.lod.jacquet.mediaselector.controller.command.AuthenticatedCommand;
import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.Media;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import jp.kde.lod.jacquet.mediaselector.view.MediaView;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;
import jp.kde.lod.jacquet.pageprocessing.WebView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 16/05/2015.
 */
public class UserPageCommand extends AuthenticatedCommand {
    private String user;

    @Override
    public View process() {
        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();

        User targetUser = super.getHandler().getDaoProvider().getUserDao().getUser(Long.parseLong(this.user));

        List<MediaView> subscriptionViews = new ArrayList<>();
        Map<Media, Integer> subscriptions = mediaDao.getSubscriptionsWithCount(Long.parseLong(this.user));
        for (Map.Entry<Media, Integer> mediaSubscribedEntry : subscriptions.entrySet()) {
            Media mediaSubscribed = mediaSubscribedEntry.getKey();

            MediaView mediaView = new MediaView();
            mediaView.setId(mediaSubscribed.getId());
            mediaView.setTitle(mediaSubscribed.getName());
            mediaView.setDescription(mediaSubscribed.getDescription());
            mediaView.setSubscribed(true);
            mediaView.setSubscribedCount(mediaSubscribedEntry.getValue());
            mediaView.setAuthor(mediaSubscribed.getAuthor().getLogin());
            subscriptionViews.add(mediaView);
        }

        List<MediaView> ownedMediaViews = new ArrayList<>();
        Map<Media, Integer> ownedMedias = mediaDao.getOwnedMedias(Long.parseLong(this.user));
        for (Map.Entry<Media, Integer> ownedMediaEntry : ownedMedias.entrySet()) {
            Media ownedMedia = ownedMediaEntry.getKey();

            MediaView mediaView = new MediaView();
            mediaView.setId(ownedMedia.getId());
            mediaView.setTitle(ownedMedia.getName());
            mediaView.setDescription(ownedMedia.getDescription());
            mediaView.setSubscribed(true);
            mediaView.setSubscribedCount(ownedMediaEntry.getValue());
            mediaView.setAuthor(ownedMedia.getAuthor().getLogin());
            ownedMediaViews.add(mediaView);
        }

        WebView view = ViewFactory.buildBootstrapView("Media Selector - User " + this.user, "user/user.ftl");
        view.addJs("/js/user/user.js");
        view.addParameter("subscriptions", subscriptionViews);
        view.addParameter("medias", ownedMediaViews);
        view.addParameter("user_target", targetUser);
        return view;
    }

    public UserPageCommand setUser(String user) {
        this.user = user;
        return this;
    }

    @Override
    public String getRedirectionURL() {
        return "/user/" + this.user;
    }
}
