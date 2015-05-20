package jp.kde.lod.jacquet.mediaselector.controller.command.data;

import jp.kde.lod.jacquet.mediaselector.controller.command.SessionVisibleCommand;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;

/**
 * Created by Clement on 17/05/2015.
 */
public class DataPageCommand extends SessionVisibleCommand {
    @Override
    public View process() {
        return ViewFactory.buildBootstrapView("Media Selector - Data", "data/data.ftl");
    }
}
