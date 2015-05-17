package jp.kde.lod.jacquet.pageprocessing;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 15/05/2015.
 */
public class DefaultLayout implements Layout {
    private Map<String, Object> mapping;
    private String layoutTemplatePath;

    public DefaultLayout() {
        this.mapping = new HashMap<>();
    }

    @Override
    public void setLayoutTemplate(String layoutTemplatePath) {
        this.layoutTemplatePath = layoutTemplatePath;
    }

    @Override
    public String getLayoutTemplate() {
        return this.layoutTemplatePath;
    }

    @Override
    public void addParameter(String parameter, Object value) {
        this.mapping.put(parameter, value);
    }

    @Override
    public Map<String, Object> getMapping(String key, String contentPath) {
        Map<String, Object> finalMapping = new HashMap<>(mapping);
        finalMapping.put(key, contentPath);
        return finalMapping;
    }
}
