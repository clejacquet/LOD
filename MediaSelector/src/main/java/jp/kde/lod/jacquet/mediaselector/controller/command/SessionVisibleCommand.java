package jp.kde.lod.jacquet.mediaselector.controller.command;

import jp.kde.lod.jacquet.mediaselector.controller.HTMLCommand;

/**
 * Created by Clement on 16/05/2015.
 */
public abstract class SessionVisibleCommand extends BaseServletSubject implements HTMLCommand {
    @Override
    public boolean isSessionVisible() {
        return true;
    }

    @Override
    public boolean isAuthenticationNeeded() {
        return false;
    }

    @Override
    public String getRedirectionURL() {
        return "/home";
    }
}
