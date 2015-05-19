package jp.kde.lod.jacquet.mediaselector.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import jp.kde.lod.jacquet.mediaselector.model.*;
import jp.kde.lod.jacquet.mediaselector.model.dao.DefaultDaoProvider;
import jp.kde.lod.jacquet.mediaselector.model.dao.DefaultMediaDao;
import jp.kde.lod.jacquet.mediaselector.model.dao.DefaultUserDao;
import jp.kde.lod.jacquet.mediaselector.controller.servlethandler.DefaultServletHandler;
import jp.kde.lod.jacquet.mediaselector.model.storage.DefaultQueryStorage;
import jp.kde.lod.jacquet.mediaselector.model.storage.DefaultUpdateStorage;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Clement on 16/05/2015.
 */
public final class WebContext {
    private WebContext() {

    }

    private static final String SESSION_USER_KEY = "user";

    private static Injector INJECTOR = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            super.bind(MediaDao.class).to(DefaultMediaDao.class);
            super.bind(UserDao.class).to(DefaultUserDao.class);
            super.bind(DaoProvider.class).to(DefaultDaoProvider.class);
            super.bind(QueryStorage.class).to(DefaultQueryStorage.class);
            super.bind(UpdateStorage.class).to(DefaultUpdateStorage.class);

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

        QueryStorage queryStorage = handler.getQueryStorage();
        queryStorage.add("getMainResourceType", handler.getAbsolutePath("/sparql/get-main-resource-type.rq"));
        queryStorage.add("getMedia", handler.getAbsolutePath("/sparql/get-media.rq"));
        queryStorage.add("getMediaCounter", handler.getAbsolutePath("/sparql/get-media-counter.rq"));
        queryStorage.add("getRelatedResourceType", handler.getAbsolutePath("/sparql/get-related-resource-type.rq"));
        queryStorage.add("getRelatedResourceTypeCounter", handler.getAbsolutePath("/sparql/get-related-resource-type-counter.rq"));
        queryStorage.add("searchMedia", handler.getAbsolutePath("/sparql/search-media.rq"));
        queryStorage.add("searchResources", handler.getAbsolutePath("/sparql/external/search-resources.rq"));
        queryStorage.add("searchResourcesLang", handler.getAbsolutePath("/sparql/external/search-resources-lang.rq"));

        UpdateStorage updateStorage = handler.getUpdateStorage();
        updateStorage.add("deleteMainResource", handler.getAbsolutePath("/sparqlu/delete-main-resource.ru"));
        updateStorage.add("deleteMainResourceType", handler.getAbsolutePath("/sparqlu/delete-main-resource-type.ru"));
        updateStorage.add("deleteMedia", handler.getAbsolutePath("/sparqlu/delete-media.ru"));
        updateStorage.add("deleteRelatedResource", handler.getAbsolutePath("/sparqlu/delete-related-resource.ru"));
        updateStorage.add("deleteRelatedResourceType", handler.getAbsolutePath("/sparqlu/delete-related-resource-type.ru"));
        updateStorage.add("insertMainResource", handler.getAbsolutePath("/sparqlu/insert-main-resource.ru"));
        updateStorage.add("insertMainResourceType", handler.getAbsolutePath("/sparqlu/insert-main-resource-type.ru"));
        updateStorage.add("insertMedia", handler.getAbsolutePath("/sparqlu/insert-media.ru"));
        updateStorage.add("insertRelatedResource", handler.getAbsolutePath("/sparqlu/insert-related-resource.ru"));
        updateStorage.add("insertRelatedResourceType", handler.getAbsolutePath("/sparqlu/insert-related-resource-type.ru"));
        updateStorage.add("setMediaCounter", handler.getAbsolutePath("/sparqlu/set-media-counter.ru"));
        updateStorage.add("setRelatedResourceTypeCounter", handler.getAbsolutePath("/sparqlu/set-related-resource-type-counter.ru"));
        updateStorage.add("saveUser", handler.getAbsolutePath("/sparqlu/save-user.ru"));

        return handler;
    }

    public static String getSessionUserKey() {
        return SESSION_USER_KEY;
    }

    public static Injector getInjector() {
        return INJECTOR;
    }
}
