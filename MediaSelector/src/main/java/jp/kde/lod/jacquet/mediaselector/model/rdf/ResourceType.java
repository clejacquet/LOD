package jp.kde.lod.jacquet.mediaselector.model.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import org.json.JSONObject;

/**
 * Created by Clement on 12/05/2015.
 */
public abstract class ResourceType {
    private String name;
    private String typeIri;
    private Media media;

    public ResourceType() {

    }

    public ResourceType(JSONObject resourceJson) {
        this.name = resourceJson.getString("name");
        this.typeIri = resourceJson.getString("type");
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

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public abstract void save(Model model, String commandText);
}
