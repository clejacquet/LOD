package jp.kde.lod.jacquet.mediaselector.persistence.dao;

import jp.kde.lod.jacquet.mediaselector.persistence.domain.User;

import java.util.List;

/**
 * Created by Clement on 06/05/2015.
 */
public interface UserDao {
    List<User> findAll();
    User findByLogin(String login);
    User logUser(String login, String password);
    void saveUser(User user);
    void removeUser(User user);
}
