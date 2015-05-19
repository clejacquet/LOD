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
public class MainResourceType extends ResourceType {

    public MainResourceType() {

    }

    public MainResourceType(JSONObject resourceJson) {
        super(resourceJson);
    }

    @Override
    public void save(Model model, ParameterizedSparqlString updateSparqlString) {
        UpdateAccess access = new ModelAccess(model);

        updateSparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(super.getMedia().getId()), XSDDatatype.XSDlong));
        updateSparqlString.setParam("main_resource", NodeFactory.createURI("http://mediaselector.com/media/" + super.getMedia().getId() + "/res"));
        updateSparqlString.setParam("main_resource_type", NodeFactory.createURI(super.getTypeUri()));
        updateSparqlString.setParam("main_resource_name", NodeFactory.createLiteral(super.getName()));
        updateSparqlString.setParam("title_property", NodeFactory.createURI(super.getTitlePropertyUri()));

        access.execute(updateSparqlString.asUpdate());
    }
}
