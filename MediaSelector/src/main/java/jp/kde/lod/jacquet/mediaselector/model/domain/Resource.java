package jp.kde.lod.jacquet.mediaselector.model.domain;

import jp.kde.lod.jacquet.mediaselector.model.JSONModel;
import jp.kde.lod.jacquet.mediaselector.model.RDFModel;
import org.json.JSONObject;

/**
 * Created by Clement on 15/05/2015.
 */
public abstract class Resource implements RDFModel, JSONModel {
    private String name;
    private String uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public void loadJSON(JSONObject jsonObject) {
        this.name = jsonObject.getString("name");
        this.uri = jsonObject.getString("uri");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject resourceJson = new JSONObject();
        resourceJson.put("name", this.name);
        resourceJson.put("uri", this.uri);
        return resourceJson;
    }
}
