package jp.kde.lod.jacquet.mediaselector.persistence.dao;

import jp.kde.lod.jacquet.mediaselector.rdf.Media;

/**
 * Created by Clement on 12/05/2015.
 */
public interface MediaDao {
    long getMediaCounter();
    void setMediaCounter(long counter);
    long saveMedia(Media media);
    Media getMedia(long mediaId);
    Media getMedia(String mediaId);
}
