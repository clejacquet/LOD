package jp.kde.lod.jacquet.querylauncher.controller;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;
import jp.kde.lod.jacquet.access.Access;
import jp.kde.lod.jacquet.querylauncher.model.QueryStorage;
import jp.kde.lod.jacquet.querylauncher.view.QueryDisplay;
import org.apache.log4j.Logger;

/**
 * Created by Clement on 16/04/2015.
 * Default Controller of the application, in respect of the MVC Pattern
 */
public class DefaultController implements Controller {
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(DefaultController.class);

    /**
     * SPARQL end point where queries are sent
     */
    private Access access;

    /**
     * Query display where results of queries are sent
     */
    private QueryDisplay queryDisplay;

    /**
     * Query storage where queries are saved
     */
    private QueryStorage queryStorage;

    /**
     * Give to the controller the SPARQL end point where queries are send to.
     * @param endPoint SPARQL end point
     */
    public void setAccess(final Access endPoint) {
        this.access = endPoint;
    }

    /**
     * Give to the controller a place where data should be sent in order to be displayed
     * @param queryDisplay Something which can display the result of a query
     */
    public void setQueryDisplay(final QueryDisplay queryDisplay) {
        this.queryDisplay = queryDisplay;
    }

    /**
     * Give to the controller a query storage where it can load SPARQL queries
     * @param queryStorage Something which contain some SPARQL queries
     */
    public void setQueryStorage(final QueryStorage queryStorage) {
        this.queryStorage = queryStorage;
    }

    /**
     * Tell the controller that it has to execute the query with the following name, and display it in its display
     * @param queryName name of the query to launch
     */
    public void executeQuery(final String queryName) {
        LOGGER.debug("Query \"" + queryName + "\" execution begin ...");
        Query query = this.queryStorage.getQuery(queryName);

        LOGGER.debug("Fetching query result ...");
        ResultSet resultSet = this.access.executeSelect(query);
        LOGGER.debug("Fetch succeed");

        this.queryDisplay.displayQueryResults(resultSet);
    }
}
