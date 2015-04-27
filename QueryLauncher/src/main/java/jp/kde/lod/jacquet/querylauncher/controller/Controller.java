package jp.kde.lod.jacquet.querylauncher.controller;

import jp.kde.lod.jacquet.access.Access;
import jp.kde.lod.jacquet.querylauncher.model.QueryStorage;
import jp.kde.lod.jacquet.querylauncher.view.QueryDisplay;

/**
 * Created by Clement on 16/04/2015.
 * Controller of the application, in respect of the MVC pattern
 */
public interface Controller {
    /**
     * Give to the controller the SPARQL end point where queries are send to.
     * @param endPoint SPARQL end point
     */
    void setAccess(Access endPoint);

    /**
     * Give to the controller a place where data should be sent in order to be displayed
     * @param queryDisplay Something which can display the result of a query
     */
    void setQueryDisplay(QueryDisplay queryDisplay);

    /**
     * Give to the controller a query storage where it can load SPARQL queries
     * @param queryStorage Something which contain some SPARQL queries
     */
    void setQueryStorage(QueryStorage queryStorage);

    /**
     * Tell the controller that it has to execute the query with the following name, and display it in its display
     * @param queryName name of the query to launch
     */
    void executeQuery(String queryName);
}
