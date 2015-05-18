package jp.kde.lod.jacquet.mediaselector.model.storage;

import com.hp.hpl.jena.update.UpdateRequest;
import jp.kde.lod.jacquet.mediaselector.model.UpdateStorage;

/**
 * Created by Clement on 18/05/2015.
 */
public class DefaultUpdateStorage extends BaseStorage implements UpdateStorage {
    @Override
    public UpdateRequest getUpdateRequest(String name) {
        return super.getSparqlString(name).copy().asUpdate();
    }
}
