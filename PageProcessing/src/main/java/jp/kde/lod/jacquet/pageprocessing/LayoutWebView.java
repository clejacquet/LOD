package jp.kde.lod.jacquet.pageprocessing;


/**
 * Created by Clement on 15/05/2015.
 */
public abstract class LayoutWebView extends BaseWebView {
    private Layout layout;
    private String contentPath;

    private String contentKey;
    private String cssKey;
    private String jsKey;

    public Layout getLayout() {
        return this.layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public String getContentPath() {
        return this.contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getContentKey() {
        return this.contentKey;
    }

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
    }

    public String getCssKey() {
        return cssKey;
    }

    public void setCssKey(String cssKey) {
        this.cssKey = cssKey;
    }

    public String getJsKey() {
        return jsKey;
    }

    public void setJsKey(String jsKey) {
        this.jsKey = jsKey;
    }
}
