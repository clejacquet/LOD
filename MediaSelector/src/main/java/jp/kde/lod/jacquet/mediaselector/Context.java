package jp.kde.lod.jacquet.mediaselector;

/**
 * Created by Clement on 05/05/2015.
 */
public class Context {
    private Context() {

    }

    public static final String ARTIFACT_NAME = "media-selector";

    public static final String makeCompleteIRI(String path) {
        return "/" + ARTIFACT_NAME + path;
    }
}
