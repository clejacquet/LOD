package jp.kde.lod.jacquet.mediaselector.model;

import org.json.JSONObject;

/**
 * Created by Clement on 18/05/2015.
 */
public interface JSONModel {
    void loadJSON(JSONObject jsonObject);
    JSONObject toJSON();
}
