package jp.kde.lod.jacquet.mediaselector.web;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import jp.kde.lod.jacquet.mediaselector.persistence.PersistenceModule;
import jp.kde.lod.jacquet.mediaselector.web.service.*;

/**
 * Created by Clement on 09/05/2015.
 */
public class RestModule implements Module {
    public void configure(final Binder binder) {
        binder.bind(HomeService.class);
        binder.bind(MediaService.class);
        binder.bind(UserService.class);
        binder.bind(DataService.class);
        binder.bind(DocService.class);

        binder.install(new PersistenceModule());

        binder.install(new ServletModule() {
            @Override
            protected void configureServlets() {
                super.configureServlets();

                super.install(new JpaPersistModule("mediaselector"));
                super.filter("/*").through(PersistFilter.class);
            }
        });
    }
}
