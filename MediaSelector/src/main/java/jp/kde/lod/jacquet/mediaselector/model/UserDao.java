package jp.kde.lod.jacquet.mediaselector.model;

import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import jp.kde.lod.jacquet.mediaselector.controller.ServletSubject;

import java.util.List;

/**
 * Created by Clement on 06/05/2015.
 */
public interface UserDao extends ServletSubject {
    List<User> findAll();
    User findByLogin(String login);
    User logUser(String login, String password);
    void saveUser(User user);
    void removeUser(User user);
}
