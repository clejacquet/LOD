package jp.kde.lod.jacquet.mediaselector.model.storage;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import jp.kde.lod.jacquet.mediaselector.model.QueryStorage;

import java.lang.reflect.Parameter;

/**
 * Created by Clement on 18/05/2015.
 */
public class DefaultQueryStorage extends BaseStorage implements QueryStorage {
    @Override
    public Query getQuery(String name) {
        return super.getSparqlString(name).copy().asQuery();
    }
}
