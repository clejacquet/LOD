package jp.kde.lod.jacquet.mediaselector.model.dao;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.model.DaoProvider;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.UserDao;

/**
 * Created by Clement on 17/05/2015.
 */
public class DefaultDaoProvider implements DaoProvider {
    @Inject
    private MediaDao mediaDao;

    @Inject
    private UserDao userDao;

    @Override
    public MediaDao getMediaDao() {
        return this.mediaDao;
    }

    @Override
    public UserDao getUserDao() {
        return this.userDao;
    }
}
