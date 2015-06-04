package jp.kde.lod.jacquet.jenamedialite.data;

import com.hp.hpl.jena.rdf.model.RDFNode;
import org.mymedialite.data.PosOnlyFeedback;
import org.mymedialite.datatype.IBooleanMatrix;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 25/05/2015.
 */
public class RDFPosOnlyFeedback<T extends IBooleanMatrix> extends PosOnlyFeedback<T> implements IRDFPosOnlyFeedback {

    private Map<RDFNode, Integer> userMapping;
    private Map<RDFNode, Integer> itemMapping;

    private int userCount;
    private int itemCount;

    /**
     * Create a PosOnlyFeedback object.
     *
     * @param c the Class<T>
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public RDFPosOnlyFeedback(Class<T> c) throws InstantiationException, IllegalAccessException {
        super(c);
        this.userMapping = new HashMap<>();
        this.itemMapping = new HashMap<>();
        this.userCount = 0;
        this.itemCount = 0;
    }

    /**
     * Add a user-item event to the data structure.
     *
     * @param user the user node
     * @param item the item node
     */
    @Override
    public void add(RDFNode user, RDFNode item) {
        int userId, itemId;

        if (!this.userMapping.containsKey(user)) {
            userId = this.userCount++;
            this.userMapping.put(user, userId);
        } else {
            userId = this.userMapping.get(user);
        }

        if (!this.itemMapping.containsKey(item)) {
            itemId = this.itemCount++;
            this.itemMapping.put(item, itemId);
        } else {
            itemId = this.itemMapping.get(item);
        }

        super.add(userId, itemId);
    }

    /**
     * Remove a user-item event from the data structure.
     *
     * @param user the user node
     * @param item the item node
     */
    @Override
    public void remove(RDFNode user, RDFNode item) {
        if (this.userMapping.containsKey(user) && this.itemMapping.containsKey(item)) {
            int userId = this.userMapping.get(user);
            int itemId = this.itemMapping.get(item);
            super.remove(userId, itemId);
        }
    }

    @Override
    public Integer getUserId(RDFNode user) {
        Integer userId;
        if (!this.userMapping.containsKey(user)) {
            userId = this.userCount++;
            this.userMapping.put(user, userId);
        } else {
            userId = this.userMapping.get(user);
        }
        return userId;
    }

    @Override
    public Integer getItemId(RDFNode item) {
        return this.itemMapping.get(item);
    }
}
