package jp.kde.lod.jacquet.movielensloader;

import com.hp.hpl.jena.update.UpdateRequest;

/**
 * Created by Clement on 18/05/2015.
 */
public class UpdateStorage extends BaseStorage {
    public UpdateStorage() {
        super.add("saveUser", "save-user.ru");
        super.add("insertRate", "insert-rate.ru");
        super.add("deleteRate", "delete-rate.ru");
    }

    public UpdateRequest getUpdateRequest(String name) {
        return super.getSparqlString(name).copy().asUpdate();
    }
}
