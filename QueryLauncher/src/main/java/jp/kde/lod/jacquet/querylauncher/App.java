package jp.kde.lod.jacquet.querylauncher;

import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.shared.NotFoundException;
import jp.kde.lod.jacquet.access.Access;
import jp.kde.lod.jacquet.access.VirtAccess;
import jp.kde.lod.jacquet.querylauncher.controller.Controller;
import jp.kde.lod.jacquet.querylauncher.controller.DefaultController;
import jp.kde.lod.jacquet.querylauncher.model.QueryStorage;
import jp.kde.lod.jacquet.querylauncher.view.QueryFrame;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Clement on 06/04/2015.
 * Main handler of the QueryLauncher mediaselector.
 */
public class App {
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(App.class);

    /**
     * Name of the graph you want to use
     */
    private static final String GRAPH_IRI = "http://localhost/BritishLibrary";

    /**
     * Connexion string of the server you want to access
     */
    private static final String DB_URL = "jdbc:virtuoso://localhost:1111";

    /**
     * Username used to connect to the server
     */
    private static final String DB_USERNAME = "dba";

    /**
     * Password used to connect to the server
     */
    private static final String DB_PASSWORD = "dba";

    /**
     * If the graph you specified doesn't exist yet, it creates it from this rdf file
     */
    private static final String RDF_FILE = "res/rdf/BNBLODB/sample.rdf";

    /**
     * RDF syntax used in RDF_FILE
     */
    private static final String RDF_FILE_LANG = "RDF/XML";

    /**
     * Jena Model bound to a Virtuoso Server's graph
     */
    private VirtModel model;

    /**
     * Contain all the queries
     */
    private QueryStorage queryStorage;

    /**
     * Launch the mediaselector
     * @throws Exception if the connection failed
     */
    public void launch() throws Exception {
        this.model = new VirtModel(new VirtGraph(GRAPH_IRI, DB_URL, DB_USERNAME, DB_PASSWORD));
        this.queryStorage = new QueryStorage();

        Controller controller = new DefaultController();
        QueryFrame queryFrame = new QueryFrame();
        Access access = new VirtAccess(model);

        controller.setQueryDisplay(queryFrame);
        controller.setQueryStorage(this.queryStorage);
        controller.setAccess(access);
        queryFrame.setController(controller);
        this.queryStorage.addObserver(queryFrame);

        this.loadQueries();
        this.initConnection();
        queryFrame.setVisible(true);
    }

    /**
     * Load the queries from a json file
     * @throws IOException if it doesn't find the queries file
     */
    private void loadQueries() throws IOException {
        JSONTokener tokener = new JSONTokener(new FileReader("res/query.json"));
        JSONObject root = new JSONObject(tokener);

        for (Object queryName : root.keySet()) {
            String queryNameStr = (String) queryName;
            String queryPath = root.getString(queryName.toString());
            try {
                this.queryStorage.putQuery(queryNameStr, QueryFactory.read(queryPath));
            } catch (NotFoundException e) {
                LOGGER.error("Query \"" + queryNameStr + "\" not found at \"" + queryPath + "\"");
            }
        }
    }

    /**
     * Init the connection
     * @throws Exception if it doesn't find the rdf file
     */
    private void initConnection() throws Exception {
        LOGGER.debug("Loading data to \"" + GRAPH_IRI + "\"");

        if (this.model.isEmpty()) {
            try {
                model.read(RDF_FILE, RDF_FILE_LANG);
                LOGGER.debug("Data loaded successfully");
            } catch (Exception e) {
                LOGGER.error("Failed to load data :");
                throw e;
            }
        } else {
            LOGGER.debug("Data already loaded");
        }
    }

    /**
     * Entry point of the application
     * @param args arguments provided to the application (they are ignored)
     */
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new App().launch();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
