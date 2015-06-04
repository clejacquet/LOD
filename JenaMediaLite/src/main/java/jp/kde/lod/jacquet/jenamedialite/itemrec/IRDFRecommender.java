package jp.kde.lod.jacquet.jenamedialite.itemrec;

import com.hp.hpl.jena.rdf.model.RDFNode;
import org.mymedialite.IRecommender;

/**
 * Created by Clement on 25/05/2015.
 */
public interface IRDFRecommender extends IRecommender {
    double predict(RDFNode user, RDFNode item);
}
