package jp.kde.lod.jacquet.mediaselector.controller.command.media;

import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.Media;
import jp.kde.lod.jacquet.mediaselector.controller.command.AuthentificatedCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;
import java.io.IOException;

/**
 * Created by Clement on 17/05/2015.
 */
public class MediaSuccessPageCommand extends AuthentificatedCommand {
    @Override
    public View process() {
        MediaDao mediaDao = super.getHandler().getDaoProvider().getMediaDao();

        String mediaId = super.getHandler().getRequest().getParameter("media-id");
        Media media = mediaDao.getMedia(Long.parseLong(mediaId));

        if (media != null) {
            View view = ViewFactory.buildBootstrapView("Media Selector - Create Success", "media/create_success.ftl");
            view.addParameter("media", media);
            return view;
        }

        try {
            super.getHandler().getResponse().sendRedirect("/error");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ViewFactory.buildEmptyView();
    }
}
