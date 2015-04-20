package jp.kde.lod.jacquet.querylauncher.controller;

import com.hp.hpl.jena.query.Query;
import jp.kde.lod.jacquet.access.SPARQLEndPoint;
import jp.kde.lod.jacquet.querylauncher.model.QueryStorage;
import jp.kde.lod.jacquet.querylauncher.view.QueryDisplay;

/**
 * Created by Clement on 16/04/2015.
 */
public interface Controller {
    void setSPARQLEndPoint(SPARQLEndPoint endPoint);
    void setQueryDisplay(QueryDisplay queryDisplay);
    void setQueryStorage(QueryStorage queryStorage);
    void putQuery(String queryName, Query query);
    void executeQuery(String queryName);
}
