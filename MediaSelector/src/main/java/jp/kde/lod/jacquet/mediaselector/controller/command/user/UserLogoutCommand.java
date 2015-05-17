package jp.kde.lod.jacquet.mediaselector.controller.command.user;

import jp.kde.lod.jacquet.mediaselector.controller.command.UnauthenticatedCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

import java.io.IOException;

/**
 * Created by Clement on 17/05/2015.
 */
public class UserLogoutCommand extends UnauthenticatedCommand {
    @Override
    public View process() {
        super.getHandler().getRequest().getSession().invalidate();
        try {
            super.getHandler().getResponse().sendRedirect("/home");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ViewFactory.buildEmptyView();
    }
}
