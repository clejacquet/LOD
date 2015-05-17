package jp.kde.lod.jacquet.pageprocessing;

import java.util.Collection;

/**
 * Created by Clement on 15/05/2015.
 */
public interface WebView extends View {
    void addCss(String cssLink);
    void addJs(String jsLink);

    Collection<String> getCssLinks();
    Collection<String> getJsLinks();
}
