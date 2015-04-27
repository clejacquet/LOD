package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Created by Clement on 16/04/2015.
 * Useful interface which describe an easy-to-use generic SPARQL access
 */
public interface Access {
    /**
     * return the result of a select query
     * @param query select query
     * @return result of the query
     */
    ResultSet executeSelect(Query query);

    /**
     * return the constructed graph from a construct query
     * @param query construct query
     * @return constructed graph
     */
    Model executeConstruct(Query query);

    /**
     * return the description graph from a describe query
     * @param query describe query
     * @return description graph
     */
    Model executeDescribe(Query query);

    /**
     * return the answer of an ask query
     * @param query ask query
     * @return answer of the ask query
     */
    boolean executeAsk(Query query);
}
