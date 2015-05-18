package jp.kde.lod.jacquet.mediaselector.model.rdf;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import jp.kde.lod.jacquet.access.ModelAccess;
import virtuoso.jena.driver.VirtModel;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.util.Map;

/**
 * Created by Clement on 16/04/2015.
 * Virtuoso SPARQL access to query a Virtuoso server
 */
public class VirtAccess extends ModelAccess {

    /**
     * Construct a Virtuoso SPARQL Access from a Virtuoso model
     * @param model Virtuoso model
     */
    public VirtAccess(VirtModel model) {
        super(model);
    }

    /**
     * Create a Jena QueryExecution instance
     * @param query source for the queryExecution which is going to be created
     * @return Query execution
     */
    @Override
    protected QueryExecution createQueryExecution(Query query) {
        return VirtuosoQueryExecutionFactory.create(query, super.getModel());
    }
}
