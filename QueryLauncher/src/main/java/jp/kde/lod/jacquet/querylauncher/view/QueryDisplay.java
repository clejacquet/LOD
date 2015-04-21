package jp.kde.lod.jacquet.querylauncher.view;

import com.hp.hpl.jena.query.ResultSet;

/**
 * Created by Clement on 16/04/2015.
 * Display the result of a SPARQL query
 */
public interface QueryDisplay {
    /**
     * display the result of a SPARQL query
     * @param resultSet results of a SPARQL query which are going to be displayed
     */
    void displayQueryResults(ResultSet resultSet);
}
