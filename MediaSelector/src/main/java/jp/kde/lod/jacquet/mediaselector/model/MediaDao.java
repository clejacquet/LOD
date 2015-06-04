package jp.kde.lod.jacquet.mediaselector.model;

import jp.kde.lod.jacquet.jenamedialite.itemrec.Prediction;
import jp.kde.lod.jacquet.mediaselector.model.domain.MainResource;
import jp.kde.lod.jacquet.mediaselector.model.domain.Media;
import jp.kde.lod.jacquet.mediaselector.controller.ServletSubject;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 12/05/2015.
 */
public interface MediaDao extends ServletSubject {
    long getMediaCounter();
    void setMediaCounter(long counter);

    void saveMedia(Media media);
    void deleteMedia(long mediaId);
    Media getMedia(long mediaId);

    void subscribeUserToMedia(long userId, long mediaId);
    void unsubscribeUserToMedia(long userId, long mediaId);
    boolean isSubscribed(long userId, long mediaId);
    Integer getUserSubscribedCount(long mediaId);
    List<Long> getSubscriptions(long userId);
    List<Long> getSubscriptions(long userId, int limit);

    MainResource getMainResource(long mediaId, String resourceURI);

    List<Media> searchMedia(String searchText);
    Map<Integer, Collection<MainResource>> searchResource(String searchText, String language, long mediaId);

    void saveUser(User user);
    String getUserUri(long userId);

    void rateMainResource(User user, long mediaId, String mainResourceUri);
    void unrateMainResource(User user, String mainResourceUri);
    boolean isMainResourceRated(User user, String mainResourceUri);

    int getLikeCount(String mainResourceUri);
    List<Pair<MainResource, Double>> getRecommendedResources(long mediaId, long userId);
    Prediction predictUserRating(long mediaId, long userId, String resourceUri);
}
