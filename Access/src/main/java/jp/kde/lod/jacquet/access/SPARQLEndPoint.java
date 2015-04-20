package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Created by Clement on 16/04/2015.
 */
public interface SPARQLEndPoint {
    ResultSet executeSelect(Query query);
    Model executeConstruct(Query query);
    Model executeDescribe(Query query);
    boolean executeAsk(Query query);
}
