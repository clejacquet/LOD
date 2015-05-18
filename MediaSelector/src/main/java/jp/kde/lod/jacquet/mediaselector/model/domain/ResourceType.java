package jp.kde.lod.jacquet.mediaselector.model.domain;

import jp.kde.lod.jacquet.mediaselector.model.JSONModel;
import jp.kde.lod.jacquet.mediaselector.model.RDFModel;
import org.json.JSONObject;

/**
 * Created by Clement on 12/05/2015.
 */
public abstract class ResourceType implements RDFModel, JSONModel {
    private String name;
    private String typeUri;
    private Media media;

    public ResourceType() {

    }

    public ResourceType(JSONObject resourceJson) {
        this.loadJSON(resourceJson);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeUri() {
        return typeUri;
    }

    public void setTypeUri(String typeUri) {
        this.typeUri = typeUri;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public void loadJSON(JSONObject jsonObject) {
        this.name = jsonObject.getString("name");
        this.typeUri = jsonObject.getString("type");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject resourceJson = new JSONObject();
        resourceJson.put("name", this.name);
        resourceJson.put("type", this.typeUri);
        return resourceJson;
    }
}
