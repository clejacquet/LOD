package jp.kde.lod.jacquet.querylauncher.controller;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;
import jp.kde.lod.jacquet.access.SPARQLEndPoint;
import jp.kde.lod.jacquet.querylauncher.model.QueryStorage;
import jp.kde.lod.jacquet.querylauncher.view.QueryDisplay;
import org.apache.log4j.Logger;

/**
 * Created by Clement on 16/04/2015.
 */
public class DefaultController implements Controller {
    private static final Logger LOGGER = Logger.getLogger(DefaultController.class);

    private SPARQLEndPoint SPARQLEndPoint;
    private QueryDisplay queryDisplay;
    private QueryStorage queryStorage;

    public void setSPARQLEndPoint(SPARQLEndPoint endPoint) {
        this.SPARQLEndPoint = endPoint;
    }

    public void setQueryDisplay(QueryDisplay queryDisplay) {
        this.queryDisplay = queryDisplay;
    }

    public void setQueryStorage(QueryStorage queryStorage) {
        this.queryStorage = queryStorage;
    }

    public void putQuery(String queryName, Query query) {
        this.queryStorage.putQuery(queryName, query);
        LOGGER.debug("Query \"" + queryName + "\" set");
    }

    public void executeQuery(String queryName) {
        LOGGER.debug("Query \"" + queryName + "\" execution begin ...");
        Query query = this.queryStorage.getQuery(queryName);

        LOGGER.debug("Fetching query result ...");
        ResultSet resultSet = this.SPARQLEndPoint.executeSelect(query);
        LOGGER.debug("Fetch succeed");

        this.queryDisplay.displayQueryResults(resultSet);
    }
}
