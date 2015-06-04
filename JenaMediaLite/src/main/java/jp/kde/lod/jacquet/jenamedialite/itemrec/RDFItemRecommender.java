package jp.kde.lod.jacquet.jenamedialite.itemrec;

import com.hp.hpl.jena.rdf.model.RDFNode;
import jp.kde.lod.jacquet.jenamedialite.data.IRDFPosOnlyFeedback;
import org.mymedialite.itemrec.ItemRecommender;

/**
 * Created by Clement on 27/05/2015.
 */
public class RDFItemRecommender extends RDFRecommenderBase {
    private IRDFPosOnlyFeedback rdfFeedback;
    private ItemRecommender itemRecommender;

    public void setItemRecommender(ItemRecommender recommender) {
        super.setRecommender(recommender);
        this.itemRecommender = recommender;
    }

    public void setRDFFeedback(IRDFPosOnlyFeedback feedback) {
        this.rdfFeedback = feedback;
        if (this.itemRecommender != null) {
            this.itemRecommender.setFeedback(feedback);
        }
    }

    @Override
    public double predict(RDFNode user, RDFNode item) {
        Integer userId = rdfFeedback.getUserId(user);
        Integer itemId = rdfFeedback.getUserId(item);
        return this.itemRecommender.predict(userId, itemId);
    }
}
