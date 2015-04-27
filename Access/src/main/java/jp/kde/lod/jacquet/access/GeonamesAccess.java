package jp.kde.lod.jacquet.access;

import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * Created by Clement on 27/04/2015.
 */
public class GeonamesAccess extends ModelAccess {

    private static final String BASE_URI = "http://sws.geonames.org/";
    private static final String MOTHER_EARTH_ID = "6295630";

    public enum Category {
        CONTAINS("/contains.rdf"),
        NEIGHBOURS("/neighbours.rdf"),
        NEARBY("/nearby.rdf");

        private String suffix;

        Category(String suffix) {
            this.suffix = suffix;
        }

        public String getSuffix() {
            return this.suffix;
        }
    }

    public GeonamesAccess() {
        this(MOTHER_EARTH_ID);
    }

    public GeonamesAccess(String resource) {
        this(resource, Category.CONTAINS);
    }

    public GeonamesAccess(String resource, Category category) {
        super(ModelFactory.createDefaultModel());
        this.read(resource, category);
    }

    public void read(String resource, Category category) {
        if (!super.getModel().isEmpty()) {
            super.getModel().removeAll();
        }

        super.getModel().read(BASE_URI + resource + category.getSuffix());
    }
}
