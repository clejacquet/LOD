package jp.kde.lod.jacquet.jenamedialite.data;

import com.hp.hpl.jena.rdf.model.RDFNode;
import org.mymedialite.data.WeightedItem;

/**
 * Created by Clement on 25/05/2015.
 */
public class RDFWeightedItem implements Comparable<RDFWeightedItem> {

    /** Item ID */
    public RDFNode item;

    /** Weight */
    public Double weight;

    /** Default constructor */
    public RDFWeightedItem() {}

    /**
     * Constructor
     * @param item the item node
     * @param weight the weight
     */
    public RDFWeightedItem(RDFNode item, double weight) {
        this.item = item;
        this.weight = weight;
    }

    public int compareTo(RDFWeightedItem otherItem) {
        return this.weight.compareTo(otherItem.weight);
    }

    public boolean equals(RDFWeightedItem otherItem) {
        if (otherItem == null) return false;
        return Math.abs(this.weight - otherItem.weight) < 0.000001;
    }

    public int getHashCode() {
        return weight.hashCode();
    }

}
