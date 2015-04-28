package jp.kde.lod.jacquet.mediaselector.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import jp.kde.lod.jacquet.mediaselector.web.servlet.DefaultServlet;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebListener;

/**
 * Created by Clement on 28/04/2015.
 */
@WebListener
public class MediaSelectorContextListener extends GuiceServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(MediaSelectorContextListener.class);

    @Override
    protected Injector getInjector() {
        LOGGER.info("Injector creation asked");
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                super.configureServlets();

                LOGGER.info("Configuring servlets ...");
                super.serve("/*").with(DefaultServlet.class);
                LOGGER.info("Configuring servlets done");
            }
        });
    }
}
