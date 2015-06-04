package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Created by Clement on 13/05/2015.
 */
public abstract class BaseAccess implements Access {

    /**
     * return the result of a select query
     * @param query select query
     * @return result of the query
     */
    public ResultSet executeSelect(Query query) {
        QueryExecution queryExecution = this.createQueryExecution(query);
        ResultSet resultSet = queryExecution.execSelect();
        return resultSet;
    }

    /**
     * return the constructed graph from a construct query
     * @param query construct query
     * @return constructed graph
     */
    public Model executeConstruct(Query query) {
        QueryExecution queryExecution = this.createQueryExecution(query);
        Model model = queryExecution.execConstruct();
        return model;
    }

    /**
     * return the description graph from a describe query
     * @param query describe query
     * @return description graph
     */
    public Model executeDescribe(Query query) {
        QueryExecution queryExecution = this.createQueryExecution(query);
        Model model = queryExecution.execDescribe();
        return model;
    }

    /**
     * return the answer of an ask query
     * @param query ask query
     * @return answer of the ask query
     */
    public Boolean executeAsk(Query query) {
        QueryExecution queryExecution = this.createQueryExecution(query);
        return queryExecution.execAsk();
    }

    /**
     * Create a Jena QueryExecution instance
     * @param query source for the queryExecution which is going to be created
     * @return Query execution
     */
    protected abstract QueryExecution createQueryExecution(Query query);
}
