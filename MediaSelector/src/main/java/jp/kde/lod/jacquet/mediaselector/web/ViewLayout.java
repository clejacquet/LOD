package jp.kde.lod.jacquet.mediaselector.web;

import java.util.*;

/**
 * Created by Clement on 09/05/2015.
 */
public class ViewLayout {
    private String title;
    private List<String> cssLinks;
    private List<String> jsLinks;
    private Map<String, HeaderItem> headerItems;

    public static ViewLayout buildBootstrapViewLayout() {
        ViewLayout viewLayout = new ViewLayout();

        viewLayout.addCssLink("/css/bootstrap.min.css");
        viewLayout.addCssLink("/css/navbar-fixed-top.css");
        viewLayout.addCssLink("/css/sticky-footer-navbar.css");

        viewLayout.addJsLink("https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js");
        viewLayout.addJsLink("/js/bootstrap.min.js");

        Map<String, String> mediaSubItems = new LinkedHashMap<String, String>();
        mediaSubItems.put("Search a media environment", "/media/search");
        mediaSubItems.put("Create a new one!", "/media/create");

        Map<String, String> dataSubItems = new LinkedHashMap<String, String>();
        dataSubItems.put("Vocabulary", "/data/voc");

        viewLayout.addHeaderItem("/home", HeaderItem.buildItem("Home"));
        viewLayout.addHeaderItem("/media", HeaderItem.buildDropdownList("Media", mediaSubItems));
        viewLayout.addHeaderItem("/data", HeaderItem.buildDropdownList("Data", dataSubItems));

        return viewLayout;
    }

    public static ViewLayout buildBootstrapViewLayout(String title) {
        ViewLayout viewLayout = buildBootstrapViewLayout();
        viewLayout.setTitle(title);

        return viewLayout;
    }

    public ViewLayout() {
        this.cssLinks = new ArrayList<String>();
        this.jsLinks = new ArrayList<String>();
        this.headerItems = new LinkedHashMap<String, HeaderItem>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addCssLink(String cssLink) {
        this.cssLinks.add(cssLink);
    }

    public void addJsLink(String jsLink) {
        this.jsLinks.add(jsLink);
    }

    public void addHeaderItem(String uri, HeaderItem headerItem) {
        this.headerItems.put(uri, headerItem);
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getCssLinks() {
        return this.cssLinks;
    }

    public List<String> getJsLinks() {
        return this.jsLinks;
    }

    public Map<String, HeaderItem> getHeaderItems() {
        return this.headerItems;
    }
}
