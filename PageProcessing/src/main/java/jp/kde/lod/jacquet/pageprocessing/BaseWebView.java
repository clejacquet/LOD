package jp.kde.lod.jacquet.pageprocessing;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;

/**
 * Created by Clement on 15/05/2015.
 */
public abstract class BaseWebView implements WebView {
    private Collection<String> cssLinks;
    private Collection<String> jsLinks;
    private Map<String, Object> parameters;

    public BaseWebView() {
        this.cssLinks = new ArrayList<>();
        this.jsLinks = new ArrayList<>();
        this.parameters = new HashMap<>();
    }

    @Override
    public void addCss(String cssLink) {
        this.cssLinks.add("<link href=\"" + cssLink + "\" rel=\"stylesheet\"/>");
    }

    @Override
    public void addJs(String jsLink) {
        this.jsLinks.add("<script src=\"" + jsLink + "\"></script>");
    }

    @Override
    public void addParameter(String parameter, Object value) {
        this.parameters.put(parameter, value);
    }

    @Override
    public Collection<String> getCssLinks() {
        return this.cssLinks;
    }

    @Override
    public Collection<String> getJsLinks() {
        return this.jsLinks;
    }

    @Override
    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    @Override
    public String toString() {
        return this.generate();
    }
}
