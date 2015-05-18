package jp.kde.lod.jacquet.mediaselector.model.domain;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Model;
import org.json.JSONObject;

/**
 * Created by Clement on 18/05/2015.
 */
public class MainResource extends Resource {
    @Override
    public void save(Model model, ParameterizedSparqlString updateSparqlString) {

    }

    @Override
    public void loadJSON(JSONObject jsonObject) {

    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
