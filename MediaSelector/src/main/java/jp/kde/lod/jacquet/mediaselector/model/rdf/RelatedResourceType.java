package jp.kde.lod.jacquet.mediaselector.model.rdf;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.rdf.model.Model;
import jp.kde.lod.jacquet.access.ModelAccess;
import jp.kde.lod.jacquet.access.UpdateAccess;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 14/05/2015.
 */
public class RelatedResourceType extends ResourceType {
    private long id;
    private String relation;

    public RelatedResourceType() {

    }

    public RelatedResourceType(JSONObject resourceJson) {
        super(resourceJson);
        this.relation = resourceJson.getString("property");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void save(Model model, String commandText) {
        UpdateAccess access = new ModelAccess(model);

        Map<String, Node> parameters = new HashMap<>();
        parameters.put("media_id", NodeFactory.createLiteral(Long.toString(super.getMedia().getId()), XSDDatatype.XSDlong));
        parameters.put("related_resource", NodeFactory.createURI("http://mediaselector.com/media/" + super.getMedia().getId() + "/rel/" + this.id));
        parameters.put("related_resource_id", NodeFactory.createLiteral(Long.toString(this.id), XSDDatatype.XSDlong));
        parameters.put("related_resource_type", NodeFactory.createURI(super.getTypeIri()));
        parameters.put("related_resource_name", NodeFactory.createLiteral(super.getName()));
        parameters.put("related_resource_relation", NodeFactory.createURI(this.relation));

        access.execute(commandText, parameters);
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
