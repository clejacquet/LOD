package jp.kde.lod.jacquet.mediaselector.web;

import java.util.Map;

/**
 * Created by Clement on 11/05/2015.
 */
public class HeaderItem {
    public static HeaderItem buildItem(String title) {
        HeaderItem item = new HeaderItem();
        item.setType("item");
        item.setTitle(title);
        return item;
    }

    public static HeaderItem buildDropdownList(String title, Map<String, String> subItems) {
        HeaderItem item = new HeaderItem();
        item.setType("dropdown");
        item.setTitle(title);
        item.setSubItems(subItems);
        return item;
    }

    private String type;
    private String title;
    private Map<String, String> subItems;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getSubItems() {
        return subItems;
    }

    public void setSubItems(Map<String, String> subItems) {
        this.subItems = subItems;
    }
}
