package jp.kde.lod.jacquet.mediaselector.controller.command;

/**
 * Created by Clement on 20/05/2015.
 */
public abstract class AuthenticatedCommand extends SessionVisibleCommand {
    @Override
    public boolean isAuthenticationNeeded() {
        return true;
    }
}
