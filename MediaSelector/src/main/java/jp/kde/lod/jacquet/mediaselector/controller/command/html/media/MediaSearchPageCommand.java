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

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaSearchPageCommand extends AuthenticatedCommand {
    @Override
    public View process() {
        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();
        User user = (User) super.getHandler().getRequest().getSession(false).getAttribute("user");
        List<Long> mediasSubscribed = mediaDao.getSubscriptions(user.getId());
        List<MediaView> mediaViews = new ArrayList<>();

        for (Long mediaSubscribed : mediasSubscribed) {
            Media media = mediaDao.getMedia(mediaSubscribed);
            MediaView mediaView = new MediaView();
            mediaView.setId(media.getId());
            mediaView.setTitle(media.getName());
            mediaView.setDescription(media.getDescription());
            mediaView.setSubscribed(mediaDao.isSubscribed(user.getId(), mediaSubscribed));
            mediaView.setSubscribedCount(mediaDao.getUserSubscribedCount(mediaSubscribed));
            mediaView.setAuthor(media.getAuthor().getLogin());
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
