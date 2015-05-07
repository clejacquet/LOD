package jp.kde.lod.jacquet.mediaselector;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import jp.kde.lod.jacquet.mediaselector.persistence.PersistenceModule;
import jp.kde.lod.jacquet.mediaselector.web.servlet.ErrorServlet;
import jp.kde.lod.jacquet.mediaselector.web.servlet.HomeServlet;
import jp.kde.lod.jacquet.mediaselector.web.servlet.user.UserConnectionServlet;
import jp.kde.lod.jacquet.mediaselector.web.servlet.user.UserRegisterServlet;
import jp.kde.lod.jacquet.mediaselector.web.servlet.user.UserServlet;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebListener;

/**
 * Created by Clement on 28/04/2015.
 */
@WebListener
public class ContextListener extends GuiceServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(ContextListener.class);

    @Override
    protected Injector getInjector() {
        LOGGER.info("Injector creation asked");
        return Guice.createInjector(new PersistenceModule(), new ServletModule() {
            @Override
            protected void configureServlets() {
                super.configureServlets();

                LOGGER.info("Configuring servlets ...");
                super.serve("/").with(HomeServlet.class);
                super.serve("/home").with(HomeServlet.class);

                super.serve("/user").with(UserServlet.class);
                super.serve("/user/connect").with(UserConnectionServlet.class);
                super.serve("/user/register").with(UserRegisterServlet.class);

                super.serve("/error").with(ErrorServlet.class);

                LOGGER.info("Install JpaPersistModule media-selector");
                super.install(new JpaPersistModule("media-selector"));

                LOGGER.info("Install servlet filter");
                filter("/*").through(PersistFilter.class);

                LOGGER.info("Configuring servlets done");
            }
        });
    }
}
