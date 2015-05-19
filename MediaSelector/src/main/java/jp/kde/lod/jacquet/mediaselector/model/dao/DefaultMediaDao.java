package jp.kde.lod.jacquet.mediaselector.model.dao;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import jp.kde.lod.jacquet.access.Access;
import jp.kde.lod.jacquet.access.EndPointAccess;
import jp.kde.lod.jacquet.access.ModelAccess;
import jp.kde.lod.jacquet.access.UpdateAccess;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.mediaselector.model.domain.*;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 12/05/2015.
 */
public class DefaultMediaDao extends BaseServletSubject implements MediaDao {

    private static Map<String, String> LANGUAGE_BINDING = new HashMap<>();

    static {
        LANGUAGE_BINDING.put("None", "");
        LANGUAGE_BINDING.put("French", "fr");
        LANGUAGE_BINDING.put("English", "en");
        LANGUAGE_BINDING.put("Japanese", "jp");
        LANGUAGE_BINDING.put("Spanish", "es");
    }

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
            media.setAuthor(super.getHandler().getDaoProvider().getUserDao().getUser(solution.getLiteral("author_id").getLong()));
        }

        MainResourceType mainResourceType = new MainResourceType();
        mainResourceType.setMedia(media);
        mainResourceQuery.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));
        ResultSet mainResourceTypeResult = access.executeSelect(mainResourceQuery.asQuery());
        if (mainResourceTypeResult.hasNext()) {
            QuerySolution solution = mainResourceTypeResult.next();
            mainResourceType.setName(solution.getLiteral("main_resource_name").getString());
            mainResourceType.setTypeUri(solution.getResource("main_resource_type").getURI());
            mainResourceType.setTitlePropertyUri(solution.getResource("title_property").getURI());
            media.setMainResource(mainResourceType);
        }

        RelatedResourceType relatedResourceType = new RelatedResourceType();
        relatedResourceType.setMedia(media);
        relatedResourceQuery.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));
        ResultSet relatedResourceTypeResult = access.executeSelect(relatedResourceQuery.asQuery());
        while (relatedResourceTypeResult.hasNext()) {
            QuerySolution solution = relatedResourceTypeResult.next();
            relatedResourceType.setName(solution.getLiteral("related_resource_name").getString());
            relatedResourceType.setTypeUri(solution.getResource("related_resource_type").getURI());
            relatedResourceType.setRelation(solution.getResource("related_resource_relation").getURI());
            relatedResourceType.setTitlePropertyUri(solution.getResource("title_property").getURI());
            media.getRelatedResource().add(relatedResourceType);
        }

        return media;
    }

    @Override
    public List<Media> searchMedia(String searchText) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString searchSparqlString = super.getHandler().getQueryStorage().getSparqlString("searchMedia");

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
    public List<MainResource> searchResource(String searchText, String language, long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        Media media = this.getMedia(mediaId);

        String sparqlEndPoint = media.getSparqlEndPoint();
        Access mediaAccess = new EndPointAccess(sparqlEndPoint);

        ParameterizedSparqlString searchSparqlString;
        if (language.equals("All")) {
            searchSparqlString = super.getHandler().getQueryStorage().getSparqlString("searchResources");
        } else {
            searchSparqlString = super.getHandler().getQueryStorage().getSparqlString("searchResourcesLang");
            searchSparqlString.setParam("language", NodeFactory.createLiteral(LANGUAGE_BINDING.get(language)));
        }

        searchSparqlString.setParam("search_text", NodeFactory.createLiteral(searchText));
        searchSparqlString.setParam("resource_type", NodeFactory.createURI(media.getMainResource().getTypeUri()));
        searchSparqlString.setParam("title_property", NodeFactory.createURI(media.getMainResource().getTitlePropertyUri()));

        ResultSet results = mediaAccess.executeSelect(searchSparqlString.asQuery());

        List<MainResource> resources = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution solution = results.next();
            MainResource mainResource = new MainResource();
            mainResource.setName(solution.getLiteral("name").getString());
            mainResource.setUri(solution.getResource("uri").getURI());
            resources.add(mainResource);
        }

        return resources;
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

    @Override
    public void saveUser(User user) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString sparqlString = super.getHandler().getUpdateStorage().getSparqlString("saveUser");
        sparqlString.setParam("user", NodeFactory.createURI("http://mediaselector.com/user/" + user.getId()));
        sparqlString.setParam("user_id", NodeFactory.createLiteral(Long.toString(user.getId()), XSDDatatype.XSDlong));
        sparqlString.setParam("user_login", NodeFactory.createLiteral(user.getLogin()));

        UpdateAccess updateAccess = new ModelAccess(model);
        updateAccess.execute(sparqlString.asUpdate());
    }
}
