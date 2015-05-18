package jp.kde.lod.jacquet.mediaselector.model.domain;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Model;
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
    private MainResourceType mainResource;
    private Collection<RelatedResourceType> relatedResource;

    public Media() {
        this.relatedResource = new ArrayList<>();
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

    public String getSparqlEndPoint() {
        return sparqlEndPoint;
    }

    public void setSparqlEndPoint(String sparqlEndPoint) {
        this.sparqlEndPoint = sparqlEndPoint;
    }

    public MainResourceType getMainResource() {
        return mainResource;
    }

    public void setMainResource(MainResourceType mainResource) {
        this.mainResource = mainResource;
    }

    public Collection<RelatedResourceType> getRelatedResource() {
        return relatedResource;
    }

    public void setRelatedResource(Collection<RelatedResourceType> relatedResource) {
        this.relatedResource = relatedResource;
    }

    @Override
    public void save(Model model, ParameterizedSparqlString updateSparqlString) {
        UpdateAccess access = new ModelAccess(model);

        updateSparqlString.setParam("media", NodeFactory.createURI("http://mediaselector.com/media/" + this.getId()));
        updateSparqlString.setParam("media_name", NodeFactory.createLiteral(this.getName()));
        updateSparqlString.setParam("media_end_point", NodeFactory.createURI(this.getSparqlEndPoint()));
        updateSparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(this.getId()), XSDDatatype.XSDlong));

        access.execute(updateSparqlString.asUpdate());
    }

    @Override
    public void loadJSON(JSONObject mediaJson) {
        this.name = mediaJson.getString("name");
        this.sparqlEndPoint = mediaJson.getString("sparql");

        this.mainResource = new MainResourceType(mediaJson.getJSONObject("resource"));
        this.mainResource.setMedia(this);

        JSONArray array = mediaJson.getJSONArray("relatedResources");
        int arrayLength = array.length();

        for (int i = 0;i < arrayLength;i++) {
            JSONObject object = array.getJSONObject(i);
            RelatedResourceType resourceType = new RelatedResourceType(object);
            resourceType.setMedia(this);
            this.relatedResource.add(resourceType);
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject mediaJson = new JSONObject();
        mediaJson.put("id", this.id);
        mediaJson.put("name", this.name);
        mediaJson.put("sparql", this.sparqlEndPoint);
        mediaJson.put("resource", this.mainResource.toJSON());

        JSONArray relatedResourcesArray = new JSONArray();
        for (RelatedResourceType relatedResourceType : this.relatedResource) {
            relatedResourcesArray.put(relatedResourceType.toJSON());
        }
        mediaJson.put("relatedResources", relatedResourcesArray);

        return mediaJson;
    }
}