package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.graph.Node;

import java.util.Map;

/**
 * Created by Clement on 13/05/2015.
 */
public interface UpdateAccess extends Access {
    /**
     * run an update query (useful for insert / delete queries)
     * @param commandText text containing the update query
     * @param parameters parameters for the update query
     */
    void execute(String commandText, Map<String, Node> parameters);
}
