package jp.kde.lod.jacquet.jenamedialite.itemrec;

/**
 * Created by Clement on 28/05/2015.
 */
public enum Prediction {
    LOVE("love"),
    LIKE("like"),
    DISLIKE("dislike"),
    HATE("hate");

    private String name;

    Prediction(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
