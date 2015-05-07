package jp.kde.lod.jacquet.mediaselector.web.servlet.user;

import com.google.inject.Singleton;
import jp.kde.lod.jacquet.mediaselector.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Clement on 07/05/2015.
 */
@Singleton
public class UserServlet extends BaseServlet {
    public UserServlet() {
        super("Media Selector - User", "user/user.ftl");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
