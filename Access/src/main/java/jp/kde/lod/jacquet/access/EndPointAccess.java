package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Created by Clement on 27/04/2015.
 * Provide a simple SPARQL access for Web SPARQL endpoints
 */
public class EndPointAccess extends BaseAccess {

    /**
     *
     */
    private String endPointURL;

    /**
     *
     * @param endPointURL
     */
    public EndPointAccess(String endPointURL) {
        this.endPointURL = endPointURL;
    }

    /**
     * Create a Jena QueryExecution instance
     * @param query source for the queryExecution which is going to be created
     * @return Query execution
     */
    @Override
    protected QueryExecution createQueryExecution(Query query) {
        return QueryExecutionFactory.sparqlService(this.endPointURL, query);
    }
}
