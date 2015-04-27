package jp.kde.lod.jacquet.samples;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import jp.kde.lod.jacquet.access.ModelAccess;
import jp.kde.lod.jacquet.access.Access;


/**
 * Created by Clement on 27/04/2015.
 */
public class AccessWebSPARQL {
    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        model.read("http://sws.geonames.org/3017382/contains.rdf");
        Access access = new ModelAccess(model);

        Query query = QueryFactory.read("src/main/resources/query.rq");
        ResultSet results = access.executeSelect(query);
        ResultSetFormatter.out(results);


    }
}
