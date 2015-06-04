package jp.kde.lod.jacquet.mediaselector.controller.command.html.home;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.Media;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import jp.kde.lod.jacquet.mediaselector.view.MediaView;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;
import jp.kde.lod.jacquet.pageprocessing.WebView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 16/05/2015.
 */
public class HomePageCommand extends SessionVisibleCommand {
    @Override
    public View process() {
        WebView webView = ViewFactory.buildBootstrapView("Media Selector - Home", "home.ftl");
        webView.addCss("/css/home/home.css");

        HttpSession session = super.getHandler().getRequest().getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();
                List<Long> mediasSubscribed = mediaDao.getSubscriptions(user.getId(), 4);
                List<MediaView> mediaViews = new ArrayList<>();

                for (Long mediaSubscribed : mediasSubscribed) {
                    Media media = mediaDao.getMedia(mediaSubscribed);
                    MediaView mediaView = new MediaView();
                    mediaView.setId(media.getId());
                    mediaView.setTitle(media.getName());
                    mediaView.setDescription(media.getDescription());
                    mediaViews.add(mediaView);
                }

                webView.addParameter("medias", mediaViews);
            }
        }

        return webView;
    }
}
