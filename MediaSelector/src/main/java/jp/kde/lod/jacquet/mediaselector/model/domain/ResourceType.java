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
    private String titlePropertyUri;
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

    public String getTitlePropertyUri() {
        return titlePropertyUri;
    }

    public void setTitlePropertyUri(String titlePropertyUri) {
        this.titlePropertyUri = titlePropertyUri;
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
        this.titlePropertyUri = jsonObject.getString("titleProperty");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject resourceJson = new JSONObject();
        resourceJson.put("name", this.name);
        resourceJson.put("type", this.typeUri);
        resourceJson.put("titleProperty", this.titlePropertyUri);
        return resourceJson;
    }
}
