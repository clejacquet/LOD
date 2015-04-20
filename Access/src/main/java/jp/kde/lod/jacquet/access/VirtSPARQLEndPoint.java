package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 * Created by Clement on 16/04/2015.
 */
public class VirtSPARQLEndPoint implements SPARQLEndPoint {
    private VirtGraph graph;

    public VirtSPARQLEndPoint(VirtGraph graph) {
        this.graph = graph;
    }

    public ResultSet executeSelect(Query query) {
        return this.createQueryExecution(query).execSelect();
    }

    public Model executeConstruct(Query query) {
        return this.createQueryExecution(query).execConstruct();
    }

    public Model executeDescribe(Query query) {
        return this.createQueryExecution(query).execDescribe();
    }

    public boolean executeAsk(Query query) {
        return this.createQueryExecution(query).execAsk();
    }

    private VirtuosoQueryExecution createQueryExecution(Query query) {
        return VirtuosoQueryExecutionFactory.create(query, this.graph);
    }
}
