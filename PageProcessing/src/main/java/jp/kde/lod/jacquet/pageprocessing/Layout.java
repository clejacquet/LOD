package jp.kde.lod.jacquet.pageprocessing;

import java.util.Map;

/**
 * Created by Clement on 15/05/2015.
 */
public interface Layout {
    void setLayoutTemplate(String layoutTemplatePath);
    String getLayoutTemplate();

    void addParameter(String parameter, Object value);

    Map<String, Object> getMapping(String key, String contentPath);
}
