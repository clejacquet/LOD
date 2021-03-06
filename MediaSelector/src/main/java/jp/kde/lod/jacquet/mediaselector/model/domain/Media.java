package jp.kde.lod.jacquet.mediaselector.model.domain;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import jp.kde.lod.jacquet.access.Access;
import jp.kde.lod.jacquet.access.ModelAccess;
import jp.kde.lod.jacquet.access.UpdateAccess;
import jp.kde.lod.jacquet.mediaselector.model.JSONModel;
import jp.kde.lod.jacquet.mediaselector.model.RDFModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Clement on 12/05/2015.
 */
public class Media implements RDFModel, JSONModel {

    private long id;
    private String name;
    private String sparqlEndPoint;
    private String description;
    private MainResourceType mainResourceType;
    private User author;

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

    public String getSparqlEndPoint() {
        return sparqlEndPoint;
    }

    public void setSparqlEndPoint(String sparqlEndPoint) {
        this.sparqlEndPoint = sparqlEndPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MainResourceType getMainResourceType() {
        return mainResourceType;
    }

    public void setMainResourceType(MainResourceType mainResourceType) {
        this.mainResourceType = mainResourceType;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public void save(Model model, ParameterizedSparqlString updateSparqlString) {
        UpdateAccess access = new ModelAccess(model);

        updateSparqlString.setParam("media", NodeFactory.createURI("http://mediaselector.com/media/" + this.getId()));
        updateSparqlString.setParam("media_name", NodeFactory.createLiteral(this.getName()));
        updateSparqlString.setParam("media_description", NodeFactory.createLiteral(this.getDescription()));
        updateSparqlString.setParam("media_end_point", NodeFactory.createURI(this.getSparqlEndPoint()));
        updateSparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(this.getId()), XSDDatatype.XSDlong));
        updateSparqlString.setParam("author_id", NodeFactory.createLiteral(Long.toString(this.author.getId()), XSDDatatype.XSDlong));

        access.execute(updateSparqlString.asUpdate());
    }

    @Override
    public void loadJSON(JSONObject mediaJson) {
        this.name = mediaJson.getString("name");
        this.sparqlEndPoint = mediaJson.getString("sparql");
        this.description = mediaJson.getString("description");

        this.mainResourceType = new MainResourceType(mediaJson.getJSONObject("resource"));
        this.mainResourceType.setMedia(this);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject mediaJson = new JSONObject();

        mediaJson.put("id", this.id);
        mediaJson.put("name", this.name);
        mediaJson.put("sparql", this.sparqlEndPoint);
        mediaJson.put("description", this.description);
        mediaJson.put("resource", this.mainResourceType.toJSON());
        mediaJson.put("author", this.author.getId());

        return mediaJson;
    }
}
