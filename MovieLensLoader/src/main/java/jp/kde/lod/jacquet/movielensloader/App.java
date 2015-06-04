package jp.kde.lod.jacquet.movielensloader;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntList;
import org.mymedialite.datatype.IBooleanMatrix;
import org.mymedialite.datatype.SparseBooleanMatrix;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Clement on 26/05/2015.
 */
public class App {
    public static void main(String[] args) {
        new App().launch();
    }

    private static VirtModel getMediaModel() {
        return new VirtModel(new VirtGraph("http://mediaselector.com/rdf", "jdbc:virtuoso://localhost:1111", "dba", "dba"));
    }

    public void launch() {
        UserDao userDao = new UserDao();
        MediaDao mediaDao = new MediaDao();
        Map<Integer, String> idNameMapping = new HashMap<>();
        Map<String, String>  nameResourceMapping = new ConcurrentHashMap<>();
        Map<Integer, User> idUsersMapping = new HashMap<>();

        IBooleanMatrix matrix = new SparseBooleanMatrix();

        try {
            List<String> items = Files.readAllLines(Paths.get("result.item"));
            for (String itemLine : items) {
                String[] splittedString = itemLine.split("\\|");
                idNameMapping.put(Integer.parseInt(splittedString[0]), splittedString[1]);
            }

            List<String> data = Files.readAllLines(Paths.get("u.data"));
            for (String dataLine : data) {
                String[] splittedString = dataLine.split("[^0-9]+");
                int userId = Integer.parseInt(splittedString[0]);
                int itemId = Integer.parseInt(splittedString[1]);
                boolean like = Integer.parseInt(splittedString[2]) > 3;
                matrix.set(userId, itemId, like);
            }

            IntCollection validUsersId = matrix.nonEmptyRowIDs();

            int counter = 1;
            int size = idNameMapping.size();
            int notfound = 0;
            for (Map.Entry<Integer, String> entry : idNameMapping.entrySet()) {
                String name = entry.getValue();
                try {
                    String resourceUri = mediaDao.getMainResourceURI(name);
                    if (resourceUri != null) {
                        nameResourceMapping.put(name, resourceUri);
                        System.out.println("ID -> Name : " + counter++ + " / " + size);
                    } else {
                        System.out.println("ID -> Name : " + counter++ + " / " + size + " #NOTFOUND");
                        notfound++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("ID -> Name : " + counter++ + " / " + size + " #NOTFOUND");
                    notfound++;
                }
            }
            System.out.println(((double) (size - notfound) / (double) size) * 100.0 + "% of the items has been found");

            counter = 1;
            size = validUsersId.size();
            for (int validUserId : validUsersId) {
                User user = new User();
                user.setLogin("GeneratedUser" + validUserId);
                user.setPassword(EncryptionUtils.encryptPassword("GeneratedUser" + validUserId));
                userDao.saveUser(user);
                idUsersMapping.put(validUserId, user);
                System.out.println("User " + counter++ + " / " + size + " is created");
                Thread.sleep(100);
            }

            counter = 1;
            for (int validUserId : validUsersId) {
                IntList itemsId = matrix.getEntriesByRow(validUserId);
                for (int itemId : itemsId) {
                    if (matrix.get(validUserId, itemId)) {
                        String resource = nameResourceMapping.get(idNameMapping.get(itemId));
                        if (resource != null) {
                            try {
                                mediaDao.rateMainResource(idUsersMapping.get(validUserId), resource);
                                Thread.sleep(50);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                System.out.println("Ratings of user " + counter++ + " / " + size + " are stored");

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
