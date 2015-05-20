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

    private String authorProperty;
    private String authorNameProperty;
    private String abstractProperty;
    private String dateProperty;

    public MainResourceType() {

    }

    public MainResourceType(JSONObject resourceJson) {
        super(resourceJson);
    }

    public String getAuthorProperty() {
        return authorProperty;
    }

    public void setAuthorProperty(String authorProperty) {
        this.authorProperty = authorProperty;
    }

    public String getAuthorNameProperty() {
        return authorNameProperty;
    }

    public void setAuthorNameProperty(String authorNameProperty) {
        this.authorNameProperty = authorNameProperty;
    }

    public String getAbstractProperty() {
        return abstractProperty;
    }

    public void setAbstractProperty(String abstractProperty) {
        this.abstractProperty = abstractProperty;
    }

    public String getDateProperty() {
        return dateProperty;
    }

    public void setDateProperty(String dateProperty) {
        this.dateProperty = dateProperty;
    }

    @Override
    public void save(Model model, ParameterizedSparqlString updateSparqlString) {
        UpdateAccess access = new ModelAccess(model);

        updateSparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(super.getMedia().getId()), XSDDatatype.XSDlong));
        updateSparqlString.setParam("main_resource", NodeFactory.createURI("http://mediaselector.com/media/" + super.getMedia().getId() + "/res"));
        updateSparqlString.setParam("main_resource_type", NodeFactory.createURI(super.getTypeUri()));
        updateSparqlString.setParam("main_resource_name", NodeFactory.createLiteral(super.getName()));
        updateSparqlString.setParam("title_property", NodeFactory.createURI(super.getTitlePropertyUri()));
        updateSparqlString.setParam("author_property", NodeFactory.createURI(this.authorProperty));
        updateSparqlString.setParam("author_name_property", NodeFactory.createURI(this.authorNameProperty));
        updateSparqlString.setParam("abstract_property", NodeFactory.createURI(this.abstractProperty));
        updateSparqlString.setParam("date_property", NodeFactory.createURI(this.dateProperty));

        access.execute(updateSparqlString.asUpdate());
    }

    @Override
    public void loadJSON(JSONObject jsonObject) {
        super.loadJSON(jsonObject);

        this.authorProperty = jsonObject.getString("authorProperty");
        this.authorNameProperty = jsonObject.getString("authorNameProperty");
        this.abstractProperty = jsonObject.getString("abstractProperty");
        this.dateProperty = jsonObject.getString("dateProperty");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject resourceJson = super.toJSON();

        resourceJson.put("authorProperty", this.authorProperty);
        resourceJson.put("authorNameProperty", this.authorNameProperty);
        resourceJson.put("abstractProperty", this.abstractProperty);
        resourceJson.put("dateProperty", this.dateProperty);

        return resourceJson;
    }
}
