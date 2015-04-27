package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Created by Clement on 27/04/2015.
 */
public class ModelAccess implements Access {

    /**
     * Jena Model used as a SPARQL access
     */
    private Model model;

    /**
     * Construct a Virtuoso SPARQL Access from a Virtuoso model
     * @param model Virtuoso model
     */
    public ModelAccess(Model model) {
        this.model = model;
    }

    /**
     *
     * @return
     */
    public Model getModel() {
        return this.model;
    }

    /**
     * return the result of a select query
     * @param query select query
     * @return result of the query
     */
    public ResultSet executeSelect(Query query) {
        return this.createQueryExecution(query).execSelect();
    }

    /**
     * return the constructed graph from a construct query
     * @param query construct query
     * @return constructed graph
     */
    public Model executeConstruct(Query query) {
        return this.createQueryExecution(query).execConstruct();
    }

    /**
     * return the description graph from a describe query
     * @param query describe query
     * @return description graph
     */
    public Model executeDescribe(Query query) {
        return this.createQueryExecution(query).execDescribe();
    }

    /**
     * return the answer of an ask query
     * @param query ask query
     * @return answer of the ask query
     */
    public boolean executeAsk(Query query) {
        return this.createQueryExecution(query).execAsk();
    }

    /**
     * Create a Jena QueryExecution instance
     * @param query source for the queryExecution which is going to be created
     * @return Query execution
     */
    protected QueryExecution createQueryExecution(Query query) {
        return QueryExecutionFactory.create(query, this.model);
    }
}
