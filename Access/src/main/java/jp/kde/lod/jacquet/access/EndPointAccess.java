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
public class EndPointAccess implements Access {

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
     * return the result of a select query
     *
     * @param query select query
     * @return result of the query
     */
    public ResultSet executeSelect(Query query) {
        return this.createQueryExecution(query).execSelect();
    }

    /**
     * return the constructed graph from a construct query
     *
     * @param query construct query
     * @return constructed graph
     */
    public Model executeConstruct(Query query) {
        return this.createQueryExecution(query).execConstruct();
    }

    /**
     * return the description graph from a describe query
     *
     * @param query describe query
     * @return description graph
     */
    public Model executeDescribe(Query query) {
        return this.createQueryExecution(query).execDescribe();
    }

    /**
     * return the answer of an ask query
     *
     * @param query ask query
     * @return answer of the ask query
     */
    public boolean executeAsk(Query query) {
        return this.createQueryExecution(query).execAsk();
    }

    /**
     *
     * @param query
     * @return
     */
    private QueryExecution createQueryExecution(Query query) {
        return QueryExecutionFactory.sparqlService(this.endPointURL, query);
    }
}
