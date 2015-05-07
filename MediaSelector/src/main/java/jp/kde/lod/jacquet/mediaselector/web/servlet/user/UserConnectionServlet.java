package jp.kde.lod.jacquet.mediaselector.web.servlet.user;

import com.google.inject.Singleton;
import jp.kde.lod.jacquet.mediaselector.web.servlet.BaseServlet;

/**
 * Created by Clement on 07/05/2015.
 */
@Singleton
public class UserConnectionServlet extends BaseServlet {
    public UserConnectionServlet() {
        super("Media Selector - User Connection", "user/user-connection.ftl");
    }
}
