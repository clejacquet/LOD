package jp.kde.lod.jacquet.mediaselector.rdf;

import org.json.JSONObject;

/**
 * Created by Clement on 12/05/2015.
 */
public class ResourceType {
    private long id;
    private String name;
    private String typeIri;
    private long mediaId;

    public ResourceType(JSONObject resourceJson) {
        this.name = resourceJson.getString("name");
        this.typeIri = resourceJson.getString("type");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeIri() {
        return typeIri;
    }

    public void setTypeIri(String typeIri) {
        this.typeIri = typeIri;
    }

    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }
}
