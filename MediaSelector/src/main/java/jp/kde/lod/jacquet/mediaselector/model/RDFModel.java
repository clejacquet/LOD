package jp.kde.lod.jacquet.mediaselector.model;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Created by Clement on 18/05/2015.
 */
public interface RDFModel {
    void save(Model model, ParameterizedSparqlString updateSparqlString);
}
