package jp.kde.lod.jacquet.mediaselector.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import jp.kde.lod.jacquet.mediaselector.model.DaoProvider;
import jp.kde.lod.jacquet.mediaselector.model.dao.DefaultDaoProvider;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.UserDao;
import jp.kde.lod.jacquet.mediaselector.model.dao.DefaultMediaDao;
import jp.kde.lod.jacquet.mediaselector.model.dao.DefaultUserDao;
import jp.kde.lod.jacquet.mediaselector.controller.servlethandler.DefaultServletHandler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Clement on 16/05/2015.
 */
public final class WebContext {
    private WebContext() {

    }

    private static final String SESSION_USER_KEY = "entity";

    private static Injector INJECTOR = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            super.bind(MediaDao.class).to(DefaultMediaDao.class);
            super.bind(UserDao.class).to(DefaultUserDao.class);
            super.bind(DaoProvider.class).to(DefaultDaoProvider.class);

            super.install(new ServletModule() {
                @Override
                protected void configureServlets() {
                    super.configureServlets();

                    super.install(new JpaPersistModule("mediaselector"));
                    super.filter("/*").through(PersistFilter.class);
                }
            });
        }
    });

    public static DefaultServletHandler getServletHandler(ServletContext context,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) {
        DefaultServletHandler handler = INJECTOR.getInstance(DefaultServletHandler.class);
        handler.setContext(context);
        handler.setRequest(request);
        handler.setResponse(response);
        handler.initDaoProvider();
        return handler;
    }

    public static String getSessionUserKey() {
        return SESSION_USER_KEY;
    }

    public static Injector getInjector() {
        return INJECTOR;
    }
}
