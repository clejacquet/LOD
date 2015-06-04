package jp.kde.lod.jacquet.mediaselector.controller.command.service.user;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionHiddenCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

import java.io.IOException;

/**
 * Created by Clement on 17/05/2015.
 */
public class UserLogoutCommand extends SessionHiddenCommand {
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
