package jp.kde.lod.jacquet.querylauncher.view;

import com.hp.hpl.jena.query.ResultSet;

/**
 * Created by Clement on 16/04/2015.
 */
public interface QueryDisplay {
    void displayQueryResults(ResultSet resultSet);
}
