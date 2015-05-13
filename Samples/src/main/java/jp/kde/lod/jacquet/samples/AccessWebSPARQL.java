package jp.kde.lod.jacquet.samples;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.graph.GraphFactory;
import jp.kde.lod.jacquet.access.EndPointAccess;
import jp.kde.lod.jacquet.access.Access;


/**
 * Created by Clement on 27/04/2015.
 */
public class AccessWebSPARQL {
    public static void main(String[] args) {
        Access access = new EndPointAccess("http://localhost:8890/sparql");

        Query query = QueryFactory.create("select distinct ?Concept where {[] a ?Concept} LIMIT 100");
        ResultSet results = access.executeSelect(query);
        ResultSetFormatter.out(results);
    }
}
