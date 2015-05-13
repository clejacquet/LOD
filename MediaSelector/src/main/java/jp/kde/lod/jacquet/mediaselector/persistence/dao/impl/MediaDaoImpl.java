package jp.kde.lod.jacquet.mediaselector.persistence.dao.impl;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import jp.kde.lod.jacquet.access.Access;
import jp.kde.lod.jacquet.access.ModelAccess;
import jp.kde.lod.jacquet.access.UpdateAccess;
import jp.kde.lod.jacquet.access.VirtAccess;
import jp.kde.lod.jacquet.mediaselector.persistence.dao.MediaDao;
import jp.kde.lod.jacquet.mediaselector.rdf.Media;
import org.apache.commons.io.IOUtils;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 12/05/2015.
 */
public class MediaDaoImpl implements MediaDao {

    private static VirtModel getMediaModel() {
        return new VirtModel(new VirtGraph("http://mediaselector.com/rdf", "jdbc:virtuoso://localhost:1111", "dba", "dba"));
    }

    @Override
    public long getMediaCounter() {
        Access access = new VirtAccess(MediaDaoImpl.getMediaModel());
        Query query = QueryFactory.read("/sparql/media/get-media-counter.rq");
        ResultSet resultSet = access.executeSelect(query);

        if (resultSet.getRowNumber() > 0) {
            QuerySolution solution = resultSet.next();
            Literal literal = solution.getLiteral("media_count_value");
            return literal.getLong();
        }

        return -1L;
    }

    @Override
    public void setMediaCounter(long counter) {
        Model model = MediaDaoImpl.getMediaModel();

        Map<String, Node> parameters = new HashMap<String, Node>();
        parameters.put("", NodeFactory.createLiteral(Long.toString(counter), XSDDatatype.XSDlong));

        UpdateAccess updateAccess = new ModelAccess(model);
        try {
            updateAccess.execute(IOUtils.toString(new FileInputStream("/sparqlu/media/set-media-counter.ru")), parameters);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long saveMedia(Media media) {
        Model virtModel = MediaDaoImpl.getMediaModel();



        return 0;
    }

    @Override
    public Media getMedia(long mediaId) {
        return null;
    }

    @Override
    public Media getMedia(String mediaId) {
        return this.getMedia(Integer.parseInt(mediaId));
    }
}
