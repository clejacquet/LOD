package jp.kde.lod.jacquet.jenamedialite.data;

import com.hp.hpl.jena.rdf.model.RDFNode;
import org.mymedialite.data.IPosOnlyFeedback;

/**
 * Created by Clement on 25/05/2015.
 */
public interface IRDFPosOnlyFeedback extends IPosOnlyFeedback {
    /**
     * Add a user-item event to the data structure.
     * @param user the user node
     * @param item the item node
     */
    void add(RDFNode user, RDFNode item);

    /**
     * Remove a user-item event from the data structure.
     * @param user the user node
     * @param item the item node
     */
    void remove(RDFNode user, RDFNode item);

    Integer getUserId(RDFNode user);
    Integer getItemId(RDFNode item);
}
