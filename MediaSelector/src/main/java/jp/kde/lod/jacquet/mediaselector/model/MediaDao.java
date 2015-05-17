package jp.kde.lod.jacquet.mediaselector.model;

import jp.kde.lod.jacquet.mediaselector.model.rdf.Media;
import jp.kde.lod.jacquet.mediaselector.model.rdf.Resource;
import jp.kde.lod.jacquet.mediaselector.controller.ServletSubject;

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
    List<Resource> searchResource(String searchText, long mediaId);
}
