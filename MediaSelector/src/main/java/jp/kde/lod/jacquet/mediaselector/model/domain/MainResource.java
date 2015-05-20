package jp.kde.lod.jacquet.mediaselector.model.domain;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Model;
import org.json.JSONObject;

/**
 * Created by Clement on 18/05/2015.
 */
public class MainResource extends Resource {
    private String authorUri;
    private String authorName;
    private String abstractText;
    private String date;

    public String getAuthorUri() {
        return authorUri;
    }

    public void setAuthorUri(String authorUri) {
        this.authorUri = authorUri;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void loadJSON(JSONObject jsonObject) {
        super.loadJSON(jsonObject);
        this.authorUri = jsonObject.getString("author");
        this.authorName = jsonObject.getString("author_name");
        this.abstractText = jsonObject.getString("abstract");
        this.date = jsonObject.getString("date");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject mainResourceJson = super.toJSON();

        mainResourceJson.put("author", this.authorUri);
        mainResourceJson.put("author_name", this.authorName);
        mainResourceJson.put("abstract", this.abstractText);
        mainResourceJson.put("date", this.date);

        return mainResourceJson;
    }
}
