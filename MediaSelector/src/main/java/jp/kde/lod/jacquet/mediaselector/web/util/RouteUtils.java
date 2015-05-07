package jp.kde.lod.jacquet.mediaselector.web.util;

import java.util.Arrays;

/**
 * Created by Clement on 03/05/2015.
 */
public class RouteUtils {
    private RouteUtils() {

    }

    public static String[] getRouteValues(String route) {
        String[] routeValues = route.split("/");
        return Arrays.copyOfRange(routeValues, 2, routeValues.length);
    }

    public static String getRouteValue(String route, int id) {
        int a = route.indexOf("/");
        for (int i = 0;i < 1 + id;i++) {
            if (a != -1) {
                a = route.indexOf("/", a + 1);
            } else {
                return null;
            }
        }
        int b = route.indexOf("/", a + 1);
        if (b == -1) {
            return route.substring(a);
        } else {
            return route.substring(a, b);
        }
    }

    public static int getRouteValueCount(String route) {
        int i = -1;
        int a = route.indexOf("/");
        while (a != -1) {
            a = route.indexOf("/", a + 1);
            i++;
        }
        return i;
    }
}
