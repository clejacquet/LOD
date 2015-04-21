package jp.kde.lod.jacquet.querylauncher.model;

import com.hp.hpl.jena.query.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Created by Clement on 16/04/2015.
 * Storage class which simply contains some queries
 */
public class QueryStorage extends Observable {
    /**
     * Internal storage of the queries. Each query is identified by a key which correspond to its name
     */
    private Map<String, Query> queries;

    /**
     * Construct the query map
     */
    public QueryStorage() {
        this.queries = new HashMap<String, Query>();
    }

    /**
     * Add a query if the name didn't exist before, replace it if not
     * @param queryName name of the query
     * @param query SPARQL query
     */
    public void putQuery(final String queryName, final Query query) {
        this.queries.put(queryName, query);
        super.setChanged();
        super.notifyObservers(queryName);
    }

    /**
     * Return the query corresponding to the query name provided
     * @param queryName name of the desired query
     * @return the SPARQL query corresponding to
     */
    public Query getQuery(final String queryName) {
        return this.queries.get(queryName);
    }
}
