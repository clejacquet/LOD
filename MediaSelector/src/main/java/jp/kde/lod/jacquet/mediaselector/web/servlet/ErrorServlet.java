package jp.kde.lod.jacquet.mediaselector.web.servlet;

import com.google.inject.Singleton;

/**
 * Created by Clement on 05/05/2015.
 */
@Singleton
public class ErrorServlet extends BaseServlet {
    public ErrorServlet() {
        super("Media Selector - Error", "error.ftl");
    }
}
