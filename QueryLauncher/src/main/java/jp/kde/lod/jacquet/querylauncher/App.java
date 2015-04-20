package jp.kde.lod.jacquet.querylauncher;

import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.shared.NotFoundException;
import jp.kde.lod.jacquet.access.SPARQLEndPoint;
import jp.kde.lod.jacquet.access.VirtSPARQLEndPoint;
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
 *
 */
public class App {
    private final static Logger LOGGER = Logger.getLogger(App.class);
    private final static String GRAPH_IRI = "http://localhost/BritishLibrary";
    private final static String DB_URL = "jdbc:virtuoso://localhost:1111";
    private final static String DB_USERNAME = "dba";
    private final static String DB_PASSWORD = "dba";
    private final static String RDF_FILE = "res/rdf/BNBLODB/sample.rdf";
    private final static String RDF_FILE_LANG = "RDF/XML";

    private VirtModel model;
    private QueryStorage queryStorage;

    public void launch() throws Exception {
        VirtGraph graph = new VirtGraph(GRAPH_IRI, DB_URL, DB_USERNAME, DB_PASSWORD);
        this.model = new VirtModel(graph);
        this.queryStorage = new QueryStorage();

        Controller controller = new DefaultController();
        QueryFrame queryFrame = new QueryFrame();
        SPARQLEndPoint sparqlEndPoint = new VirtSPARQLEndPoint(graph);

        controller.setQueryDisplay(queryFrame);
        controller.setQueryStorage(this.queryStorage);
        controller.setSPARQLEndPoint(sparqlEndPoint);
        queryFrame.setController(controller);
        this.queryStorage.addObserver(queryFrame);

        this.loadQueries();
        this.initConnection();
        queryFrame.setVisible(true);
    }

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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new App().launch();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
