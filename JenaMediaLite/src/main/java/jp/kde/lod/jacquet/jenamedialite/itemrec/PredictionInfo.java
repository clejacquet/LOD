package jp.kde.lod.jacquet.jenamedialite.itemrec;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Created by Clement on 28/05/2015.
 */
public class PredictionInfo {
    public static final double DISLIKE_PERCENTILE = 70.0;
    public static final double LIKE_PERCENTILE = 85.0;
    public static final double LOVE_PERCENTILE = 95.0;

    private DescriptiveStatistics statistics;
    private double weight;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public DescriptiveStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(DescriptiveStatistics statistics) {
        this.statistics = statistics;
    }
}
