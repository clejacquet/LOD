package jp.kde.lod.jacquet.mediaselector.controller.command;

import jp.kde.lod.jacquet.mediaselector.controller.HTMLCommand;

/**
 * Created by Clement on 16/05/2015.
 */
public abstract class AuthentificatedCommand extends BaseServletSubject implements HTMLCommand {
    @Override
    public boolean isAuthenticatedNeeded() {
        return true;
    }
}
