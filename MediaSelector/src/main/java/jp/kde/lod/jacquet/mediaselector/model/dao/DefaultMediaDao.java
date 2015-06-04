package jp.kde.lod.jacquet.mediaselector.model.dao;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import jp.kde.lod.jacquet.access.Access;
import jp.kde.lod.jacquet.access.EndPointAccess;
import jp.kde.lod.jacquet.access.ModelAccess;
import jp.kde.lod.jacquet.access.UpdateAccess;
import jp.kde.lod.jacquet.jenamedialite.data.IRDFPosOnlyFeedback;
import jp.kde.lod.jacquet.jenamedialite.io.ItemDataSparql;
import jp.kde.lod.jacquet.jenamedialite.itemrec.IRDFRecommender;
import jp.kde.lod.jacquet.jenamedialite.itemrec.PredictionInfo;
import jp.kde.lod.jacquet.jenamedialite.itemrec.RDFRecommenderExtension;
import jp.kde.lod.jacquet.jenamedialite.itemrec.RDFItemRecommender;
import jp.kde.lod.jacquet.mediaselector.model.MediaDao;
import jp.kde.lod.jacquet.jenamedialite.itemrec.Prediction;
import jp.kde.lod.jacquet.mediaselector.controller.command.BaseServletSubject;
import jp.kde.lod.jacquet.mediaselector.model.domain.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.mymedialite.itemrec.MostPopular;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

import java.util.*;

/**
 * Created by Clement on 12/05/2015.
 */
public class DefaultMediaDao extends BaseServletSubject implements MediaDao {

    private static Map<String, String> LANGUAGE_BINDING = new HashMap<>();
    private static final String DEFAULT_LANGUAGE = "English";

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

    @Override
    public void saveMedia(Media media) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString mediaSparqlString = super.getHandler().getUpdateStorage().getSparqlString("insertMedia");
        ParameterizedSparqlString mainResourceTypeSparqlString = super.getHandler().getUpdateStorage().getSparqlString("insertMainResourceType");

        media.save(model, mediaSparqlString);
        media.getMainResourceType().save(model, mainResourceTypeSparqlString);
    }

    @Override
    public Media getMedia(long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString mediaQuery = super.getHandler().getQueryStorage().getSparqlString("getMedia");
        ParameterizedSparqlString mainResourceQuery = super.getHandler().getQueryStorage().getSparqlString("getMainResourceType");

        Media media = new Media();
        media.setId(mediaId);

        Access access = new ModelAccess(model);

        mediaQuery.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));
        ResultSet mediaResults = access.executeSelect(mediaQuery.asQuery());
        if (mediaResults.hasNext()) {
            QuerySolution solution = mediaResults.next();
            media.setName(solution.getLiteral("media_name").getString());
            media.setSparqlEndPoint(solution.getResource("media_end_point").getURI());
            media.setDescription(solution.getLiteral("media_description").getString());
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
            mainResourceType.setAuthorProperty(solution.getResource("author_property").getURI());
            mainResourceType.setAuthorNameProperty(solution.getResource("author_name_property").getURI());
            mainResourceType.setAbstractProperty(solution.getResource("abstract_property").getURI());
            media.setMainResourceType(mainResourceType);
        }

        return media;
    }

    @Override
    public void subscribeUserToMedia(long userId, long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString subscribeSparqlQuery = super.getHandler().getUpdateStorage().getSparqlString("subscribeUserToMedia");
        subscribeSparqlQuery.setParam("user_id", NodeFactory.createLiteral(Long.toString(userId), XSDDatatype.XSDlong));
        subscribeSparqlQuery.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));

        UpdateAccess access = new ModelAccess(model);
        access.execute(subscribeSparqlQuery.asUpdate());
    }

    @Override
    public void unsubscribeUserToMedia(long userId, long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString subscribeSparqlQuery = super.getHandler().getUpdateStorage().getSparqlString("unsubscribeUserToMedia");
        subscribeSparqlQuery.setParam("user_id", NodeFactory.createLiteral(Long.toString(userId), XSDDatatype.XSDlong));
        subscribeSparqlQuery.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));

        UpdateAccess access = new ModelAccess(model);
        access.execute(subscribeSparqlQuery.asUpdate());
    }

    @Override
    public boolean isSubscribed(long userId, long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString isSubscribedSparqlString = super.getHandler().getQueryStorage().getSparqlString("isUserSubscribed");
        isSubscribedSparqlString.setParam("user_id", NodeFactory.createLiteral(Long.toString(userId), XSDDatatype.XSDlong));
        isSubscribedSparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));

        Access access = new ModelAccess(model);
        return access.executeAsk(isSubscribedSparqlString.asQuery());
    }

    @Override
    public Integer getUserSubscribedCount(long mediaId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString subscribeSparqlQuery = super.getHandler().getQueryStorage().getSparqlString("getUserSubscribedCount");
        subscribeSparqlQuery.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));

        Access access = new ModelAccess(model);
        ResultSet resultSet = access.executeSelect(subscribeSparqlQuery.asQuery());
        if (resultSet.hasNext()) {
            return resultSet.next().getLiteral("user_count").getInt();
        }
        return null;
    }

    @Override
    public List<Long> getSubscriptions(long userId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString subscribeSparqlQuery = super.getHandler().getQueryStorage().getSparqlString("getSubscriptions");
        subscribeSparqlQuery.setParam("user_id", NodeFactory.createLiteral(Long.toString(userId), XSDDatatype.XSDlong));

        Access access = new ModelAccess(model);
        Query query = subscribeSparqlQuery.asQuery();
        ResultSet resultSet = access.executeSelect(query);
        List<Long> medias = new ArrayList<>();

        while (resultSet.hasNext()) {
            medias.add(resultSet.next().getLiteral("media_id").getLong());
        }
        return medias;
    }

    @Override
    public List<Long> getSubscriptions(long userId, int limit) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString subscribeSparqlQuery = super.getHandler().getQueryStorage().getSparqlString("getSubscriptions");
        subscribeSparqlQuery.setParam("user_id", NodeFactory.createLiteral(Long.toString(userId), XSDDatatype.XSDlong));

        Access access = new ModelAccess(model);
        Query query = subscribeSparqlQuery.asQuery();
        query.setLimit(limit);
        ResultSet resultSet = access.executeSelect(query);
        List<Long> medias = new ArrayList<>();

        while (resultSet.hasNext()) {
            medias.add(resultSet.next().getLiteral("media_id").getLong());
        }
        return medias;
    }

    @Override
    public MainResource getMainResource(long mediaId, String resourceURI) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        Media media = getMedia(mediaId);
        MainResourceType mainResourceType = media.getMainResourceType();

        ParameterizedSparqlString mediaQuery = super.getHandler().getQueryStorage().getSparqlString("getMainResourceLang");
        mediaQuery.setParam("language", NodeFactory.createLiteral(LANGUAGE_BINDING.get(DEFAULT_LANGUAGE)));
        mediaQuery.setParam("resource", NodeFactory.createURI(resourceURI));
        mediaQuery.setParam("resource_type", NodeFactory.createURI(mainResourceType.getTypeUri()));
        mediaQuery.setParam("title_property", NodeFactory.createURI(mainResourceType.getTitlePropertyUri()));
        mediaQuery.setParam("author_property", NodeFactory.createURI(mainResourceType.getAuthorProperty()));
        mediaQuery.setParam("author_name_property", NodeFactory.createURI(mainResourceType.getAuthorNameProperty()));
        mediaQuery.setParam("abstract_property", NodeFactory.createURI(mainResourceType.getAbstractProperty()));

        Access access = new EndPointAccess(media.getSparqlEndPoint());
        ResultSet resultSet = access.executeSelect(mediaQuery.asQuery());

        if (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            MainResource mainResource = new MainResource();
            mainResource.setUri(resourceURI);
            mainResource.setName(solution.getLiteral("name").getString());
            mainResource.setAuthorUri(solution.getResource("author").getURI());
            mainResource.setAuthorName(solution.getLiteral("author_name").getString());
            mainResource.setAbstractText(solution.getLiteral("abstract").getString());
            mainResource.setLikeCount(this.getLikeCount(resourceURI));
            return mainResource;
        }

        return null;
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
    public Map<Integer, Collection<MainResource>> searchResource(String searchText, String language, long mediaId) {
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
        searchSparqlString.setParam("resource_type", NodeFactory.createURI(media.getMainResourceType().getTypeUri()));
        searchSparqlString.setParam("title_property", NodeFactory.createURI(media.getMainResourceType().getTitlePropertyUri()));
        searchSparqlString.setParam("author_property", NodeFactory.createURI(media.getMainResourceType().getAuthorProperty()));
        searchSparqlString.setParam("author_name_property", NodeFactory.createURI(media.getMainResourceType().getAuthorNameProperty()));
        searchSparqlString.setParam("abstract_property", NodeFactory.createURI(media.getMainResourceType().getAbstractProperty()));

        ResultSet results = mediaAccess.executeSelect(searchSparqlString.asQuery());

        Map<Integer, Collection<MainResource>> resources = new TreeMap<>((o, t1) -> {
            return t1.compareTo(o);
        });

        while (results.hasNext()) {
            QuerySolution solution = results.next();

            MainResource mainResource = new MainResource();
            mainResource.setUri(solution.getResource("resource").getURI());
            mainResource.setName(solution.getLiteral("name").getString());
            mainResource.setAuthorUri(solution.getResource("author").getURI());
            mainResource.setAuthorName(solution.getLiteral("author_name").getString());
            mainResource.setAbstractText(solution.getLiteral("abstract").getString());

            int likeCount = this.getLikeCount(solution.getResource("resource").getURI());
            mainResource.setLikeCount(likeCount);

            Collection<MainResource> mainResources = resources.get(likeCount);
            if (mainResources != null) {
                mainResources.add(mainResource);
            } else {
                mainResources = new ArrayList<>();
                mainResources.add(mainResource);
                resources.put(likeCount, mainResources);
            }
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

    @Override
    public String getUserUri(long userId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString sparqlString = super.getHandler().getQueryStorage().getSparqlString("getUserUri");
        sparqlString.setParam("user_id", NodeFactory.createLiteral(Long.toString(userId), XSDDatatype.XSDlong));

        Access access = new ModelAccess(model);
        ResultSet resultSet = access.executeSelect(sparqlString.asQuery());

        if (resultSet.hasNext()) {
            return resultSet.next().getResource("user_uri").getURI();
        }
        return null;
    }

    @Override
    public void rateMainResource(User user, long mediaId, String mainResourceUri) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString sparqlString = super.getHandler().getUpdateStorage().getSparqlString("insertRate");
        sparqlString.setParam("user_id", NodeFactory.createLiteral(Long.toString(user.getId()), XSDDatatype.XSDlong));
        sparqlString.setParam("main_resource", NodeFactory.createURI(mainResourceUri));
        sparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));

        UpdateAccess updateAccess = new ModelAccess(model);
        updateAccess.execute(sparqlString.asUpdate());
    }

    @Override
    public void unrateMainResource(User user, String mainResourceUri) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString sparqlString = super.getHandler().getUpdateStorage().getSparqlString("deleteRate");
        sparqlString.setParam("user_id", NodeFactory.createLiteral(Long.toString(user.getId()), XSDDatatype.XSDlong));
        sparqlString.setParam("main_resource", NodeFactory.createURI(mainResourceUri));

        UpdateAccess updateAccess = new ModelAccess(model);
        updateAccess.execute(sparqlString.asUpdate());
    }

    @Override
    public boolean isMainResourceRated(User user, String mainResourceUri) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString sparqlString = super.getHandler().getQueryStorage().getSparqlString("isMainResourceRated");
        sparqlString.setParam("user_id", NodeFactory.createLiteral(Long.toString(user.getId()), XSDDatatype.XSDlong));
        sparqlString.setParam("main_resource", NodeFactory.createURI(mainResourceUri));

        Access access = new ModelAccess(model);
        return access.executeAsk(sparqlString.asQuery());
    }

    @Override
    public int getLikeCount(String mainResourceUri) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);

        ParameterizedSparqlString likeSparqlString = super.getHandler().getQueryStorage().getSparqlString("getLikeCount");
        likeSparqlString.setParam("resource_uri", NodeFactory.createURI(mainResourceUri));

        Access likeAccess = new ModelAccess(model);
        return likeAccess.executeSelect(likeSparqlString.asQuery()).next().getLiteral("count").getInt();
    }

    private IRDFRecommender getRecommender(Access access, long mediaId) {
        ParameterizedSparqlString sparqlString = super.getHandler().getQueryStorage().getSparqlString("getUsersLikes");
        sparqlString.setParam("media_id", NodeFactory.createLiteral(Long.toString(mediaId), XSDDatatype.XSDlong));

        RDFItemRecommender recommender = new RDFItemRecommender();
        IRDFPosOnlyFeedback feedback;
        try {
            feedback = ItemDataSparql.read(access.executeSelect(sparqlString.asQuery()), "user", "resource_uri");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        recommender.setItemRecommender(new MostPopular());
        recommender.setRDFFeedback(feedback);
        recommender.train();

        return recommender;
    }

    @Override
    public List<Pair<MainResource, Double>> getRecommendedResources(long mediaId, long userId) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);
        Access access = new ModelAccess(model);

        IRDFRecommender recommender = this.getRecommender(access, mediaId);
        if (recommender == null) {
            return null;
        }

        ParameterizedSparqlString getRatedResources = super.getHandler().getQueryStorage().getSparqlString("getUnratedResources");
        getRatedResources.setParam("user_id", NodeFactory.createLiteral(Long.toString(userId), XSDDatatype.XSDlong));

        ResultSet resultSet = access.executeSelect(getRatedResources.asQuery());
        Collection<RDFNode> candidates = new ArrayList<>();
        while (resultSet.hasNext()) {
            candidates.add(resultSet.next().get("resource"));
        }

        List<Pair<RDFNode, Double>> recommendedResources = RDFRecommenderExtension.predictItemsWithWeight(recommender, ResourceFactory.createResource(getUserUri(userId)), candidates, 5);
        List<Pair<MainResource, Double>> recommendedMainResources = new ArrayList<>();
        for (Pair<RDFNode, Double> recommendedResource : recommendedResources) {
            recommendedMainResources.add(new ImmutablePair<>(this.getMainResource(mediaId, recommendedResource.getKey().asResource().getURI()), recommendedResource.getValue()));
        }
        return recommendedMainResources;
    }

    @Override
    public Prediction predictUserRating(long mediaId, long userId, String resourceUri) {
        Model model = DefaultMediaDao.getMediaModel();
        DefaultMediaDao.InitGraph(model);
        Access access = new ModelAccess(model);

        IRDFRecommender recommender = this.getRecommender(access, mediaId);
        if (recommender == null) {
            return null;
        }

        ParameterizedSparqlString getRatedResources = super.getHandler().getQueryStorage().getSparqlString("getUnratedResources");
        getRatedResources.setParam("user_id", NodeFactory.createLiteral(Long.toString(userId), XSDDatatype.XSDlong));

        ResultSet resultSet = access.executeSelect(getRatedResources.asQuery());
        Collection<RDFNode> candidates = new ArrayList<>();
        while (resultSet.hasNext()) {
            candidates.add(resultSet.next().get("resource"));
        }

        PredictionInfo info = RDFRecommenderExtension.predictItemRating(recommender, ResourceFactory.createResource(getUserUri(userId)), ResourceFactory.createResource(resourceUri), candidates);
        if (info.getWeight() > info.getStatistics().getPercentile(95.0)) {
            return Prediction.LOVE;
        } else if (info.getWeight() > info.getStatistics().getPercentile(75.0)) {
            return Prediction.LIKE;
        } else if (info.getWeight() > info.getStatistics().getPercentile(30.0)) {
            return Prediction.DISLIKE;
        } else {
            return Prediction.HATE;
        }
    }
}
