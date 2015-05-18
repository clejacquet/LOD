package jp.kde.lod.jacquet.mediaselector.model.rdf;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Model;
import jp.kde.lod.jacquet.access.ModelAccess;
import jp.kde.lod.jacquet.access.UpdateAccess;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 14/05/2015.
 */
public class MainResourceType extends ResourceType {

    public MainResourceType() {

    }

    public MainResourceType(JSONObject resourceJson) {
        super(resourceJson);
    }

    @Override
    public void save(Model model, ParameterizedSparqlString sparqlString) {
        UpdateAccess access = new ModelAccess(model);

        sparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(super.getMedia().getId()), XSDDatatype.XSDlong));
        sparqlString.setParam("main_resource", NodeFactory.createURI("http://mediaselector.com/media/" + super.getMedia().getId() + "/res"));
        sparqlString.setParam("main_resource_type", NodeFactory.createURI(super.getTypeIri()));
        sparqlString.setParam("main_resource_name", NodeFactory.createLiteral(super.getName()));

        access.execute(sparqlString.asUpdate());
    }
}
