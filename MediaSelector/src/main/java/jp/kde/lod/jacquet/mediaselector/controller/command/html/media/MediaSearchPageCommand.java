package jp.kde.lod.jacquet.mediaselector.controller.command.html.media;

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
 * Created by Clement on 17/05/2015.
 */
public class MediaSearchPageCommand extends AuthenticatedCommand {
    @Override
    public View process() {
        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();
        User user = (User) super.getHandler().getRequest().getSession(false).getAttribute("user");
        Map<Media, Integer> mediasSubscribed = mediaDao.getSubscriptionsWithCount(user.getId());
        List<MediaView> mediaViews = new ArrayList<>();

        for (Map.Entry<Media, Integer> mediaSubscribedEntry : mediasSubscribed.entrySet()) {
            Media mediaSubscribed = mediaSubscribedEntry.getKey();

            MediaView mediaView = new MediaView();
            mediaView.setId(mediaSubscribed.getId());
            mediaView.setTitle(mediaSubscribed.getName());
            mediaView.setDescription(mediaSubscribed.getDescription());
            mediaView.setSubscribed(true);
            mediaView.setSubscribedCount(mediaSubscribedEntry.getValue());
            mediaView.setAuthor(mediaSubscribed.getAuthor().getLogin());
            mediaViews.add(mediaView);
        }

        WebView view = ViewFactory.buildBootstrapView("Media Selector - Media Search", "media/search.ftl");
        view.addJs("/js/media/search.js");
        view.addParameter("medias", mediaViews);
        return view;
    }

    @Override
    public String getRedirectionURL() {
        return "/media/search";
    }
}
