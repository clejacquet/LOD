package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.update.UpdateAction;

import java.util.Map;

/**
 * Created by Clement on 27/04/2015.
 */
public class ModelAccess extends BaseAccess implements UpdateAccess {

    /**
     * Jena Model used as a SPARQL access
     */
    private Model model;

    /**
     * Default constructor
     */
    public ModelAccess() {

    }

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
     * run an update query (useful for insert / delete queries)
     * @param commandText text containing the update query
     * @param parameters parameters for the update query
     */
    public void execute(String commandText, Map<String, Node> parameters) {
        ParameterizedSparqlString sparqlString = new ParameterizedSparqlString(commandText);

        for (Map.Entry<String, Node> param : parameters.entrySet()) {
            sparqlString.setParam(param.getKey(), param.getValue());
        }

        UpdateAction.execute(sparqlString.asUpdate(), this.model);
    }

    /**
     * Create a Jena QueryExecution instance
     * @param query source for the queryExecution which is going to be created
     * @return Query execution
     */
    @Override
    protected QueryExecution createQueryExecution(Query query) {
        return QueryExecutionFactory.create(query, this.model);
    }
}
