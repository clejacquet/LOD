package jp.kde.lod.jacquet.mediaselector.model;

import jp.kde.lod.jacquet.mediaselector.model.domain.MainResource;
import jp.kde.lod.jacquet.mediaselector.model.domain.Media;
import jp.kde.lod.jacquet.mediaselector.controller.ServletSubject;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;

import java.util.List;

/**
 * Created by Clement on 12/05/2015.
 */
public interface MediaDao extends ServletSubject {
    long getMediaCounter();
    void setMediaCounter(long counter);

    void saveMedia(Media media);
    Media getMedia(long mediaId);

    List<Media> searchMedia(String searchText);
    List<MainResource> searchResource(String searchText, String language, long mediaId);

    void saveUser(User user);
}
