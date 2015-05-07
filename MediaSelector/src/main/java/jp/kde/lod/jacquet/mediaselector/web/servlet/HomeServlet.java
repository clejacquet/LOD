package jp.kde.lod.jacquet.mediaselector.web.servlet;

import com.google.inject.Singleton;

/**
 * Created by Clement on 03/05/2015.
 */
@Singleton
public class HomeServlet extends BaseServlet {
    public HomeServlet() {
        super("Media Selector - Home", "home.ftl");
    }
}
