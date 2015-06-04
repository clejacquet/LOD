package jp.kde.lod.jacquet.jenamedialite.itemrec;

import org.mymedialite.IRecommender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Clement on 25/05/2015.
 */
public abstract class RDFRecommenderBase implements IRDFRecommender {
    private IRecommender recommender;

    public void setRecommender(IRecommender recommender) {
        this.recommender = recommender;
    }

    /**
     * Predict the rating or score for a given user-item combination.
     *
     * @param userId the user ID
     * @param itemId the item ID
     * @return the predicted score/rating for the given user-item combination
     */
    @Override
    public double predict(int userId, int itemId) {
        return this.recommender.predict(userId, itemId);
    }

    /**
     * Check whether a useful prediction can be made for a given user-item combination.
     *
     * @param userId the user ID
     * @param itemId the item ID
     * @return true if a useful prediction can be made, false otherwise
     */
    @Override
    public boolean canPredict(int userId, int itemId) {
        return this.recommender.canPredict(userId, itemId);
    }

    /**
     * Learn the model parameters of the recommender from the training data
     */
    @Override
    public void train() {
        this.recommender.train();
    }

    /**
     * Save the model parameters to a file
     *
     * @param filename the file to write to
     */
    @Override
    public void saveModel(String filename) throws IOException {
        this.recommender.saveModel(filename);
    }

    /**
     * Save the model parameters to a PrintWriter
     *
     * @param writer
     */
    @Override
    public void saveModel(PrintWriter writer) throws IOException {
        this.recommender.saveModel(writer);
    }

    /**
     * Get the model parameters from a file
     *
     * @param filename the file to read from
     */
    @Override
    public void loadModel(String filename) throws IOException {
        this.recommender.loadModel(filename);
    }

    /**
     * Get the model parameters from a BufferedReader
     *
     * @param reader
     */
    @Override
    public void loadModel(BufferedReader reader) throws IOException {
        this.loadModel(reader);
    }
}
