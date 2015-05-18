package jp.kde.lod.jacquet.mediaselector.model;

import com.hp.hpl.jena.query.Query;

/**
 * Created by Clement on 18/05/2015.
 */
public interface QueryStorage extends Storage {
    Query getQuery(String name);
}
