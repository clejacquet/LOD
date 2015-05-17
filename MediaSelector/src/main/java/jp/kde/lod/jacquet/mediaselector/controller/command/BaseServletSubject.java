package jp.kde.lod.jacquet.mediaselector.controller.command;

import jp.kde.lod.jacquet.mediaselector.controller.ServletSubject;
import jp.kde.lod.jacquet.mediaselector.controller.ServletHandler;

/**
 * Created by Clement on 16/05/2015.
 */
public abstract class BaseServletSubject implements ServletSubject {
    private ServletHandler handler;

    @Override
    public void setHandler(ServletHandler handler) {
        this.handler = handler;
    }

    public ServletHandler getHandler() {
        return this.handler;
    }
}
