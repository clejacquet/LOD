package jp.kde.lod.jacquet.mediaselector.model.domain;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Model;
import jp.kde.lod.jacquet.access.ModelAccess;
import jp.kde.lod.jacquet.access.UpdateAccess;
import org.json.JSONObject;

/**
 * Created by Clement on 14/05/2015.
 */
public class RelatedResourceType extends ResourceType {
    private long id;
    private String relation;

    public RelatedResourceType() {

    }

    public RelatedResourceType(JSONObject resourceJson) {
        this.loadJSON(resourceJson);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public void save(Model model, ParameterizedSparqlString updateSparqlString) {
        UpdateAccess access = new ModelAccess(model);

        updateSparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(super.getMedia().getId()), XSDDatatype.XSDlong));
        updateSparqlString.setParam("related_resource", NodeFactory.createURI("http://mediaselector.com/media/" + super.getMedia().getId() + "/rel/" + this.id));
        updateSparqlString.setParam("related_resource_id", NodeFactory.createLiteral(Long.toString(this.id), XSDDatatype.XSDlong));
        updateSparqlString.setParam("related_resource_type", NodeFactory.createURI(super.getTypeUri()));
        updateSparqlString.setParam("related_resource_name", NodeFactory.createLiteral(super.getName()));
        updateSparqlString.setParam("related_resource_relation", NodeFactory.createURI(this.relation));

        access.execute(updateSparqlString.asUpdate());
    }

    @Override
    public void loadJSON(JSONObject jsonObject) {
        super.loadJSON(jsonObject);
        this.relation = jsonObject.getString("property");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject resourceJson = super.toJSON();
        resourceJson.put("property", this.relation);
        return resourceJson;
    }
}
