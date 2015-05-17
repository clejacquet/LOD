package jp.kde.lod.jacquet.pageprocessing;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Clement on 15/05/2015.
 */
public interface View {
    void addParameter(String parameter, Object value);
    Map<String, Object> getParameters();

    String generate();
}
