package jp.kde.lod.jacquet.mediaselector.model;

import com.hp.hpl.jena.update.UpdateRequest;

/**
 * Created by Clement on 18/05/2015.
 */
public interface UpdateStorage extends Storage {
    UpdateRequest getUpdateRequest(String name);
}
