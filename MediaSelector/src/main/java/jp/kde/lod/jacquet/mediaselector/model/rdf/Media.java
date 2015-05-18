package jp.kde.lod.jacquet.mediaselector.model.rdf;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Model;
import jp.kde.lod.jacquet.access.ModelAccess;
import jp.kde.lod.jacquet.access.UpdateAccess;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 12/05/2015.
 */
public class Media {

    private long id;
    private String name;
    private String sparqlEndPoint;
    private MainResourceType mainResource;
    private Collection<RelatedResourceType> relatedResource;

    public Media() {
        this.relatedResource = new ArrayList<>();
    }

    public void loadJson(JSONObject mediaJson) {
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

    public void save(Model model, ParameterizedSparqlString sparqlString) {
        UpdateAccess access = new ModelAccess(model);

        sparqlString.setParam("media", NodeFactory.createURI("http://mediaselector.com/media/" + this.getId()));
        sparqlString.setParam("media_name", NodeFactory.createLiteral(this.getName()));
        sparqlString.setParam("media_end_point", NodeFactory.createURI(this.getSparqlEndPoint()));
        sparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(this.getId()), XSDDatatype.XSDlong));

        access.execute(sparqlString.asUpdate());
    }
}
