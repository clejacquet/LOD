package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.update.UpdateRequest;

/**
 * Created by Clement on 13/05/2015.
 */
public interface UpdateAccess extends Access {
    /**
     * run an update query (useful for insert / delete queries)
     */
    void execute(UpdateRequest updateRequest);
}
