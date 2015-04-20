package jp.kde.lod.jacquet.querylauncher.model;

import com.hp.hpl.jena.query.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Created by Clement on 16/04/2015.
 */
public class QueryStorage extends Observable {
    private Map<String, Query> queries;

    public QueryStorage() {
        this.queries = new HashMap<String, Query>();
    }

    public void putQuery(String queryName, Query query) {
        this.queries.put(queryName, query);
        super.setChanged();
        super.notifyObservers(queryName);
    }

    public Query getQuery(String queryName) {
        return this.queries.get(queryName);
    }
}
