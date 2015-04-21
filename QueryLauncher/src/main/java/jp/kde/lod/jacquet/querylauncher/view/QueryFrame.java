package jp.kde.lod.jacquet.querylauncher.view;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import jp.kde.lod.jacquet.querylauncher.controller.Controller;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by Clement on 16/04/2015.
 * Frame which display a query list, a button to launch one of it, and a table containing
 * its result set. Correspond to a View in the MVC pattern.
 * It observes a query storage : when you add a query to the query storage,
 * the list of the queries in the frame is automatically updated.
 */
public class QueryFrame extends JFrame implements QueryDisplay, Observer {
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(QueryFrame.class);

    /**
     * Width of the query list
     */
    private static final int QUERY_LIST_WIDTH = 200;

    /**
     * Height of the query launcher button
     */
    private static final int QUERY_LAUNCHER_HEIGHT = 50;

    /**
     * Controller that this view is bound to
     */
    private Controller controller;

    /**
     * Model of the query JList
     */
    private DefaultListModel<String> queryListModel;

    /**
     * Model of the query result table
     */
    private DefaultTableModel resultTableModel;

    /**
     * Component of the frame where the list of the queries of a query storage is displayed
     */
    private JList<String> queryList;

    /**
     * Construct a query frame
     */
    public QueryFrame() {
        super("Query Launcher");

        LOGGER.debug("Initialization of the JFrame ...");
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setLocationByPlatform(true);
        super.setSize(800, 600);
        LOGGER.debug("JFrame initialized ");

        this.queryListModel = new DefaultListModel<String>();
        this.resultTableModel = new DefaultTableModel();

        LOGGER.debug("Creation of components ...");
        JPanel mainPanel = new JPanel();
        JPanel queryPanel = new JPanel();
        this.queryList = new JList<String>(this.queryListModel);
        JButton queryLauncher = new JButton();
        JTable resultTable = new JTable(this.resultTableModel);
        LOGGER.debug("Components created");

        JScrollPane queryScrollPane = new JScrollPane(this.queryList);
        JScrollPane resultScrollPane = new JScrollPane(resultTable);

        queryScrollPane.setPreferredSize(new Dimension(QUERY_LIST_WIDTH, queryScrollPane.getPreferredSize().height));
        queryLauncher.setPreferredSize(new Dimension(queryLauncher.getPreferredSize().width, QUERY_LAUNCHER_HEIGHT));

        queryLauncher.setText("Execute");

        mainPanel.setLayout(new BorderLayout());
        queryPanel.setLayout(new BorderLayout());

        queryPanel.add(queryScrollPane, BorderLayout.CENTER);
        queryPanel.add(queryLauncher, BorderLayout.SOUTH);
        mainPanel.add(queryPanel, BorderLayout.WEST);
        mainPanel.add(resultScrollPane, BorderLayout.CENTER);
        super.setContentPane(mainPanel);

        queryLauncher.addActionListener(new AbstractAction() {
            public void actionPerformed(final ActionEvent actionEvent) {
                QueryFrame.LOGGER.debug("Query Launcher pushed !");
                String queryName = QueryFrame.this.queryList.getSelectedValue();
                if (queryName != null) {
                    QueryFrame.this.controller.executeQuery(queryName);
                }
            }
        });
    }

    /**
     * Set the controller bound to this view
     * @param controller controller which receive the event of this view
     */
    public void setController(final Controller controller) {
        this.controller = controller;
    }

    /**
     * Display the result of a SPARQL query
     * @param resultSet results of a SPARQL query which are going to be displayed
     */
    public void displayQueryResults(final ResultSet resultSet) {
        List<String> vars = resultSet.getResultVars();
        this.resultTableModel.getDataVector().clear();
        this.resultTableModel.setColumnCount(0);

        for (String var : vars) {
            this.resultTableModel.addColumn(var);
        }

        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();
            Vector<String> tableRow = new Vector<String>();

            for (String var : vars) {
                Object value = row.get(var);
                tableRow.addElement((value == null) ? "" : value.toString());
            }

            this.resultTableModel.addRow(tableRow);
        }
    }

    /**
     * When the query storage that this view is observing changes, the list of the queries is updated
     * @param observable QueryStorage
     * @param o a parameter provided by the observable
     */
    public void update(final Observable observable, final Object o) {
        String queryName = o.toString();
        if (this.queryListModel.indexOf(queryName) < 0) {
            this.queryListModel.addElement(queryName);
        }
    }
}
