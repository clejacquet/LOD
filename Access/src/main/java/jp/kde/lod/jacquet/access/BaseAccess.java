package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

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

    public ResultSet executeSelect(String queryPath, Map<String, Node> parameters) {
        ParameterizedSparqlString sparqlString = null;
        try {
            sparqlString = new ParameterizedSparqlString(IOUtils.toString(new FileInputStream(queryPath)));
            for (Map.Entry<String, Node> param : parameters.entrySet()) {
                sparqlString.setParam(param.getKey(), param.getValue());
            }
            QueryExecution queryExecution = this.createQueryExecution(sparqlString.asQuery());
            return queryExecution.execSelect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public Model executeConstruct(String queryPath, Map<String, Node> parameters) {
        ParameterizedSparqlString sparqlString = null;
        try {
            sparqlString = new ParameterizedSparqlString(IOUtils.toString(new FileInputStream(queryPath)));
            for (Map.Entry<String, Node> param : parameters.entrySet()) {
                sparqlString.setParam(param.getKey(), param.getValue());
            }
            QueryExecution queryExecution = this.createQueryExecution(sparqlString.asQuery());
            return queryExecution.execConstruct();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public Model executeDescribe(String queryPath, Map<String, Node> parameters) {
        ParameterizedSparqlString sparqlString = null;
        try {
            sparqlString = new ParameterizedSparqlString(IOUtils.toString(new FileInputStream(queryPath)));
            for (Map.Entry<String, Node> param : parameters.entrySet()) {
                sparqlString.setParam(param.getKey(), param.getValue());
            }
            QueryExecution queryExecution = this.createQueryExecution(sparqlString.asQuery());
            return queryExecution.execDescribe();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public Boolean executeAsk(String queryPath, Map<String, Node> parameters) {
        ParameterizedSparqlString sparqlString = null;
        try {
            sparqlString = new ParameterizedSparqlString(IOUtils.toString(new FileInputStream(queryPath)));
            for (Map.Entry<String, Node> param : parameters.entrySet()) {
                sparqlString.setParam(param.getKey(), param.getValue());
            }
            QueryExecution queryExecution = this.createQueryExecution(sparqlString.asQuery());
            return queryExecution.execAsk();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a Jena QueryExecution instance
     * @param query source for the queryExecution which is going to be created
     * @return Query execution
     */
    protected abstract QueryExecution createQueryExecution(Query query);
}
