package jp.kde.lod.jacquet.movielensloader;

import com.hp.hpl.jena.query.Query;

/**
 * Created by Clement on 18/05/2015.
 */
public class QueryStorage extends BaseStorage {
    public QueryStorage() {
        super.add("getUserUri", "get-user-uri.rq");
        super.add("getMainResource", "get-main-resource.rq");
    }

    public Query getQuery(String name) {
        return super.getSparqlString(name).copy().asQuery();
    }
}
