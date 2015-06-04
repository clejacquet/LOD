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

import javax.servlet.http.HttpSession;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaPageCommand extends AuthenticatedCommand {
    private String media;

    @Override
    public View process() {
        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();

        HttpSession session = super.getHandler().getRequest().getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            long mediaId = Long.parseLong(this.media);

            Media media = mediaDao.getMedia(mediaId);
            MediaView mediaView = new MediaView();
            mediaView.setId(media.getId());
            mediaView.setTitle(media.getName());
            mediaView.setDescription(media.getDescription());
            mediaView.setSubscribed(mediaDao.isSubscribed(user.getId(), mediaId));
            mediaView.setSubscribedCount(mediaDao.getUserSubscribedCount(mediaId));
            mediaView.setAuthor(media.getAuthor().getLogin());
            mediaView.setAuthorId(media.getAuthor().getId());

            boolean admin = user.getId() == media.getAuthor().getId();

            WebView view = ViewFactory.buildBootstrapView("Media Selector - " + this.media, "media/home.ftl");
            view.addJs("/js/media/media.js");
            view.addJs("/js/media/rate.js");
            view.addJs("/js/media/search-resource.js");
            view.addJs("/js/media/selector.js");
            view.addParameter("media", mediaView);
            view.addParameter("admin", admin);
            return view;
        }

        return ViewFactory.buildEmptyView();
    }

    public MediaPageCommand setMedia(String media) {
        this.media = media;
        return this;
    }

    @Override
    public String getRedirectionURL() {
        return "/media/" + this.media;
    }
}
