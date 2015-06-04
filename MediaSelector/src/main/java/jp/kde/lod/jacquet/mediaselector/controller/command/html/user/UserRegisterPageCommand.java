package jp.kde.lod.jacquet.mediaselector.controller.command.html.user;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;
import jp.kde.lod.jacquet.pageprocessing.WebView;

/**
 * Created by Clement on 17/05/2015.
 */
public class UserRegisterPageCommand extends SessionVisibleCommand {
    @Override
    public View process() {
        WebView view = ViewFactory.buildBootstrapView("Media Selector - Register", "user/register.ftl");
        view.addCss("/css/user/register/register.css");
        view.addJs("/js/utils/url.js");
        view.addJs("/js/user/sign-redirection.js");
        return view;
    }
}
