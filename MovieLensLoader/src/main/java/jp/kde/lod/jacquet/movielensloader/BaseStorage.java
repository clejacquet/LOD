package jp.kde.lod.jacquet.movielensloader;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 18/05/2015.
 */
public abstract class BaseStorage {
    Map<String, ParameterizedSparqlString> sparqlStrings;

    public BaseStorage() {
        this.sparqlStrings = new HashMap<>();
    }

    public void add(String name, String file) {
        try {
            InputStream fileStream = new FileInputStream(file);
            this.sparqlStrings.put(name, new ParameterizedSparqlString(IOUtils.toString(fileStream)));
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParameterizedSparqlString getSparqlString(String name) {
        return this.sparqlStrings.get(name).copy();
    }
}
