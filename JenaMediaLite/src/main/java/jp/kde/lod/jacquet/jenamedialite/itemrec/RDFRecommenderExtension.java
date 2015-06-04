package jp.kde.lod.jacquet.jenamedialite.itemrec;

import com.hp.hpl.jena.rdf.model.RDFNode;
import jp.kde.lod.jacquet.jenamedialite.data.RDFWeightedItem;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.*;

/**
 * Created by Clement on 25/05/2015.
 */
public class RDFRecommenderExtension {
    public static List<Pair<RDFNode, Double>> predictItemsWithWeight(IRDFRecommender recommender, RDFNode user, Collection<RDFNode> candidate_items, int predictionCount) {
        ArrayList<RDFWeightedItem> result = new ArrayList<>(candidate_items.size());
        for (RDFNode item : candidate_items) {
            double predict = recommender.predict(user, item);
            result.add(new RDFWeightedItem(item, predict));
        }
        Collections.sort(result, Collections.reverseOrder());

        List<Pair<RDFNode, Double>> return_array = new ArrayList<>(result.size());
        for (int i = 0; i < result.size() && i < predictionCount; i++) {
            return_array.add(i, new ImmutablePair<>(result.get(i).item, result.get(i).weight));
        }

        return return_array;
    }

    public static PredictionInfo predictItemRating(IRDFRecommender recommender, RDFNode user, RDFNode item, Collection<RDFNode> candidate_items) {
        double[] values = new double[candidate_items.size()];
        int counter = 0;

        for (RDFNode candidate : candidate_items) {
            double predict = recommender.predict(user, candidate);
            values[counter++] = predict;
        }

        DescriptiveStatistics statistics = new DescriptiveStatistics(values);

        double itemPredict = recommender.predict(user, item);

        PredictionInfo info = new PredictionInfo();
        info.setStatistics(statistics);
        info.setWeight(itemPredict);

        return info;
    }
}
