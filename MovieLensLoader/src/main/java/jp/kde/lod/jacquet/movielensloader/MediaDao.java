package jp.kde.lod.jacquet.movielensloader;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import jp.kde.lod.jacquet.access.Access;
import jp.kde.lod.jacquet.access.EndPointAccess;
import jp.kde.lod.jacquet.access.ModelAccess;
import jp.kde.lod.jacquet.access.UpdateAccess;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

/**
 * Created by Clement on 26/05/2015.
 */
public class MediaDao {
    private static VirtModel getMediaModel() {
        return new VirtModel(new VirtGraph("http://mediaselector.com/rdf", "jdbc:virtuoso://localhost:1111", "dba", "dba"));
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

    private UpdateStorage updateStorage = new UpdateStorage();
    private QueryStorage queryStorage = new QueryStorage();

    public String getMainResourceURI(String name) {
        String regex = "(^" + name + "$)|(^" + name + " \\(film\\)$)";

        ParameterizedSparqlString sparqlString = this.queryStorage.getSparqlString("getMainResource");
        sparqlString.setParam("regex", NodeFactory.createLiteral(regex));

        Access access = new EndPointAccess("http://dbpedia.org/sparql");
        ResultSet resultSet = access.executeSelect(sparqlString.asQuery());
        if (resultSet.hasNext()) {
            return resultSet.next().getResource("resource").getURI();
        }
        return null;
    }

    public void saveUser(User user) {
        Model model = getMediaModel();
        InitGraph(model);

        ParameterizedSparqlString sparqlString = this.updateStorage.getSparqlString("saveUser");
        sparqlString.setParam("user", NodeFactory.createURI("http://mediaselector.com/user/" + user.getId()));
        sparqlString.setParam("user_id", NodeFactory.createLiteral(Long.toString(user.getId()), XSDDatatype.XSDlong));
        sparqlString.setParam("user_login", NodeFactory.createLiteral(user.getLogin()));

        UpdateAccess updateAccess = new ModelAccess(model);
        updateAccess.execute(sparqlString.asUpdate());
    }

    public String getUserUri(long userId) {
        Model model = getMediaModel();
        InitGraph(model);

        ParameterizedSparqlString sparqlString = this.queryStorage.getSparqlString("getUserUri");
        sparqlString.setParam("user_id", NodeFactory.createLiteral(Long.toString(userId), XSDDatatype.XSDlong));

        Access access = new ModelAccess(model);
        ResultSet resultSet = access.executeSelect(sparqlString.asQuery());

        if (resultSet.hasNext()) {
            return resultSet.next().getResource("user_uri").getURI();
        }
        return null;
    }

    public void rateMainResource(User user, String mainResourceUri) {
        Model model = getMediaModel();
        InitGraph(model);

        ParameterizedSparqlString sparqlString = this.updateStorage.getSparqlString("insertRate");
        sparqlString.setParam("user_id", NodeFactory.createLiteral(Long.toString(user.getId()), XSDDatatype.XSDlong));
        sparqlString.setParam("main_resource", NodeFactory.createURI(mainResourceUri));

        UpdateAccess updateAccess = new ModelAccess(model);
        updateAccess.execute(sparqlString.asUpdate());
    }

    public void unrateMainResource(User user, String mainResourceUri) {
        Model model = getMediaModel();
        InitGraph(model);

        ParameterizedSparqlString sparqlString = this.updateStorage.getSparqlString("deleteRate");
        sparqlString.setParam("user_id", NodeFactory.createLiteral(Long.toString(user.getId()), XSDDatatype.XSDlong));
        sparqlString.setParam("main_resource", NodeFactory.createURI(mainResourceUri));

        UpdateAccess updateAccess = new ModelAccess(model);
        updateAccess.execute(sparqlString.asUpdate());
    }
}
