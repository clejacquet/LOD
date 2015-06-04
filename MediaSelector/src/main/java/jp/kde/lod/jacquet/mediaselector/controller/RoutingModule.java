package jp.kde.lod.jacquet.mediaselector.controller;

import com.google.inject.Binder;
import com.google.inject.Module;
import jp.kde.lod.jacquet.mediaselector.controller.routing.*;

/**
 * Created by Clement on 09/05/2015.
 */
public class RoutingModule implements Module {
    public void configure(final Binder binder) {
        binder.bind(HomeRouter.class);
        binder.bind(MediaRouter.class);
        binder.bind(UserRouter.class);
    }
}
