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
 * its result set.
 */
public class QueryFrame extends JFrame implements QueryDisplay, Observer {
    private static final Logger LOGGER = Logger.getLogger(QueryFrame.class);
    private static final int QUERY_LIST_WIDTH = 200;
    private static final int QUERY_LAUNCHER_HEIGHT = 50;

    private Controller controller;
    private DefaultListModel<String> queryListModel;
    private DefaultTableModel resultTableModel;
    private JList<String> queryList;
    private JScrollPane resultScrollPane;
    private JTable resultTable;

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
        this.resultTable = new JTable(this.resultTableModel);
        LOGGER.debug("Components created");

        JScrollPane queryScrollPane = new JScrollPane(this.queryList);
        this.resultScrollPane = new JScrollPane(this.resultTable);

        queryScrollPane.setPreferredSize(new Dimension(QUERY_LIST_WIDTH, queryScrollPane.getPreferredSize().height));
        queryLauncher.setPreferredSize(new Dimension(queryLauncher.getPreferredSize().width, QUERY_LAUNCHER_HEIGHT));

        queryLauncher.setText("Execute");

        mainPanel.setLayout(new BorderLayout());
        queryPanel.setLayout(new BorderLayout());

        queryPanel.add(queryScrollPane, BorderLayout.CENTER);
        queryPanel.add(queryLauncher, BorderLayout.SOUTH);
        mainPanel.add(queryPanel, BorderLayout.WEST);
        mainPanel.add(this.resultScrollPane, BorderLayout.CENTER);
        super.setContentPane(mainPanel);

        queryLauncher.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                QueryFrame.LOGGER.debug("Query Launcher pushed !");
                String queryName = QueryFrame.this.queryList.getSelectedValue();
                if (queryName != null) {
                    QueryFrame.this.controller.executeQuery(queryName);
                }
            }
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void displayQueryResults(ResultSet resultSet) {
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

    public void update(Observable observable, Object o) {
        String queryName = o.toString();
        if (this.queryListModel.indexOf(queryName) < 0) {
            this.queryListModel.addElement(queryName);
        }
    }
}
