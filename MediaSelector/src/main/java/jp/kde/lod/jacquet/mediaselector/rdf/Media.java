package jp.kde.lod.jacquet.mediaselector.rdf;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import jp.kde.lod.jacquet.mediaselector.persistence.dao.MediaDao;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Clement on 12/05/2015.
 */
public class Media implements RDFModel {

    @Inject
    private static MediaDao mediaDao;

    private long id;
    private String title;
    private String sparqlEndPoint;
    private ResourceType mainResource;
    private Collection<ImmutablePair<String, ResourceType>> relatedResource;

    @Transactional
    private static long generateId() {
        long counter = Media.mediaDao.getMediaCounter() + 1L;
        Media.mediaDao.setMediaCounter(counter);
        return counter;
    }

    public Media() {
        this.relatedResource = new ArrayList<ImmutablePair<String, ResourceType>>();
    }

    public Media(boolean generate) {
        this();
        if (generate) {
            this.id = Media.generateId();
        }
    }

    public Media(JSONObject mediaJson, boolean generate) {
        this(generate);

        this.title = mediaJson.getString("title");
        this.sparqlEndPoint = mediaJson.getString("sparql");

        this.mainResource = new ResourceType(mediaJson.getJSONObject("resource"));

        JSONArray array = mediaJson.getJSONArray("relatedResources");
        int arrayLength = array.length();

        for (int i = 0;i < arrayLength;i++) {
            JSONObject object = array.getJSONObject(i);
            ResourceType resourceType = new ResourceType(object);
            this.relatedResource.add(new ImmutablePair<String, ResourceType>(object.getString("property"), resourceType));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSparqlEndPoint() {
        return sparqlEndPoint;
    }

    public void setSparqlEndPoint(String sparqlEndPoint) {
        this.sparqlEndPoint = sparqlEndPoint;
    }

    public ResourceType getMainResource() {
        return mainResource;
    }

    public void setMainResource(ResourceType mainResource) {
        this.mainResource = mainResource;
    }

    public Collection<ImmutablePair<String, ResourceType>> getRelatedResource() {
        return relatedResource;
    }

    public void setRelatedResource(Collection<ImmutablePair<String, ResourceType>> relatedResource) {
        this.relatedResource = relatedResource;
    }

    public Model toRDF() {
        return ModelFactory.createDefaultModel();
    }
}
