package jp.kde.lod.jacquet.mediaselector.model;

import com.hp.hpl.jena.query.ParameterizedSparqlString;

/**
 * Created by Clement on 18/05/2015.
 */
public interface Storage {
    void add(String name, String file);
    ParameterizedSparqlString getSparqlString(String name);
}
