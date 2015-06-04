package jp.kde.lod.jacquet.mediaselector.view;

import jp.kde.lod.jacquet.pageprocessing.*;

import java.util.*;

/**
 * Created by Clement on 15/05/2015.
 */
public final class ViewFactory {


    private ViewFactory() {

    }

    private static Layout buildBootstrapLayout(String title, String contentPath) {
        Map<String, String> mediaSubItems = new LinkedHashMap<>();
        mediaSubItems.put("Search a media environment", "/media/search");
        mediaSubItems.put("Create a new one!", "/media/create");

        Map<String, HeaderItem> headerItems = new HashMap<>();
        headerItems.put("/home", HeaderItem.buildItem("Home"));
        headerItems.put("/media", HeaderItem.buildDropdownList("Media", mediaSubItems));

        Layout layout = new DefaultLayout();
        layout.setLayoutTemplate("/templates/layout.ftl");
        layout.addParameter("title", title);
        layout.addParameter("home", "/home");
        layout.addParameter("header_items", headerItems);

        return layout;
    }

    public static View buildEmptyView() {
        return new BaseWebView() {
            @Override
            public String generate() {
                return null;
            }
        };
    }

    public static LayoutWebView buildBootstrapView(String title, String contentPath) {
        LayoutWebView view = new FreemarkerLayoutWebView();
        view.setContentKey("content");

        view.setContentPath(contentPath);
        view.setLayout(ViewFactory.buildBootstrapLayout(title, contentPath));

        view.setCssKey("css_links");
        view.addCss("/css/bootstrap.min.css");
        view.addCss("/css/navbar-fixed-top.css");
        view.addCss("/css/sticky-footer-navbar.css");

        view.setJsKey("js_links");
        view.addJs("https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js");
        view.addJs("/js/bootstrap.min.js");

        return view;
    }
}
