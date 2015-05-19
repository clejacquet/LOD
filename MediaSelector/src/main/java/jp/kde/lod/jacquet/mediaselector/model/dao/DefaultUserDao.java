package jp.kde.lod.jacquet.mediaselector.model.dao;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import jp.kde.lod.jacquet.mediaselector.model.UserDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import jp.kde.lod.jacquet.mediaselector.model.domain.User_;
import jp.kde.lod.jacquet.mediaselector.util.EncryptionUtils;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import org.apache.log4j.Logger;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Clement on 06/05/2015.
 */
public class DefaultUserDao extends BaseServletSubject implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(DefaultUserDao.class);

    @Inject
    private Provider<EntityManager> provider;

    @Override
    @Transactional
    public List<User> findAll() {
        String query = "from " + User.class.getName();
        List<User> users = provider.get().createQuery(query, User.class).getResultList();
        LOGGER.debug("End of users search");
        return users;
    }

    @Override
    @Transactional
    public User findByLogin(final String login) {
        StringBuilder query = new StringBuilder("from ");
        query.append(User.class.getName()).append(" as user");
        query.append(" where user.").append(User_.login.getName()).append(" = :login");

        List<User> resultList = this.provider.get().createQuery(query.toString(), User.class).setParameter("login", login).getResultList();

        if (resultList.size() > 0) {
            LOGGER.debug("User with login '" + login + "' found");
            return (User) resultList.get(0);
        }
        LOGGER.debug("No entity with login '" + login + "' found");
        return null;
    }

    @Override
    public User getUser(long id) {
        StringBuilder query = new StringBuilder("from ");
        query.append(User.class.getName()).append(" as user");
        query.append(" where user.").append(User_.id.getName()).append(" = :id");

        List<User> resultList = this.provider.get().createQuery(query.toString(), User.class).setParameter("id", id).getResultList();

        if (resultList.size() > 0) {
            LOGGER.debug("User with id '" + id + "' found");
            return (User) resultList.get(0);
        }
        LOGGER.debug("No entity with id '" + id + "' found");
        return null;
    }

    @Override
    @Transactional
    public User logUser(String login, String password) {
        StringBuilder query = new StringBuilder("from ");
        query.append(User.class.getName()).append(" as user");
        query.append(" where user.").append(User_.login.getName()).append(" = :login");

        try {
            User user = this.provider.get().createQuery(query.toString(), User.class).setParameter("login", login).getSingleResult();
            LOGGER.debug("User  with name '" + login + "' found");
            String dec_password = EncryptionUtils.decryptPassword(user.getPassword());
            if (password.equals(dec_password)) {
                LOGGER.debug("Password valid");
                return user;
            } else {
                LOGGER.debug("Password invalid");
                return null;
            }
        } catch (Exception e) {
            LOGGER.debug("No entity with name '" + login + "' found");
            return null;
        }
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        try {
            this.provider.get().persist(user);
            super.getHandler().getDaoProvider().getMediaDao().saveUser(user);
            LOGGER.debug("User '"+ user.getLogin() + "' saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(User user) {
        String login = user.getLogin();
        this.provider.get().getTransaction().begin();
        this.provider.get().remove(login);
        this.provider.get().getTransaction().commit();
        LOGGER.debug("User '" + login + "' deleted");
    }
}
