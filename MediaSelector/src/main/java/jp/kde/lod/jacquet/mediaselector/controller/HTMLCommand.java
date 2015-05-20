package jp.kde.lod.jacquet.mediaselector.controller;

import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 16/05/2015.
 */
public interface HTMLCommand extends ServletSubject {
    View process();
    boolean isSessionVisible();
    boolean isAuthenticationNeeded();
}
