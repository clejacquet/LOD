package jp.kde.lod.jacquet.mediaselector.controller;

import org.json.JSONObject;

/**
 * Created by Clement on 16/05/2015.
 */
public interface JSONCommand extends ServletSubject {
    JSONObject process();
}
