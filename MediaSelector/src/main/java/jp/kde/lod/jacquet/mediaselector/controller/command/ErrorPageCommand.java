package jp.kde.lod.jacquet.mediaselector.controller.command;

import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 17/05/2015.
 */
public class ErrorPageCommand extends SessionVisibleCommand {
    @Override
    public View process() {
        return ViewFactory.buildBootstrapView("Media Selector - Error", "error.ftl");
    }
}
