package jp.kde.lod.jacquet.mediaselector.persistence;

import com.google.inject.AbstractModule;
import jp.kde.lod.jacquet.mediaselector.persistence.dao.MediaDao;
import jp.kde.lod.jacquet.mediaselector.persistence.dao.UserDao;
import jp.kde.lod.jacquet.mediaselector.persistence.dao.impl.MediaDaoImpl;
import jp.kde.lod.jacquet.mediaselector.persistence.dao.impl.UserDaoImpl;
import org.apache.log4j.Logger;

/**
 * Created by fred on 08/03/15.
 */
public class PersistenceModule extends AbstractModule {
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(PersistenceModule.class);

    @Override
    protected void configure() {
        LOGGER.info("CoreModule configuration started...");
        LOGGER.info("   bind UserDao on UserDaoImpl");
        bind(UserDao.class).to(UserDaoImpl.class);
        bind(MediaDao.class).to(MediaDaoImpl.class);
        LOGGER.info("CoreModule configuration ended.");
    }
}
