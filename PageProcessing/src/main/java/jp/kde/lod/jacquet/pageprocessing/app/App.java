package jp.kde.lod.jacquet.pageprocessing.app;

import jp.kde.lod.jacquet.pageprocessing.DefaultLayout;
import jp.kde.lod.jacquet.pageprocessing.FreemarkerLayoutWebView;
import jp.kde.lod.jacquet.pageprocessing.LayoutWebView;
import jp.kde.lod.jacquet.pageprocessing.Layout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 15/05/2015.
 */
public class App {
    private static List<String> getHeaders() {
        List<String> headers = new ArrayList<>();
        headers.add("Home");
        headers.add("Data");
        headers.add("Download");
        return headers;
    }

    public static void main(String[] args) {
        Layout layout = new DefaultLayout();
        layout.setLayoutTemplate("/layout.ftl");
        layout.addParameter("headers", getHeaders());

        LayoutWebView webView = new FreemarkerLayoutWebView();
        webView.setContentKey("content");

        webView.setContentPath("/content.ftl");
        webView.setLayout(layout);

        webView.setCssKey("cssLinks");
        webView.addCss("/css/main.css");
        webView.addCss("/css/test.css");

        webView.setJsKey("jsLinks");
        webView.addJs("js/jquery");
        webView.addJs("js/angularjs");

        webView.addParameter("text", "Hello World!");

        String result = webView.generate();
        System.out.println(result);
    }
}
