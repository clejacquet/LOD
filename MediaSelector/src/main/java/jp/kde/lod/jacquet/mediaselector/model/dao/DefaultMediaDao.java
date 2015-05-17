package jp.kde.lod.jacquet.mediaselector.model.dao;

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
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.rdf.MainResourceType;
import jp.kde.lod.jacquet.mediaselector.model.rdf.Media;
import jp.kde.lod.jacquet.mediaselector.model.rdf.RelatedResourceType;
import jp.kde.lod.jacquet.mediaselector.model.rdf.Resource;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import org.apache.commons.io.IOUtils;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 12/05/2015.
 */
public class DefaultMediaDao extends BaseServletSubject implements MediaDao {

    private static VirtModel getMediaModel() {
        return new VirtModel(new VirtGraph("http://mediaselector.com/rdf", "jdbc:virtuoso://localhost:1111", "dba", "dba"));
    }

    @Override
    public long getMediaCounter() {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        Access access = new ModelAccess(model);
        Query query = QueryFactory.read("/sparql/get-media-counter.rq");
        ResultSet resultSet = access.executeSelect(query);

        if (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            Literal literal = solution.getLiteral("media_count_value");
            return literal.getLong();
        }

        return -1L;
    }

    @Override
    public void setMediaCounter(long counter) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        String updateFile = super.getHandler().getAbsolutePath("/WEB-INF/classes/sparqlu/set-media-counter.ru");

        Map<String, Node> parameters = new HashMap<>();
        parameters.put("media_count_value", NodeFactory.createLiteral(Long.toString(counter), XSDDatatype.XSDlong));

        UpdateAccess updateAccess = new ModelAccess(model);

        FileInputStream ifstream;
        try {
            ifstream = new FileInputStream(updateFile);
            updateAccess.execute(IOUtils.toString(ifstream), parameters);
            ifstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long getRelativeCounter(String queryFile, long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        Map<String, Node> parameters = new HashMap<>();
        parameters.put("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));

        Access access = new ModelAccess(model);

        ResultSet resultSet = access.executeSelect(queryFile, parameters);

        if (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            Literal literal = solution.getLiteral("value");
            return literal.getLong();
        }

        return -1L;
    }

    private void setRelativeCounter(long counter, String updateFile, long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        Map<String, Node> parameters = new HashMap<>();
        parameters.put("relative_resource_type_count_value", NodeFactory.createLiteral(Long.toString(counter), XSDDatatype.XSDlong));
        parameters.put("relative_resource_type_counter", NodeFactory.createURI("http://mediaselector.com/media/" + mediaId + "/counter"));
        parameters.put("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));

        UpdateAccess updateAccess = new ModelAccess(model);

        FileInputStream ifstream;
        try {
            ifstream = new FileInputStream(updateFile);
            updateAccess.execute(IOUtils.toString(ifstream), parameters);
            ifstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveMedia(Media media) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        String mediaFile = super.getHandler().getAbsolutePath("/WEB-INF/classes/sparqlu/insert-media.ru");
        String mainResourceTypeFile = super.getHandler().getAbsolutePath("/WEB-INF/classes/sparqlu/insert-main-resource-type.ru");
        String relatedResourceTypeFile = super.getHandler().getAbsolutePath("/WEB-INF/classes/sparqlu/insert-related-resource-type.ru");
        String relativeResourceQueryFile = super.getHandler().getAbsolutePath("/WEB-INF/classes/sparql/get-relative-resource-type-counter.rq");
        String relativeResourceUpdateFile = super.getHandler().getAbsolutePath("/WEB-INF/classes/sparqlu/set-relative-resource-type-counter.ru");

        FileInputStream ifstream;
        try {
            ifstream = new FileInputStream(mediaFile);
            media.save(model, IOUtils.toString(ifstream));
            ifstream.close();

            ifstream = new FileInputStream(mainResourceTypeFile);
            media.getMainResource().save(model, IOUtils.toString(ifstream));
            ifstream.close();

            ifstream = new FileInputStream(relatedResourceTypeFile);
            String relatedResourceTextCommand = IOUtils.toString(ifstream);
            for (RelatedResourceType relatedResource : media.getRelatedResource()) {
                long relativeResourceTypeCounter = getRelativeCounter(relativeResourceQueryFile, relatedResource.getMedia().getId()) + 1L;
                relatedResource.setId(relativeResourceTypeCounter);
                setRelativeCounter(relativeResourceTypeCounter, relativeResourceUpdateFile, relatedResource.getMedia().getId());

                relatedResource.save(model, relatedResourceTextCommand);
            }
            ifstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Media getMedia(long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        String mediaQueryFile = super.getHandler().getAbsolutePath("/WEB-INF/classes/sparql/get-media.rq");
        String mainResourceQueryFile = super.getHandler().getAbsolutePath("/WEB-INF/classes/sparql/get-main-resource-type.rq");
        String relatedResourceQueryFile = super.getHandler().getAbsolutePath("/WEB-INF/classes/sparql/get-related-resource-type.rq");

        Media media = new Media();
        media.setId(mediaId);

        Access access = new ModelAccess(model);

        Map<String, Node> mediaParameters = new HashMap<>();
        mediaParameters.put("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));
        ResultSet mediaResults = access.executeSelect(mediaQueryFile, mediaParameters);
        if (mediaResults.hasNext()) {
            QuerySolution solution = mediaResults.next();
            media.setName(solution.getLiteral("media_name").getString());
            media.setSparqlEndPoint(solution.getResource("media_end_point").getURI());
        }

        MainResourceType mainResourceType = new MainResourceType();
        mainResourceType.setMedia(media);
        ResultSet mainResourceTypeResult = access.executeSelect(mainResourceQueryFile, mediaParameters);
        if (mainResourceTypeResult.hasNext()) {
            QuerySolution solution = mainResourceTypeResult.next();
            mainResourceType.setName(solution.getLiteral("main_resource_name").getString());
            mainResourceType.setTypeIri(solution.getResource("main_resource_type").getURI());
        }
        media.setMainResource(mainResourceType);

        RelatedResourceType relatedResourceType = new RelatedResourceType();
        relatedResourceType.setMedia(media);
        ResultSet relatedResourceTypeResult = access.executeSelect(relatedResourceQueryFile, mediaParameters);
        if (relatedResourceTypeResult.hasNext()) {
            QuerySolution solution = relatedResourceTypeResult.next();
            relatedResourceType.setName(solution.getLiteral("related_resource_name").getString());
            relatedResourceType.setTypeIri(solution.getResource("related_resource_type").getURI());
            relatedResourceType.setRelation(solution.getResource("related_resource_relation").getURI());
        }
        media.getRelatedResource().add(relatedResourceType);

        return media;
    }

    @Override
    public List<Media> searchMedia(String searchText) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        String searchQueryFile = super.getHandler().getAbsolutePath("/WEB-INF/classes/sparql/search-media.rq");

        List<Media> medias = new ArrayList<>();
        Access access = new ModelAccess(model);

        Map<String, Node> parameters = new HashMap<>();
        parameters.put("search_text", NodeFactory.createLiteral(searchText));

        ResultSet results = access.executeSelect(searchQueryFile, parameters);

        while (results.hasNext()) {
            QuerySolution solution = results.next();
            Literal literal = solution.getLiteral("media_id");
            medias.add(this.getMedia(literal.getLong()));
        }

        return medias;
    }

    @Override
    public List<Resource> searchResource(String searchText, long mediaId) {
        return null;
    }

    private static void InitGraph(Model model) {
        if (model.isEmpty()) {
            try {
                model.read("/rdf/init.ttl", "TURTLE");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
