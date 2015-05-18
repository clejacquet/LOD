package jp.kde.lod.jacquet.mediaselector.model.dao;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.*;
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
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

import java.util.ArrayList;
import java.util.List;

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

        ParameterizedSparqlString updateSparqlString = super.getHandler().getUpdateStorage().getSparqlString("setMediaCounter");
        updateSparqlString.setParam("media_count_value", NodeFactory.createLiteral(Long.toString(counter), XSDDatatype.XSDlong));

        UpdateAccess updateAccess = new ModelAccess(model);
        updateAccess.execute(updateSparqlString.asUpdate());
    }

    private long getRelatedCounter(long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString querySparqlString = super.getHandler().getQueryStorage().getSparqlString("getRelatedResourceTypeCounter");
        querySparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));

        Access access = new ModelAccess(model);
        ResultSet resultSet = access.executeSelect(querySparqlString.asQuery());

        if (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            Literal literal = solution.getLiteral("value");
            return literal.getLong();
        }

        return -1L;
    }

    private void setRelatedCounter(long counter, long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString updateSparqlString = super.getHandler().getUpdateStorage().getSparqlString("setRelatedResourceTypeCounter");
        updateSparqlString.setParam("relative_resource_type_count_value", NodeFactory.createLiteral(Long.toString(counter), XSDDatatype.XSDlong));
        updateSparqlString.setParam("relative_resource_type_counter", NodeFactory.createURI("http://mediaselector.com/media/" + mediaId + "/counter"));
        updateSparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));

        UpdateAccess updateAccess = new ModelAccess(model);
        updateAccess.execute(updateSparqlString.asUpdate());
    }

    @Override
    public void saveMedia(Media media) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString mediaSparqlString = super.getHandler().getUpdateStorage().getSparqlString("insertMedia");
        ParameterizedSparqlString mainResourceTypeSparqlString = super.getHandler().getUpdateStorage().getSparqlString("insertMainResourceType");
        ParameterizedSparqlString relatedResourceTypeSparqlString = super.getHandler().getUpdateStorage().getSparqlString("insertRelatedResourceType");

        media.save(model, mediaSparqlString);
        media.getMainResource().save(model, mainResourceTypeSparqlString);
        for (RelatedResourceType relatedResource : media.getRelatedResource()) {
            long relativeResourceTypeCounter = getRelatedCounter(relatedResource.getMedia().getId()) + 1L;
            relatedResource.setId(relativeResourceTypeCounter);
            setRelatedCounter(relativeResourceTypeCounter, relatedResource.getMedia().getId());

            relatedResource.save(model, relatedResourceTypeSparqlString);
        }
    }

    @Override
    public Media getMedia(long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString mediaQuery = super.getHandler().getQueryStorage().getSparqlString("getMedia");
        ParameterizedSparqlString mainResourceQuery = super.getHandler().getQueryStorage().getSparqlString("getMainResourceType");
        ParameterizedSparqlString relatedResourceQuery = super.getHandler().getQueryStorage().getSparqlString("getRelatedResourceType");

        Media media = new Media();
        media.setId(mediaId);

        Access access = new ModelAccess(model);

        mediaQuery.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));
        ResultSet mediaResults = access.executeSelect(mediaQuery.asQuery());
        if (mediaResults.hasNext()) {
            QuerySolution solution = mediaResults.next();
            media.setName(solution.getLiteral("media_name").getString());
            media.setSparqlEndPoint(solution.getResource("media_end_point").getURI());
        }

        MainResourceType mainResourceType = new MainResourceType();
        mainResourceType.setMedia(media);
        mainResourceQuery.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));
        ResultSet mainResourceTypeResult = access.executeSelect(mainResourceQuery.asQuery());
        if (mainResourceTypeResult.hasNext()) {
            QuerySolution solution = mainResourceTypeResult.next();
            mainResourceType.setName(solution.getLiteral("main_resource_name").getString());
            mainResourceType.setTypeIri(solution.getResource("main_resource_type").getURI());
        }
        media.setMainResource(mainResourceType);

        RelatedResourceType relatedResourceType = new RelatedResourceType();
        relatedResourceType.setMedia(media);
        relatedResourceQuery.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));
        ResultSet relatedResourceTypeResult = access.executeSelect(relatedResourceQuery.asQuery());
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

        ParameterizedSparqlString searchSparqlString = super.getHandler().getQueryStorage().getSparqlString("/sparql/search-media.rq");

        List<Media> medias = new ArrayList<>();
        Access access = new ModelAccess(model);

        searchSparqlString.setParam("search_text", NodeFactory.createLiteral(searchText));

        ResultSet results = access.executeSelect(searchSparqlString.asQuery());

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
