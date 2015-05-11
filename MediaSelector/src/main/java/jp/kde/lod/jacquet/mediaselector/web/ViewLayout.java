package jp.kde.lod.jacquet.mediaselector.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 09/05/2015.
 */
public class ViewLayout {
    private String title;
    private List<String> cssLinks;
    private Map<String, String> headerItems;

    public static ViewLayout buildBootstrapViewLayout() {
        ViewLayout viewLayout = new ViewLayout();

        viewLayout.addCssLink("/css/bootstrap.min.css");
        viewLayout.addCssLink("/css/navbar-fixed-top.css");
        viewLayout.addCssLink("/css/layout.css");
        viewLayout.addHeaderItem("/home", "Home");

        return viewLayout;
    }

    public static ViewLayout buildBootstrapViewLayout(String title) {
        ViewLayout viewLayout = buildBootstrapViewLayout();
        viewLayout.setTitle(title);

        return viewLayout;
    }

    public ViewLayout() {
        this.cssLinks = new ArrayList<String>();
        this.headerItems = new HashMap<String, String>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addCssLink(String cssLink) {
        this.cssLinks.add(cssLink);
    }

    public void addHeaderItem(String uri, String title) {
        this.headerItems.put(uri, title);
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getCssLinks() {
        return this.cssLinks;
    }

    public Map<String, String> getHeaderItems() {
        return this.headerItems;
    }
}
