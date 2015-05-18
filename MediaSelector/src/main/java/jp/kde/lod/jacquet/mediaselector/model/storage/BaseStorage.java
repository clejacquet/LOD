package jp.kde.lod.jacquet.mediaselector.model.storage;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import jp.kde.lod.jacquet.mediaselector.model.Storage;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 18/05/2015.
 */
public class BaseStorage implements Storage {
    Map<String, ParameterizedSparqlString> sparqlStrings;

    public BaseStorage() {
        this.sparqlStrings = new HashMap<>();
    }

    @Override
    public void add(String name, String file) {
        try {
            InputStream fileStream = new FileInputStream(file);
            this.sparqlStrings.put(name, new ParameterizedSparqlString(IOUtils.toString(fileStream)));
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ParameterizedSparqlString getSparqlString(String name) {
        return this.sparqlStrings.get(name).copy();
    }
}
