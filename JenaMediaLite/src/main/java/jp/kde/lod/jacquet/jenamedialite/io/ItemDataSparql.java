package jp.kde.lod.jacquet.jenamedialite.io;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import jp.kde.lod.jacquet.jenamedialite.data.IRDFPosOnlyFeedback;
import jp.kde.lod.jacquet.jenamedialite.data.RDFPosOnlyFeedback;
import org.mymedialite.datatype.SparseBooleanMatrix;

/**
 * Created by Clement on 25/05/2015.
 */
public class ItemDataSparql {
    static public IRDFPosOnlyFeedback read(ResultSet resultSet, String userColumnName, String itemColumnName) throws Exception {
        RDFPosOnlyFeedback<SparseBooleanMatrix> feedback = new RDFPosOnlyFeedback<>(SparseBooleanMatrix.class);

        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            feedback.add(solution.get(userColumnName), solution.get(itemColumnName));
        }

        return feedback;
    }
}
