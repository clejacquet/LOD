package jp.kde.lod.jacquet.mediaselector.model;

/**
 * Created by Clement on 17/05/2015.
 */
public interface DaoProvider {
    MediaDao getMediaDao();
    UserDao getUserDao();
}
