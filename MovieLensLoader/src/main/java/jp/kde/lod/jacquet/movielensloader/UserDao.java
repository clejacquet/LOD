package jp.kde.lod.jacquet.movielensloader;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Clement on 06/05/2015.
 */
public class UserDao {
    private EntityManager provider = EntityManagerUtils.getEntityManager();
    private MediaDao mediaDao = new MediaDao();

    public List<User> findAll() {
        provider.getTransaction().begin();
        String query = "from " + User.class.getName();
        List<User> users = provider.createQuery(query, User.class).getResultList();
        this.provider.getTransaction().commit();
        return users;
    }

    public User findByLogin(final String login) {
        provider.getTransaction().begin();
        StringBuilder query = new StringBuilder("from ");
        query.append(User.class.getName()).append(" as user");
        query.append(" where user.login = :login");

        List<User> resultList = this.provider.createQuery(query.toString(), User.class).setParameter("login", login).getResultList();
        this.provider.getTransaction().commit();

        if (resultList.size() > 0) {
            return (User) resultList.get(0);
        }
        return null;
    }

    public User getUser(long id) {
        provider.getTransaction().begin();
        StringBuilder query = new StringBuilder("from ");
        query.append(User.class.getName()).append(" as user");
        query.append(" where user.id = :id");

        List<User> resultList = this.provider.createQuery(query.toString(), User.class).setParameter("id", id).getResultList();
        this.provider.getTransaction().commit();

        if (resultList.size() > 0) {
            return (User) resultList.get(0);
        }
        return null;
    }

    public User logUser(String login, String password) {
        provider.getTransaction().begin();
        StringBuilder query = new StringBuilder("from ");
        query.append(User.class.getName()).append(" as user");
        query.append(" where user.login = :login");

        try {
            User user = this.provider.createQuery(query.toString(), User.class).setParameter("login", login).getSingleResult();
            String dec_password = EncryptionUtils.decryptPassword(user.getPassword());
            this.provider.getTransaction().commit();
            if (password.equals(dec_password)) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {

            this.provider.getTransaction().rollback();
            return null;
        }
    }

    public void saveUser(User user) {
        provider.getTransaction().begin();
        try {
            this.provider.persist(user);
            this.mediaDao.saveUser(user);
            this.provider.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            this.provider.getTransaction().rollback();
        }
    }

    public void removeUser(User user) {
        String login = user.getLogin();
        this.provider.getTransaction().begin();
        this.provider.remove(login);
        this.provider.getTransaction().commit();
    }
}
