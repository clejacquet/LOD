package jp.kde.lod.jacquet.mediaselector.persistence.dao.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import jp.kde.lod.jacquet.mediaselector.persistence.dao.UserDao;
import jp.kde.lod.jacquet.mediaselector.persistence.domain.User;
import jp.kde.lod.jacquet.mediaselector.persistence.domain.User_;
import org.apache.log4j.Logger;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Clement on 06/05/2015.
 */
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Inject
    private Provider<EntityManager> provider;

    @Override
    @Transactional
    public List<User> findAll() {
        String query = "from " + User.class.getName();
        List<User> users = provider.get().createQuery(query).getResultList();
        LOGGER.debug("End of users search");
        return users;
    }

    @Override
    @Transactional
    public User findByLogin(final String login) {
        StringBuilder query = new StringBuilder("from ");
        query.append(User.class.getName()).append(" as user");
        query.append(" where user.").append(User_.login.getName()).append(" = :login");

        List<User> resultList = this.provider.get().createQuery(query.toString()).setParameter("name", login).getResultList();

        if (resultList.size() > 0) {
            LOGGER.debug("User with login '" + login + "' found");
            return (User) resultList.get(0);
        }
        LOGGER.debug("No user with login '" + login + "' found");
        return null;
    }

    @Override
    @Transactional
    public User logUser(String login, String password) {
        StringBuilder query = new StringBuilder("from ");
        query.append(User.class.getName()).append(" as user");
        query.append(" where user.").append(User_.login.getName()).append(" = :login").append(" and");
        query.append(" user.").append(User_.password.getName()).append(" = :password");

        List<User> resultList = this.provider.get().createQuery(query.toString()).setParameter("login", login).setParameter("password", password).getResultList();

        if (resultList.size() > 0) {
            LOGGER.debug("User  with name '" + login + "' found");
            return (User) resultList.get(0);
        }
        LOGGER.debug("No user with name '" + login + "' found");
        return null;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        this.provider.get().persist(user);
        LOGGER.debug("User '"+ user.getLogin() + "' saved");
    }

    @Override
    public void removeUser(User user) {
        String login = user.getLogin();
        this.provider.get().getTransaction().begin();
        this.provider.get().remove(login);
        this.provider.get().getTransaction().commit();
        LOGGER.debug("Room '" + login + "' deleted");
    }
}
